//
//  PWAudioPlayerModule.m
//  PWDCSFramework
//
//  Created by Nick on 2018/9/30.
//  Copyright © 2018年 hisense. All rights reserved.
//

#import "PWEnum.h"
#import "PWConstants.h"
#import "PWChannelPlayer.h"
#import "PWChannelParameters.h"
#import "PWAudioPlayerModule.h"

@interface PWAudioPlayerModule () <PWChannelPlayerDelegate>
@property (nonatomic,strong) NSOperationQueue *queue;
@property (nonatomic,strong) NSMutableArray *audioArray;
@end

@implementation PWAudioPlayerModule

- (instancetype)init{
    if(self = [super init]){
        self.name = PWAudioPlayerModuleName;
        self.nameSpace = PWAudioPlayerModuleNameSpace;
    }
    return self;
}

- (void)initModule{
    if(!self.queue){
        self.queue = [[NSOperationQueue alloc] init];
        self.queue.maxConcurrentOperationCount = 1;
    }
    if(!self.audioArray){
        self.audioArray = [NSMutableArray array];
    }
    if(!self.player){
        self.player = [[PWChannelPlayer alloc] initWithChannelType:PW_CHANNEL_AUDIO];
        [self.player initPlayer];
        self.player.delegate = self;
    }
}


- (void)process:(NSString *)name payload:(NSDictionary *)payload{
    if([PWAudioPlayerModulePlay isEqualToString:name]){
        [self processPlayPayload:payload];
    }else if([PWAudioPlayerModuleStop isEqualToString:name]){
        [self processStopPayload:payload];
    }else if([PWAudioPlayerModuleClearQueue isEqualToString:name]){
        [self processClearQueuePayload:payload];
    }
}

- (void)processPlayPayload:(NSDictionary *)payload{
    if(self.queue){
        [self.queue addOperation:[[NSInvocationOperation alloc] initWithTarget:self selector:@selector(processPlayOperation:) object:payload]];
    }
}

- (void)processStopPayload:(NSDictionary *)payload{
    if(self.queue){
        [self.queue addOperation:[[NSInvocationOperation alloc] initWithTarget:self selector:@selector(processStopOperation:) object:payload]];
    }
}

- (void)processClearQueuePayload:(NSDictionary *)payload{
    if(self.queue){
        [self.queue addOperation:[[NSInvocationOperation alloc] initWithTarget:self selector:@selector(processClearQueueOperation:) object:payload]];
    }
}

- (void)processNextAudioTask{
    if(self.queue){
        [self.queue addOperation:[[NSInvocationOperation alloc] initWithTarget:self selector:@selector(processNextAudioOperation) object:nil]];
    }
}

#pragma mark NSOperationQueue
- (void)processPlayOperation:(NSDictionary *)payload{
    [self clearEnqueuedByBehavior:payload[@"playBehavior"]];
    [self.audioArray addObject:payload[@"audioItem"][@"stream"]];
    [self processNextAudioTask];
}

- (void)processStopOperation:(NSDictionary *)payload{
    [self.player stop];
}

- (void)processClearQueueOperation:(NSDictionary *)payload{
    [self clearEnqueuedByBehavior:payload[@"clearBehavior"]];
}

- (void)processNextAudioOperation{
    if(![self.player hasPlayContent]){
        if(self.audioArray.count == 0){
            [self fireAudioChannelReleased];
        }else{
            [self fireAudioChannelOccupied];
            NSDictionary *stream = self.audioArray.firstObject;
            [self.audioArray removeObject:stream];
            [self.player unload];
            self.player.parameters.playObject = stream;
            self.player.seekTime = [stream[@"offsetInMilliseconds"] integerValue] / 1000;
            [self.player load:stream[@"url"]];
        }
    }
}

#pragma mark PWChannelPlayerDelegate
- (void)onPlayerStoped:(PWChannelPlayer *) player object:(id)object{
    NSLog(@"PWAudioPlayerModule  onPlayerStoped----------");
    [self processNextAudioTask];
}

- (void)onPlayerCompleted:(PWChannelPlayer *) player object:(id)object{
    NSLog(@"PWAudioPlayerModule  onPlayerCompleted----------");
    [self processNextAudioTask];
}

- (void)onPlayerBufferingStart:(PWChannelPlayer *) player object:(id)object{
    NSLog(@"PWAudioPlayerModule  onPlayerBufferingStart----------");
}

- (void)onPlayerBufferingEnded:(PWChannelPlayer *) player object:(id)object{
    NSLog(@"PWAudioPlayerModule  onPlayerBufferingEnded----------");
}

- (void)onBufferingUpdated:(PWChannelPlayer *) player object:(id)object bufferPercentage:(NSInteger)bufferPercentage{
    NSLog(@"PWAudioPlayerModule  onBufferingUpdated---------- %zi",bufferPercentage);
}

- (void)onPlayerErrorOccurred:(PWChannelPlayer *) player object:(id)object error:(PWPlayerErrorType)error{
    NSLog(@"PWAudioPlayerModule  onPlayerErrorOccurred---------- %zi",error);
    [self processNextAudioTask];
}

- (void)onPlayerStateChanged:(PWChannelPlayer *) player object:(id)object state:(PWPlayerState) state{
    NSLog(@"PWAudioPlayerModule  onPlayerStateChanged---------- %zi",state);
}

- (void)onPlayerProgressChanged:(PWChannelPlayer *) player object:(id)object position:(NSInteger) position duration:(NSInteger)duration{
    NSLog(@"PWAudioPlayerModule  onPlayerProgressChanged----------%zi/%zi",position,duration);
}

#pragma mark 事件触发函数
- (void)fireAudioChannelOccupied{
    if (self.delegate && [self.delegate respondsToSelector:@selector(onAudioChannelOccupied)]) {
        [self.delegate onAudioChannelOccupied];
    }
}

- (void)fireAudioChannelReleased{
    if (self.delegate && [self.delegate respondsToSelector:@selector(onAudioChannelReleased)]) {
        [self.delegate onAudioChannelReleased];
    }
}

#pragma mark 功能函数
- (void)clearEnqueuedByBehavior:(NSString *)behavior {
    if ([PWAudioPlayerModuleReplaceAll isEqualToString:behavior]) {
        [self.audioArray removeAllObjects];
        [self.player stop];
    } else if ([PWAudioPlayerModuleReplaceEnqueued isEqualToString:behavior]) {
        [self.audioArray removeAllObjects];
    }
}

- (void)releaseModule{
    if(self.audioArray){
        [self.audioArray removeAllObjects];
        self.audioArray = nil;
    }
    
    if(self.player){
        [self.player destoryPlayer];
        self.player = nil;
    }
    
    if(self.queue){
        [self.queue cancelAllOperations];
        self.queue = nil;
    }
}

- (void)dealloc{
    NSLog(@"PWAudioPlayerModule dealloc");
}

@end
