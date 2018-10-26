//
//  PWDCSFramework.m
//  PWDCSFramework
//
//  Created by Nick on 2018/9/14.
//  Copyright © 2018年 hisense. All rights reserved.
//

#if __has_include(<React/RCTConvert.h>)
#import <React/RCTConvert.h>
#import <React/RCTEventDispatcher.h>
#elif __has_include("RCTConvert.h")
#import "RCTConvert.h"
#import "RCTEventDispatcher.h"
#else
#import "React/RCTConvert.h"   // Required when used as a Pod in a Swift project
#import "React/RCTEventDispatcher.h"
#endif

#import "PWFramework.h"
#import "PWDCSFramework.h"

@interface PWDCSFramework () <PWFrameworkDelegate>

@property (nonatomic,strong) PWFramework *framework;

@end

@implementation PWDCSFramework

RCT_EXPORT_MODULE();

RCT_EXPORT_METHOD(initFramework:(NSDictionary *)param
                  resolver:(RCTPromiseResolveBlock)resolve
                  rejecter:(RCTPromiseRejectBlock)reject){
    if(!self.framework){
        self.framework = [[PWFramework alloc] init];
        self.framework.delegate = self;
        self.framework.token = [param objectForKey:@"token"];
        self.framework.deviceid = [param objectForKey:@"deviceid"];
        self.framework.eventURL = [param objectForKey:@"event"];
        self.framework.speakDownloadURL = [param objectForKey:@"speak"];
        self.framework.voiceRecognizeURL = [param objectForKey:@"voice"];
        [self.framework initFramework];
    }
}


RCT_EXPORT_METHOD(enterBackground) {
    if(self.framework){
        [self.framework enterBackground];
    }
}


RCT_EXPORT_METHOD(becomeForeground) {
    if(self.framework){
        [self.framework becomeForeground];
    }
}


RCT_EXPORT_METHOD(audioRecordStarted) {
    if(self.framework){
        [self.framework audioRecordStarted];
    }
}


RCT_EXPORT_METHOD(audioRecordFinished) {
    if(self.framework){
        [self.framework audioRecordFinished];
    }
}

RCT_EXPORT_METHOD(sendTextRequest:(NSDictionary *)param){
    if(self.framework){
        [self.framework sendHTextInputRequest:[param objectForKey:@"content"] location:[param objectForKey:@"location"]];
    }
}

RCT_EXPORT_METHOD(releaseFramework){
    if(self.framework){
        [self.framework releaseFramework];
        self.framework = nil;
    }
}

-(NSArray<NSString *> *)supportedEvents{
    return @[];
}

#pragma mark PWFrameworkDelegate
- (void)sendEvent:(PWReactEventType)type content:(NSString *)content{
    NSLog(@"%@",self.bridge);
    RCTEventDispatcher *eventDispatcher = self.bridge.eventDispatcher;
    switch (type) {
        case ON_RECV_NAVIGATION:
            [eventDispatcher sendDeviceEventWithName:@"ON_RECV_NAVIGATION" body:content];
            break;
        case ON_RECV_TRAIN_TICKET:
            [eventDispatcher sendDeviceEventWithName:@"ON_RECV_TRAIN_TICKET" body:content];
            break;
        case ON_RECV_FLIGHT_TICKET:
            [eventDispatcher sendDeviceEventWithName:@"ON_RECV_FLIGHT_TICKET" body:content];
            break;
        case TEXT_INPUT_STARTED:
            [eventDispatcher sendDeviceEventWithName:@"TEXT_INPUT_STARTED" body:content];
            break;
        case TEXT_INPUT_FAILURED:
            [self.bridge.eventDispatcher sendDeviceEventWithName:@"TEXT_INPUT_FAILURED" body:content];
            break;
        case INPUT_EVENT_SUCCESSED:
            [self.bridge.eventDispatcher sendDeviceEventWithName:@"INPUT_EVENT_SUCCESSED" body:content];
            break;
        case VOICE_RECOGNIZE_STARTED:
            [self.bridge.eventDispatcher sendDeviceEventWithName:@"VOICE_RECOGNIZE_STARTED" body:content];
            break;
        case VOICE_RECOGNIZE_FAILURED:
            [self.bridge.eventDispatcher sendDeviceEventWithName:@"VOICE_RECOGNIZE_FAILURED" body:content];
            break;
        case VOICE_RECOGNIZE_SUCCESSED:
            [self.bridge.eventDispatcher sendDeviceEventWithName:@"VOICE_RECOGNIZE_SUCCESSED" body:content];
            break;
        case ON_RECEIVE_TEXT_CARD:
            [self.bridge.eventDispatcher sendDeviceEventWithName:@"ON_RECEIVE_TEXT_CARD" body:content];
            break;
        case ON_RECEIVE_LIST_CARD:
            [self.bridge.eventDispatcher sendDeviceEventWithName:@"ON_RECEIVE_LIST_CARD" body:content];
            break;
        case ON_RECEIVE_SERVICE_CARD:
            [self.bridge.eventDispatcher sendDeviceEventWithName:@"ON_RECEIVE_SERVICE_CARD" body:content];
            break;
        case ON_RECEIVE_STANDARD_CARD:
            [self.bridge.eventDispatcher sendDeviceEventWithName:@"ON_RECEIVE_STANDARD_CARD" body:content];
            break;
        case ON_RECEIVE_IMAGE_LIST_CARD:
            [self.bridge.eventDispatcher sendDeviceEventWithName:@"ON_RECEIVE_IMAGE_LIST_CARD" body:content];
            break;
        case ON_RECEIVE_SERVICE_LIST_CARD:
            [self.bridge.eventDispatcher sendDeviceEventWithName:@"ON_RECEIVE_SERVICE_LIST_CARD" body:content];
            break;
        default:
            break;
    }
}

@end
