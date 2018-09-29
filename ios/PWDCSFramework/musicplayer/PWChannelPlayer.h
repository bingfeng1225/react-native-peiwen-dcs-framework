//
//  PWChannelPlayer.h
//  PWDCSFramework
//
//  Created by Nick on 2018/9/21.
//  Copyright © 2018年 hisense. All rights reserved.
//

#import "PWEnum.h"

@protocol PWChannelPlayerDelegate

@end

@interface PWChannelPlayer : NSObject

@property (nonatomic,weak) id<PWChannelPlayerDelegate> delegate;

- (instancetype)initWithChannelType:(PWChannelType) channelType;

- (void)enterBackground;

- (void)becomeForeground;

- (void)audioRecordStarted;

- (void)audioRecordFinished;

- (void)dialogChannelOccupied;

- (void)dialogChannelReleased;

- (void)channelStateChanged:(PWChannelType)type occupied:(BOOL)occupied;

@end
