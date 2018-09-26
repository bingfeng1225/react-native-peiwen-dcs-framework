//
//  PWDCSFramework.m
//  PWDCSFramework
//
//  Created by Nick on 2018/9/14.
//  Copyright © 2018年 hisense. All rights reserved.
//

#if __has_include(<React/RCTConvert.h>)
#import <React/RCTConvert.h>
#elif __has_include("RCTConvert.h")
#import "RCTConvert.h"
#else
#import "React/RCTConvert.h"   // Required when used as a Pod in a Swift project
#endif
#import "PWDCSFramework.h"

#import "PWFramework.h"
#import "PWChannelManager.h"

@interface PWDCSFramework ()

@property (nonatomic,strong) PWFramework *framework;

@end

@implementation PWDCSFramework

RCT_EXPORT_MODULE();


RCT_EXPORT_METHOD(initFramework:(NSDictionary *)param
                  resolver:(RCTPromiseResolveBlock)resolve
                  rejecter:(RCTPromiseRejectBlock)reject){
    if(!self.framework){
        self.framework = [[PWFramework alloc] init];
        self.framework.token = [param objectForKey:@"token"];
        self.framework.deviceid = [param objectForKey:@"deviceid"];
        self.framework.eventURL = [param objectForKey:@"event"];
        self.framework.speakDownloadURL = [param objectForKey:@"speak"];
        self.framework.voiceRecognizeURL = [param objectForKey:@"voice"];
        [self.framework initFramework];
    }
}

RCT_EXPORT_METHOD(sendTextRequest:(NSString *)content){
    if(self.framework){
        [self.framework sendHTextInputRequest:content];
    }
}

RCT_EXPORT_METHOD(releaseFramework){
    if(self.framework){
        [self.framework releaseFramework];
        self.framework = nil;
    }
}
@end
