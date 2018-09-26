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

- (void)textHInputRequest:(NSString *)uuid sessionid:(NSString *)sessionid content:(NSString *)content{
    PWHTextInputRequest *request = [[PWHTextInputRequest alloc] init];
    request.uuid = uuid;
    request.content = content;
    request.sessionid = sessionid;
    request.session = self.session;
    request.deviceid = self.deviceid;
    request.url = self.voiceRecognizeURL;
    [request excuteRequest];
}

- (void)textBInputRequest:(NSString *)uuid sessionid:(NSString *)sessionid content:(NSString *)content{
    PWBTextInputRequest *request = [[PWBTextInputRequest alloc] init];
    request.content = content;
    request.token = self.token;
    request.session = self.session;
    request.deviceid = self.deviceid;
    request.url = self.eventURL;
    [request excuteRequest];
}

- (void)releaseManager{
    if (self.session) {
        [self.session invalidateAndCancel];
    }
}

- (void)dealloc{
    NSLog(@"PWHttpManager dealloc");
}

@end
