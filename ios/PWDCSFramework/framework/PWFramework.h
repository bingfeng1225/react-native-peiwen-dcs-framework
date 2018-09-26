//
//  PWFramework.h
//  PWDCSFramework
//
//  Created by Nick on 2018/9/24.
//  Copyright © 2018年 hisense. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface PWFramework : NSObject

@property (nonatomic,copy) NSString *token;
@property (nonatomic,copy) NSString *deviceid;


- (void)initFramework;

- (void)sendHTextInputRequest:(NSString *)content;

- (void)sendBTextInputRequest:(NSString *)uuid content:(NSString *)content;

- (void)releaseFramework;
@end