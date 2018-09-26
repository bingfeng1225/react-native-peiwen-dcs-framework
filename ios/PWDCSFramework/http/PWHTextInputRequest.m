//
//  PWHTextInputRequest.m
//  PWDCSFramework
//
//  Created by Nick on 2018/9/24.
//  Copyright © 2018年 hisense. All rights reserved.
//

#import "PWHTextInputRequest.h"

@interface PWHTextInputRequest ()
@property (nonatomic,assign) BOOL resume;
@property (nonatomic,strong) NSMutableArray *directives;
@end

@implementation PWHTextInputRequest

- (instancetype)init{
    if(self = [super init]){
        self.directives = [NSMutableArray array];
    }
    return self;
}

- (void)excuteRequest{
    NSURLSessionDataTask *task = [[NSURLSession sharedSession] dataTaskWithRequest:[self httpRequest:[self requestParameters]] completionHandler:^(NSData * _Nullable data, NSURLResponse * _Nullable response, NSError * _Nullable error) {
        if(error){
            //TODO 错误回调
            return;
        }
        [self parseResponse:[[NSString alloc] initWithData:data encoding:NSUTF8StringEncoding]];
        //TODO 结束回调
    }];
    [task resume];
}

- (void)parseResponse:(NSString *)content{
    NSArray *array = [content componentsSeparatedByString:@"\r\n"];
    for (NSString *part in array) {
        [self parsePartResponse:part];
    }
}

- (void)parsePartResponse:(NSString *)part{
    NSError *error = nil;
    NSData *data = [part dataUsingEncoding:NSUTF8StringEncoding];
    NSDictionary *jsonObject = [NSJSONSerialization JSONObjectWithData:data options:NSJSONReadingAllowFragments error:&error];
    if(jsonObject && !error){
        NSLog(@"%@",part);
        [self.directives addObject:jsonObject];
    }
}

- (NSDictionary *)requestParameters{
    return @{
             @"type":@"text",
             @"text":self.content,
             @"token":@"access_token",
             @"deviceid":self.deviceid,
             @"sessionid":self.sessionid
             };
}

- (NSURLRequest *)httpRequest:(NSDictionary *)param {
    NSMutableURLRequest *request = [[NSMutableURLRequest alloc] initWithURL:[NSURL URLWithString:self.url]];
    request.timeoutInterval = 60;
    request.HTTPMethod = @"POST";
    [request addValue:@"application/json; charset=utf-8" forHTTPHeaderField:@"Content-Type"];
    request.HTTPBody = [NSJSONSerialization dataWithJSONObject:param options:NSJSONWritingPrettyPrinted error:nil];
    return request;
}

- (void)dealloc{
    NSLog(@"PWHTextInputRequest dealloc");
}
@end
