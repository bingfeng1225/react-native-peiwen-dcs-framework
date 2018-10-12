//
//  PWVoiceOutputModule.m
//  PWDCSFramework
//
//  Created by Nick on 2018/9/30.
//  Copyright © 2018年 hisense. All rights reserved.
//


#import "PWConstants.h"
#import "PWUUIDManager.h"
#import "PWSandBoxHelper.h"
#import "PWSynthesisPlayer.h"
#import "PWVoiceOutputModule.h"
#import "PWChannelParameters.h"

@interface PWVoiceOutputModule () <PWChannelPlayerDelegate>
@property (nonatomic,strong) NSOperationQueue *queue;
@property (nonatomic,strong) NSMutableArray *speakArray;
@end

@implementation PWVoiceOutputModule
- (instancetype)init{
    if(self = [super init]){
        self.name = PWVoiceOutputModuleName;
        self.nameSpace = PWVoiceOutputModuleNameSpace;
    }
    return self;
}

- (void)initModule{
    if(!self.queue){
        self.queue = [[NSOperationQueue alloc] init];
        self.queue.maxConcurrentOperationCount = 1;
    }
    if(!self.speakArray){
        self.speakArray = [NSMutableArray array];
    }
    if(!self.player){
        self.player = [[PWSynthesisPlayer alloc] initWithChannelType:PW_CHANNEL_SPEAK];
        [self.player initPlayer];
        self.player.delegate = self;
    }
}

-(BOOL)isAvailableDirective:(NSString *)name{
    return [PWVoiceOutputModuleSpeak isEqualToString:name];
}

- (void)process:(NSString *)name payload:(NSDictionary *)payload{
    if([PWVoiceOutputModuleSpeak isEqualToString:name]){
        [self processSpeakPayload:payload];
    }
}

- (void)processSpeakPayload:(NSDictionary *)payload{
    if(self.queue){
        [self.queue addOperation:[[NSInvocationOperation alloc] initWithTarget:self selector:@selector(processSpeakOperation:) object:payload]];
    }
}

- (void)processNextSpeakTask{
    if(self.queue){
        [self.queue addOperation:[[NSInvocationOperation alloc] initWithTarget:self selector:@selector(processNextSpeakOperation) object:nil]];
    }
}
#pragma mark NSOperationQueue
- (void)processSpeakOperation:(NSDictionary *)payload{
    if([self.uuidManager isActiveRequest:payload[@"uuid"]]){
        [self.speakArray addObject:payload];
        [self processNextSpeakTask];
    }
}

- (void)processNextSpeakOperation{
    if (self.speakArray.count == 0) {
        [self fireSpeakChannelReleased];
    } else {
        if (![self.player hasPlayContent]){
            [self fireSpeakChannelOccupied];
            NSDictionary *payload = self.speakArray.firstObject;
            NSString *uuid = payload[@"uuid"];
            NSString *content = payload[@"content"];
            [self.speakArray removeObject:payload];
            if(![self.uuidManager isActiveRequest:uuid]){
                [self processNextSpeakTask];
            }else{
                [self.player unload];
                self.player.seekTime = 0;
                self.player.parameters.playObject = payload;
                self.player.localFilePath = [self fileLocalPath:uuid];
                [self.player load:[self synthesisURL:content]];
            }
        }else{
            [self.player stop];
        }
    }
}

#pragma mark PWChannelPlayerDelegate
- (void)onPlayerStoped:(PWChannelPlayer *) player object:(id)object{
    NSLog(@"PWAudioPlayerModule  onPlayerStoped----------");
    NSDictionary *payload = object;
    [PWSandBoxHelper deleteFile:[self fileLocalPath:payload[@"uuid"]]];
    [self processNextSpeakTask];
}

- (void)onPlayerCompleted:(PWChannelPlayer *) player object:(id)object{
    NSLog(@"PWAudioPlayerModule  onPlayerCompleted----------");
    NSDictionary *payload = object;
    [PWSandBoxHelper deleteFile:[self fileLocalPath:payload[@"uuid"]]];
    [self processNextSpeakTask];
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
    NSDictionary *payload = object;
    [PWSandBoxHelper deleteFile:[self fileLocalPath:payload[@"uuid"]]];
    [self processNextSpeakTask];
}

- (void)onPlayerStateChanged:(PWChannelPlayer *) player object:(id)object state:(PWPlayerState) state{
    NSLog(@"PWAudioPlayerModule  onPlayerStateChanged---------- %zi",state);
}

- (void)onPlayerProgressChanged:(PWChannelPlayer *) player object:(id)object position:(NSInteger) position duration:(NSInteger)duration{
    NSLog(@"PWAudioPlayerModule  onPlayerProgressChanged----------%zi/%zi",position,duration);
}

#pragma mark 事件触发函数
- (void)fireSpeakChannelOccupied{
    if (self.delegate && [self.delegate respondsToSelector:@selector(onSpeakChannelOccupied)]) {
        [self.delegate onSpeakChannelOccupied];
    }
}

- (void)fireSpeakChannelReleased{
    if (self.delegate && [self.delegate respondsToSelector:@selector(onSpeakChannelReleased)]) {
        [self.delegate onSpeakChannelReleased];
    }
}

#pragma mark 功能接口

#pragma mark 功能接口
- (NSString *)fileLocalPath:(NSString *)uuid{
    NSString *temp = [PWSandBoxHelper downloadPath];
    return [NSString stringWithFormat:@"%@/%@.mp3",temp,uuid];
}

- (NSString *)synthesisURL:(NSString *)content {
    NSMutableString *url = [NSMutableString stringWithString:self.speakDownloadURL];
    [url appendString:@"text="];
    [url appendString:content];
    [url appendString:@"&cuid="];
    [url appendString:self.deviceID];
    [url appendString:@"&deviceid="];
    [url appendString:self.deviceID];
    [url appendString:@"&per=4"];
    return [url stringByAddingPercentEscapesUsingEncoding:NSUTF8StringEncoding];
}

- (void)releaseModule{
    if(self.speakArray){
        [self.speakArray removeAllObjects];
        self.speakArray = nil;
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
    NSLog(@"PWVoiceOutputModule dealloc");
}
@end
