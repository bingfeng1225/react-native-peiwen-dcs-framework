//
//  PWVoiceRecognizeModule.h
//  PWDCSFramework
//
//  Created by Nick on 2018/9/26.
//  Copyright © 2018年 hisense. All rights reserved.
//

#import "PWBaseModule.h"
@protocol PWVoiceRecognizeModuleDelegate <NSObject>

- (void)onRecvVoiceRecognize:(NSDictionary *)payload;

@end
@interface PWVoiceRecognizeModule : PWBaseModule

@property (nonatomic,weak) id<PWVoiceRecognizeModuleDelegate> delegate;

@end
