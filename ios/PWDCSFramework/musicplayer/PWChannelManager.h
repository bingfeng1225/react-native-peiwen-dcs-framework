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

- (void)dialogChannelOccupied;

- (void)dialogChannelReleased;

- (void)speakChannelOccupied;

- (void)speakChannelReleased;

- (void)audioChannelOccupied;

- (void)audioChannelReleased;

@end
