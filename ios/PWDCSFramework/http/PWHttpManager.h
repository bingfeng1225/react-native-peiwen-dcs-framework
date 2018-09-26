//
//  HttpManager.h
//  PWDCSFramework
//
//  Created by Nick on 2018/9/24.
//  Copyright © 2018年 hisense. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface PWHttpManager : NSObject

- (void)initManager;

- (void)textHInputRequest:(NSString *)uuid deviceid:(NSString *)deviceid sessionid:(NSString *)sessionid content:(NSString *)content;

- (void)textBInputRequest:(NSString *)uuid token:(NSString *)token deviceid:(NSString *)deviceid sessionid:(NSString *)sessionid content:(NSString *)content;


- (void)releaseManager;

@end
