//
//  HttpManager.h
//  PWDCSFramework
//
//  Created by Nick on 2018/9/24.
//  Copyright © 2018年 hisense. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface PWHttpManager : NSObject

@property (nonatomic,copy) NSString *token;
@property (nonatomic,copy) NSString *deviceid;
@property (nonatomic,copy) NSString *eventURL;
@property (nonatomic,copy) NSString *voiceRecognizeURL;

- (void)initManager;

- (void)textBInputRequest:(NSString *)uuid content:(NSString *)content delegate:(id)delegate;

- (void)textHInputRequest:(NSString *)uuid sessionid:(NSString *)sessionid content:(NSString *)content delegate:(id)delegate;

- (void)releaseManager;

@end
