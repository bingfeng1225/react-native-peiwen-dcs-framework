//
//  PWChannelState.h
//  PWDCSFramework
//
//  Created by Nick on 2018/9/21.
//  Copyright © 2018年 hisense. All rights reserved.
//

#import "PWEnum.h"

@interface PWChannelState : NSObject

@property (nonatomic,assign) BOOL channelOccupied;
@property (nonatomic,assign) PWChannelType channelType;
-(instancetype)initWithChannelType:(PWChannelType) channelType;

@end
