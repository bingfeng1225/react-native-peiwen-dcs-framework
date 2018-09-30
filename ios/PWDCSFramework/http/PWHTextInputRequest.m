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
    [self fireHTextInputStarted];
    NSURLSessionDataTask *task = [[NSURLSession sharedSession] dataTaskWithRequest:[self httpRequest:[self requestParameters]] completionHandler:^(NSData * _Nullable data, NSURLResponse * _Nullable response, NSError * _Nullable error) {
        if(error){
            [self fireHTextInputFailured];
        }else{
            [self parseResponse:[[NSString alloc] initWithData:data encoding:NSUTF8StringEncoding]];
            [self fireHTextInputSuccessed];
        }
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
    NSDictionary *response = [NSJSONSerialization JSONObjectWithData:data options:NSJSONReadingAllowFragments error:&error];
    if(!error && response){
        NSDictionary *directive = [self availableResponse:response];
        if(directive){
            [self.directives addObject:directive];
        }
    }
}

- (NSDictionary *)availableResponse:(NSDictionary *)response{
    NSDictionary *directive = [response objectForKey:@"directive"];
    if(!directive){
        return nil;
    }
    NSDictionary *header = [directive objectForKey:@"header"];
    NSDictionary *payload = [directive objectForKey:@"payload"];
    if(!header || !payload){
        return nil;
    }
    
    NSMutableDictionary *exdirective = [NSMutableDictionary dictionary];
    NSMutableDictionary *expayload = [NSMutableDictionary dictionaryWithDictionary:payload];
    [expayload setObject:self.uuid forKey:@"uuid"];
    
    [exdirective setObject:header forKey:@"header"];
    [exdirective setObject:expayload forKey:@"payload"];
    return exdirective;
}

- (NSDictionary *)requestParameters{
    return @{
             @"type":@"text",
             @"text":self.content,
             @"token":@"access_token",
             @"deviceid":self.deviceid,
             @"location":self.location,
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

- (void)fireHTextInputStarted{
    if(self.delegate && [self.delegate respondsToSelector:@selector(onHTextInputStarted:content:)]){
        [self.delegate onHTextInputStarted:self.uuid content:self.content];
    }
}

- (void)fireHTextInputFailured{
    if(self.delegate && [self.delegate respondsToSelector:@selector(onHTextInputFailured:)]){
        [self.delegate onHTextInputFailured:self.uuid];
    }
}

- (void)fireHTextInputSuccessed{
    if(self.delegate && [self.delegate respondsToSelector:@selector(onHTextInputSuccessed:directives:)]){
        [self.delegate onHTextInputSuccessed:self.uuid directives:self.directives];
    }
}

- (void)dealloc{
    NSLog(@"PWHTextInputRequest dealloc");
}
@end
