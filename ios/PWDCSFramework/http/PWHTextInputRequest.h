//
//  PWHTextInputRequest.h
//  PWDCSFramework
//
//  Created by Nick on 2018/9/24.
//  Copyright © 2018年 hisense. All rights reserved.
//

#import <Foundation/Foundation.h>

@protocol PWHTextInputRequestDelegate <NSObject>

- (void)onHTextInputStarted:(NSString *)uuid content:(NSString *)content;

- (void)onHTextInputFailured:(NSString *)uuid;

- (void)onHTextInputSuccessed:(NSString *)uuid directives:(NSArray *)directives;

@end

@interface PWHTextInputRequest : NSObject

@property (nonatomic,copy) NSString *url;
@property (nonatomic,copy) NSString *uuid;
@property (nonatomic,copy) NSString *content;
@property (nonatomic,copy) NSString *deviceid;
@property (nonatomic,copy) NSString *location;
@property (nonatomic,copy) NSString *sessionid;
@property (nonatomic,weak) NSURLSession *session;
@property (nonatomic,weak) id<PWHTextInputRequestDelegate> delegate;

- (void)excuteRequest;

@end
