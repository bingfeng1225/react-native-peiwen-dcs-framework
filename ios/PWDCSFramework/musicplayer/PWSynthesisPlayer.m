//
//  PWSynthesisPlayer.m
//  PWDCSFramework
//
//  Created by Nick on 2018/9/30.
//  Copyright © 2018年 hisense. All rights reserved.
//

#import "PWSandBoxHelper.h"
#import "PWSynthesisPlayer.h"

@interface PWSynthesisPlayer () <NSURLSessionDelegate>

@property (nonatomic,strong) NSURLSession *session;
@property (nonatomic,weak) NSURLSessionDataTask *task;

@end

@implementation PWSynthesisPlayer

#pragma mark 重写父类方法
- (void)initPlayer{
    [super initPlayer];
    if (!self.session) {
        self.session = [NSURLSession sessionWithConfiguration:[NSURLSessionConfiguration ephemeralSessionConfiguration] delegate:self delegateQueue:[[NSOperationQueue alloc] init]];
    }
}

- (void)load:(NSString *)url{
    self.playerState = PW_PLAYER_PREPAREING;
    NSMutableURLRequest *request = [NSMutableURLRequest requestWithURL:[NSURL URLWithString:url]];

    self.task = [self.session dataTaskWithRequest:request];
    
    [self.task resume];
}


- (void)stop {
    self.task = nil;
    [super stop];
}

- (void)unload {
    self.task = nil;
    [super unload];
}

- (void)destoryPlayer{
    [super destoryPlayer];
    if (self.session) {
        [self.session invalidateAndCancel];
        self.session = nil;
    }
}


#pragma mark NSURLSessionDelegate
- (void)URLSession:(NSURLSession *)session dataTask:(NSURLSessionDataTask *)dataTask didReceiveResponse:(NSHTTPURLResponse *)response completionHandler:(void (^)(NSURLSessionResponseDisposition))completionHandler
{
    if(![dataTask isEqual:self.task]){
        completionHandler(NSURLSessionResponseCancel);
        return;
    }
    NSLog(@"%@",[[response allHeaderFields] description]);
    NSString *miniType = response.MIMEType; //有时后台错误，给的下载链接为非视频
    if(![@"audio/mp3" isEqualToString:miniType]){
        completionHandler(NSURLSessionResponseCancel);
        return;
    }
    completionHandler(NSURLSessionResponseAllow);
}

/**
 * 接收到服务器返回的数据
 */
- (void)URLSession:(NSURLSession *)session dataTask:(NSURLSessionDataTask *)dataTask didReceiveData:(NSData *)data
{
    if(![dataTask isEqual:self.task]){
        [dataTask cancel];
        return;
    }
    [PWSandBoxHelper writeFile:self.localFilePath data:data length:[data length]];
}

/**
 * 请求完毕（成功|失败）
 */
- (void)URLSession:(NSURLSession *)session task:(NSURLSessionTask *)task didCompleteWithError:(NSError *)error
{
    if(![task isEqual:self.task]){
        return;
    }
    if(error){
        self.errorType = PW_PLAYER_ERROR_UNKNOWN;
    }else{
        [super load:self.localFilePath];
    }
}

@end
