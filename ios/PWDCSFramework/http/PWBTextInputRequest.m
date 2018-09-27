//
//  PWBTextInputRequest.m
//  PWDCSFramework
//
//  Created by Nick on 2018/9/24.
//  Copyright © 2018年 hisense. All rights reserved.
//

#import "PWBTextInputRequest.h"

@interface PWBTextInputRequest ()
@property (nonatomic,assign) BOOL resume;
@property (nonatomic,strong) NSMutableArray *directives;
@end

@implementation PWBTextInputRequest

- (instancetype)init{
    if(self = [super init]){
        self.directives = [NSMutableArray array];
    }
    return self;
}

- (void)excuteRequest{
    [self fireBTextInputStarted];
    NSURLSessionDataTask *task = [[NSURLSession sharedSession] dataTaskWithRequest:[self httpRequest:[self requestParameters]] completionHandler:^(NSData * _Nullable data, NSURLResponse * _Nullable response, NSError * _Nullable error) {
        if(error){
            [self fireBTextInputFailured];
        }else{
            [self parseResponse:[[NSString alloc] initWithData:data encoding:NSUTF8StringEncoding]];
            [self fireBTextInputSuccessed];
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
        NSLog(@"%@",part);
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
    NSDictionary *header = @{
                             @"name":@"TextInput",
                             @"namespace":@"ai.dueros.device_interface.text_input",
                             @"messageId":[NSUUID UUID].UUIDString,
                             @"dialogRequestId":[NSUUID UUID].UUIDString
                             };
    NSDictionary *payload = @{
                              @"query":self.content
                              };
    
    NSDictionary *enevt = @{
                            @"header":header,
                            @"payload":payload
                            };
    
    return @{
             @"event":enevt
             };
}

- (NSURLRequest *)httpRequest:(NSDictionary *)param{
    NSMutableURLRequest *request = [[NSMutableURLRequest alloc] initWithURL:[NSURL URLWithString:self.url]];
    request.timeoutInterval = 60;
    request.HTTPMethod = @"POST";
    NSString *boundary = [NSUUID UUID].UUIDString;
    [request addValue:@"0" forHTTPHeaderField:@"debug"];
    [request addValue:self.deviceid forHTTPHeaderField:@"dueros-device-id"];
    [request addValue:[NSUUID UUID].UUIDString forHTTPHeaderField:@"saiyalogid"];
    [request addValue:[NSString stringWithFormat:@"Bearer %@",self.token] forHTTPHeaderField:@"Authorization"];
    [request addValue:[NSString stringWithFormat:@"multipart/form-data;boundary=%@", boundary]  forHTTPHeaderField:@"Content-Type"];
    NSData *data = [NSJSONSerialization dataWithJSONObject:param options:NSJSONWritingPrettyPrinted error:nil];
    NSMutableString *content = [NSMutableString stringWithFormat:@"\r\n--%@\r\n",boundary];
    [content appendString:@"Content-Disposition: form-data; name=\"metadata\"\r\n"];
    [content appendString:@"Content-Type: application/json;charset=UTF-8\r\n\r\n"];
    [content appendString:[[NSString alloc] initWithData:data encoding:NSUTF8StringEncoding]];
    [content appendString:[NSString stringWithFormat:@"\r\n--%@--\r\n",boundary]];
    request.HTTPBody = [content dataUsingEncoding:NSUTF8StringEncoding];
    return request;
}


- (void)fireBTextInputStarted{
    if(self.delegate && [self.delegate respondsToSelector:@selector(onBTextInputStarted:content:)]){
        [self.delegate onBTextInputStarted:self.uuid content:self.content];
    }
}
- (void)fireBTextInputFailured{
    if(self.delegate && [self.delegate respondsToSelector:@selector(onBTextInputFailured:)]){
        [self.delegate onBTextInputFailured:self.uuid];
    }
}
- (void)fireBTextInputSuccessed{
    if(self.delegate && [self.delegate respondsToSelector:@selector(onBTextInputSuccessed:directives:)]){
        [self.delegate onBTextInputSuccessed:self.uuid directives:self.directives];
    }
}

- (void)dealloc{
    NSLog(@"PWBTextInputRequest dealloc");
}
@end
