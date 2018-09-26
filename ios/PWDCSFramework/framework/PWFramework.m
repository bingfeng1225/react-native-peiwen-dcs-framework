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

@interface PWFramework ()
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
        [self.messageQueue initQueue];
    }
}

- (void)initModuleManager{
    if(!self.moduleManager){
        self.moduleManager = [[PWModuleManager alloc] init];
        [self.moduleManager initManager];
    }
}

- (void)initChannelManager{
    if(!self.channelManager){
        self.channelManager = [[PWChannelManager alloc] init];
        [self.channelManager initManager];
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

- (void)sendHTextInputRequest:(NSString *)content{
    [self.httpManager textHInputRequest:[self.uuidManager createActiveRequest] sessionid:self.uuidManager.lastSession content:content];
}

- (void)sendBTextInputRequest:(NSString *)uuid content:(NSString *)content{
   [self.httpManager textBInputRequest:uuid  sessionid:self.uuidManager.lastSession content:content];
}

- (void)dealloc{
    NSLog(@"PWFramework dealloc");
}

@end
