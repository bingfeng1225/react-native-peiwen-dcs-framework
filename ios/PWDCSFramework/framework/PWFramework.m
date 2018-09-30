//
//  PWFramework.m
//  PWDCSFramework
//
//  Created by Nick on 2018/9/24.
//  Copyright © 2018年 hisense. All rights reserved.
//

#import "PWFramework.h"
#import "PWUUIDManager.h"
#import "PWHttpManager.h"
#import "PWMessageQueue.h"
#import "PWModuleManager.h"
#import "PWChannelManager.h"
#import "PWHTextInputRequest.h"
#import "PWBTextInputRequest.h"
#import "PWScreenModule.h"
#import "PWSystemModule.h"
#import "PWAudioPlayerModule.h"
#import "PWVoiceOutputModule.h"
#import "PWVoiceRecognizeModule.h"

@interface PWFramework () <PWMessageQueueDelegate,PWHTextInputRequestDelegate,PWBTextInputRequestDelegate,PWSystemModuleDelegate,PWScreenModuleDelegate,PWAudioPlayerModuleDelegate,PWVoiceRecognizeModuleDelegate,PWVoiceOutputModuleDelegate>
@property (nonatomic,strong) PWUUIDManager *uuidManager;
@property (nonatomic,strong) PWHttpManager *httpManager;
@property (nonatomic,strong) PWMessageQueue *messageQueue;
@property (nonatomic,strong) PWModuleManager *moduleManager;
@property (nonatomic,strong) PWChannelManager *channelManager;
@end

@implementation PWFramework

- (void)initFramework{
    [self initUUIDManager];
    [self initHttpManager];
    [self initMessageQueue];
    [self initModuleManager];
    [self initChannelManager];
}

- (void)initUUIDManager{
    if(!self.uuidManager){
        self.uuidManager = [[PWUUIDManager alloc] init];
        [self.uuidManager initManager];
    }
}

- (void)initHttpManager{
    if(!self.httpManager){
        self.httpManager = [[PWHttpManager alloc] init];
        self.httpManager.token = self.token;
        self.httpManager.deviceid = self.deviceid;
        self.httpManager.eventURL = self.eventURL;
        self.httpManager.voiceRecognizeURL = self.voiceRecognizeURL;
        [self.httpManager initManager];
    }
}
- (void)initMessageQueue{
    if(!self.messageQueue){
        self.messageQueue = [[PWMessageQueue alloc] init];
        self.messageQueue.delegate = self;
        [self.messageQueue initQueue];
    }
}

- (void)initModuleManager{
    if(!self.moduleManager){
        self.moduleManager = [[PWModuleManager alloc] init];
        self.moduleManager.screenModule.delegate = self;
        self.moduleManager.systemModule.delegate = self;
        self.moduleManager.audioPlayerModule.delegate = self;
        self.moduleManager.voiceOutputModule.delegate = self;
        self.moduleManager.voiceOutputModule.deviceID = self.deviceid;
        self.moduleManager.voiceOutputModule.uuidManager = self.uuidManager;
        self.moduleManager.voiceOutputModule.speakDownloadURL = self.speakDownloadURL;
        self.moduleManager.voiceRecognizeModule.delegate = self;
        [self.moduleManager initManager];
    }
}

- (void)initChannelManager{
    if(!self.channelManager){
        self.channelManager = [[PWChannelManager alloc] init];
        [self.channelManager initManager];
        [self.channelManager insertPlayer:self.moduleManager.audioPlayerModule.player];
    }
}

- (void)releaseFramework{
    if(!self.uuidManager){
        [self.uuidManager releaseManager];
        self.uuidManager = nil;
    }
    if(self.httpManager){
        [self.httpManager releaseManager];
        self.httpManager = nil;
    }
    if(self.messageQueue){
        [self.messageQueue releaseQueue];
        self.messageQueue = nil;
    }
    if(self.moduleManager){
        [self.moduleManager releaseManager];
        self.moduleManager = nil;
    }
    if(self.channelManager){
        [self.channelManager releaseManager];
        self.channelManager = nil;
    }
}

#pragma mark 通道变化处理
- (void)enterBackground{
    [self.channelManager enterBackground];
}

- (void)becomeForeground{
    [self.channelManager becomeForeground];
}

- (void)audioRecordStarted{
    [self.channelManager audioRecordStarted];
//    this.deviceManager.voiceOutputModule().channelPlayer().stop();
}

- (void)audioRecordFinished{
    [self.channelManager audioRecordFinished ];
}

- (void)dialogChannelReleased:(NSString *) uuid {
    if ([self.uuidManager isActiveRequest:uuid]) {
        [self.channelManager dialogChannelReleased ];
    }
}

- (void)dialogChannelOccupied {
    [self.channelManager dialogChannelOccupied];
//    this.deviceManager.voiceOutputModule().channelPlayer().stop();
}

#pragma mark PWPWMessageQueueDelegate
- (void)processDirective:(NSDictionary *)directive{
    [self.moduleManager processDirective:directive];
}

#pragma mark PWHTextInputRequestDelegate
- (void)sendHTextInputRequest:(NSString *)content location:(NSString *)location{
//    [self dialogChannelOccupied];
    [self.httpManager textHInputRequest:[self.uuidManager createActiveRequest] sessionid:self.uuidManager.lastSession location:location content:content delegate:self];
}

- (void)onHTextInputStarted:(NSString *)uuid content:(NSString *)content{
    NSDictionary *dictionary = @{
                                 @"uuid":uuid,
                                 @"content":content,
                                 @"timestamp":@((long)[[NSDate date] timeIntervalSince1970]*1000)
                                 };
    [self sendEvent:VOICE_RECOGNIZE_STARTED content:[self dictionary2JsonString:dictionary]];
}

- (void)onHTextInputFailured:(NSString *)uuid{
}

- (void)onHTextInputSuccessed:(NSString *)uuid directives:(NSArray *)directives{
    for (NSDictionary *directive in directives) {
        [self.messageQueue processDirective:directive];
    }
}

#pragma mark PWBTextInputRequestDelegate
- (void)sendBTextInputRequest:(NSString *)uuid content:(NSString *)content{
    [self.httpManager textBInputRequest:uuid content:content delegate:self];
}

- (void)onBTextInputStarted:(NSString *)uuid content:(NSString *)content{
    
}

- (void)onBTextInputFailured:(NSString *)uuid{
    
}

- (void)onBTextInputSuccessed:(NSString *)uuid directives:(NSArray *)directives{
    
    for (NSDictionary *directive in directives) {
        [self.messageQueue processDirective:directive];
    }
}

#pragma mark PWScreenModuleDelegate
- (void)onRecvTextCard:(NSDictionary *)payload{
    [self sendEvent:ON_RECEIVE_TEXT_CARD content:[self dictionary2JsonString:payload]];
}
- (void)onRecvListCard:(NSDictionary *)payload{
    [self sendEvent:ON_RECEIVE_LIST_CARD content:[self dictionary2JsonString:payload]];
}
- (void)onRecvServiceCard:(NSDictionary *)payload{
    [self sendEvent:ON_RECEIVE_SERVICE_CARD content:[self dictionary2JsonString:payload]];
}

- (void)onRecvStandardCard:(NSDictionary *)payload{
    [self sendEvent:ON_RECEIVE_STANDARD_CARD content:[self dictionary2JsonString:payload]];
}

- (void)onRecvImageListCard:(NSDictionary *)payload{
    [self sendEvent:ON_RECEIVE_IMAGE_LIST_CARD content:[self dictionary2JsonString:payload]];
}

- (void)onRecvServiceListCard:(NSDictionary *)payload{
    [self sendEvent:ON_RECEIVE_SERVICE_LIST_CARD content:[self dictionary2JsonString:payload]];
}

#pragma mark PWSystemModuleDelegate
- (void)onSessionChanged:(NSString *)session{
    self.uuidManager.lastSession = session;
}

#pragma mark PWAudioPlayerModuleDelegate
- (void)onAudioChannelOccupied{
    [self.channelManager audioChannelOccupied];
}
- (void)onAudioChannelReleased{
    [self.channelManager audioChannelReleased];
}

#pragma mark PWVoiceOutputModuleDelegate
- (void)onSpeakChannelOccupied{
    [self.channelManager speakChannelOccupied];
}

- (void)onSpeakChannelReleased{
    [self.channelManager speakChannelReleased];
}

#pragma mark PWVoiceRecognizeModuleDelegate
- (void)onRecvVoiceRecognize:(NSDictionary *)payload{
    [self sendBTextInputRequest:payload[@"uuid"] content:payload[@"content"]];
}



#pragma mark 功能函数
- (NSString *)dictionary2JsonString:(NSDictionary *)dictionary{
    NSData *data = [NSJSONSerialization dataWithJSONObject:dictionary options:NSJSONWritingPrettyPrinted error:nil];
    NSString *content = [[NSString alloc] initWithData:data encoding:NSUTF8StringEncoding];
    return [content stringByReplacingOccurrencesOfString:@"\n" withString:@""];
}

- (void)sendEvent:(PWReactEventType)type content:(NSString *)content{
    if(self.delegate && [self.delegate respondsToSelector:@selector(sendEvent:content:)]){
        [self.delegate sendEvent:type content:content];
    }
}

- (void)dealloc{
    NSLog(@"PWFramework dealloc");
}

@end
