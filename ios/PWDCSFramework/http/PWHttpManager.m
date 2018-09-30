//
//  HttpManager.m
//  PWDCSFramework
//
//  Created by Nick on 2018/9/24.
//  Copyright © 2018年 hisense. All rights reserved.
//

#import "PWHttpManager.h"
#import "PWHTextInputRequest.h"
#import "PWBTextInputRequest.h"

@interface PWHttpManager ()

@property (nonatomic,strong) NSURLSession *session;

@end

@implementation PWHttpManager

- (void)initManager{
    if (!self.session) {
        self.session = [NSURLSession sessionWithConfiguration:[NSURLSessionConfiguration ephemeralSessionConfiguration]];
    }
}

- (void)textBInputRequest:(NSString *)uuid content:(NSString *)content delegate:(id)delegate{
    PWBTextInputRequest *request = [[PWBTextInputRequest alloc] init];
    request.uuid = uuid;
    request.content = content;
    request.token = self.token;
    request.delegate = delegate;
    request.session = self.session;
    request.deviceid = self.deviceid;
    request.url = self.eventURL;
    [request excuteRequest];
}

- (void)textHInputRequest:(NSString *)uuid sessionid:(NSString *)sessionid location:(NSString *)location content:(NSString *)content delegate:(id)delegate{
    PWHTextInputRequest *request = [[PWHTextInputRequest alloc] init];
    request.uuid = uuid;
    request.content = content;
    request.delegate = delegate;
    request.location = location;
    request.sessionid = sessionid;
    request.session = self.session;
    request.deviceid = self.deviceid;
    request.url = self.voiceRecognizeURL;
    [request excuteRequest];
}

- (void)releaseManager{
    if (self.session) {
        [self.session invalidateAndCancel];
        self.session = nil;
    }
}

- (void)dealloc{
    NSLog(@"PWHttpManager dealloc");
}

@end
