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

- (void)textHInputRequest:(NSString *)uuid deviceid:(NSString *)deviceid sessionid:(NSString *)sessionid content:(NSString *)content{
    PWHTextInputRequest *request = [[PWHTextInputRequest alloc] init];
    request.uuid = uuid;
    request.content = content;
    request.deviceid = deviceid;
    request.sessionid = sessionid;
    request.session = self.session;
    request.url = @"http://27.223.99.143:11181/smartapp/smart/direct";
    [request excuteRequest];
}

- (void)textBInputRequest:(NSString *)uuid token:(NSString *)token deviceid:(NSString *)deviceid sessionid:(NSString *)sessionid content:(NSString *)content{
    PWBTextInputRequest *request = [[PWBTextInputRequest alloc] init];
    request.token = token;
    request.content = content;
    request.deviceid = deviceid;
    request.session = self.session;
    request.url = @"https://dueros-h2.baidu.com/dcs/v1/events";
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
