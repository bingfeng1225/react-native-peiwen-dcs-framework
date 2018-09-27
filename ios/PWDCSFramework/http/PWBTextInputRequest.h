//
//  PWBTextInputRequest.h
//  PWDCSFramework
//
//  Created by Nick on 2018/9/24.
//  Copyright © 2018年 hisense. All rights reserved.
//

#import <Foundation/Foundation.h>

@protocol PWBTextInputRequestDelegate <NSObject>

- (void)onBTextInputStarted:(NSString *)uuid content:(NSString *)content;

- (void)onBTextInputFailured:(NSString *)uuid;

- (void)onBTextInputSuccessed:(NSString *)uuid directives:(NSArray *)directives;

@end

@interface PWBTextInputRequest : NSObject

@property (nonatomic,copy) NSString *url;
@property (nonatomic,copy) NSString *uuid;
@property (nonatomic,copy) NSString *token;
@property (nonatomic,copy) NSString *content;
@property (nonatomic,copy) NSString *deviceid;
@property (nonatomic,weak) NSURLSession *session;
@property (nonatomic,weak) id<PWBTextInputRequestDelegate> delegate;

- (void)excuteRequest;

@end
