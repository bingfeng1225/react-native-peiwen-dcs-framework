//
//  PWChannelParameters.h
//  PWDCSFramework
//
//  Created by Nick on 2018/9/21.
//  Copyright © 2018年 hisense. All rights reserved.
//

#import "PWEnum.h"

@interface PWChannelParameters : NSObject
@property (nonatomic,strong) id playObject;
@property (nonatomic,assign) NSInteger duration;
@property (nonatomic,assign) NSInteger position;
@property (nonatomic,assign) BOOL enterBackground;
@property (nonatomic,assign) BOOL audioRecordStarted;
@property (nonatomic,assign) BOOL dialogChannelOccupied;

- (instancetype)initWithChannelType:(PWChannelType) channelType;

- (BOOL)isConditionsMeetRequirements;

- (BOOL)isConditionOfChannelState;


@end
