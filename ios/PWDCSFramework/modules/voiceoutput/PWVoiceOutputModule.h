//
//  PWVoiceOutputModule.h
//  PWDCSFramework
//
//  Created by Nick on 2018/9/30.
//  Copyright © 2018年 hisense. All rights reserved.
//

#import "PWBaseModule.h"

@class PWUUIDManager;
@class PWSynthesisPlayer;

@protocol PWVoiceOutputModuleDelegate <NSObject>

- (void)onSpeakChannelOccupied;

- (void)onSpeakChannelReleased;

@end

@interface PWVoiceOutputModule : PWBaseModule

@property (nonatomic,copy) NSString *deviceID;
@property (nonatomic,copy) NSString *speakDownloadURL;
@property (nonatomic,weak) PWUUIDManager *uuidManager;
@property (nonatomic,strong) PWSynthesisPlayer* player;
@property (nonatomic,weak) id<PWVoiceOutputModuleDelegate> delegate;

@end
