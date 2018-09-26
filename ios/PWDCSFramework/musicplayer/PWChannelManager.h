//
//  PWChannelManager.h
//  PWDCSFramework
//
//  Created by Nick on 2018/9/21.
//  Copyright © 2018年 hisense. All rights reserved.
//

#import <Foundation/Foundation.h>

@class PWChannelPlayer;

@interface PWChannelManager : NSObject

- (void)initManager;

- (void)releaseManager;

- (void)insertPlayer:(PWChannelPlayer *)player;

- (void)enterBackground;

- (void)becomeForeground;

- (void)audioRecordStarted;

- (void)audioRecordFinished;

- (void)dialogChannelReleased;

- (void)dialogChannelOccupied;

- (void)speakChannelReleased;

- (void)speakChannelOccupied;

- (void)audioChannelReleased;

- (void)audioChannelOccupied;

@end
