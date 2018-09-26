//
//  PWChannelState.h
//  PWDCSFramework
//
//  Created by Nick on 2018/9/21.
//  Copyright © 2018年 hisense. All rights reserved.
//

#import "PWPlayerEnum.h"

@interface PWChannelState : NSObject
@property (nonatomic,assign) PWChannelType channelType;
@property (nonatomic,assign) PWChannelType channelOccupied;

-(instancetype)initWithChannelType:(PWChannelType) channelType;
@end
