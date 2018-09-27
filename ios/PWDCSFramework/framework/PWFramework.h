//
//  PWFramework.h
//  PWDCSFramework
//
//  Created by Nick on 2018/9/24.
//  Copyright © 2018年 hisense. All rights reserved.
//

#import "PWEnum.h"

@protocol PWFrameworkDelegate <NSObject>
- (void)sendEvent:(PWReactEventType)type content:(NSString *)content;
@end

@interface PWFramework : NSObject

@property (nonatomic,copy) NSString *token;
@property (nonatomic,copy) NSString *deviceid;
@property (nonatomic,copy) NSString *eventURL;
@property (nonatomic,copy) NSString *speakDownloadURL;
@property (nonatomic,copy) NSString *voiceRecognizeURL;
@property (nonatomic,weak) id<PWFrameworkDelegate> delegate;

- (void)initFramework;

- (void)sendHTextInputRequest:(NSString *)content;

- (void)releaseFramework;
@end
