//
//  PWChannelManager.m
//  PWDCSFramework
//
//  Created by Nick on 2018/9/21.
//  Copyright © 2018年 hisense. All rights reserved.
//

#import "PWChannelPlayer.h"
#import "PWChannelManager.h"

@interface PWChannelManager ()
@property(nonatomic,strong) NSMutableArray *players;
@end

@implementation PWChannelManager

- (void)initManager{
    self.players = [NSMutableArray array];
}

- (void)releaseManager{
    [self.players removeAllObjects];
    self.players = nil;
}

- (void)insertPlayer:(PWChannelPlayer *)player{
    [self.players addObject:player];
}

- (void)enterBackground{
    for (PWChannelPlayer *player in self.players) {
        [player enterBackground];
    }
}

- (void)becomeForeground{
    for (PWChannelPlayer *player in self.players) {
        [player becomeForeground];
    }
}

- (void)audioRecordStarted{
    for (PWChannelPlayer *player in self.players) {
        [player audioRecordStarted];
    }
}

- (void)audioRecordFinished{
    for (PWChannelPlayer *player in self.players) {
        [player audioRecordFinished];
    }
}



- (void)dialogChannelOccupied{
    for (PWChannelPlayer *player in self.players) {
        [player dialogChannelOccupied];
    }
}

- (void)dialogChannelReleased{
    for (PWChannelPlayer *player in self.players) {
        [player dialogChannelReleased];
    }
}

- (void)speakChannelOccupied{
    for (PWChannelPlayer *player in self.players) {
        [player channelStateChanged:PW_CHANNEL_SPEAK occupied:YES];
    }
}

- (void)speakChannelReleased{
    for (PWChannelPlayer *player in self.players) {
        [player channelStateChanged:PW_CHANNEL_SPEAK occupied:NO];
    }
}


- (void)audioChannelOccupied{
    for (PWChannelPlayer *player in self.players) {
        [player channelStateChanged:PW_CHANNEL_AUDIO occupied:YES];
    }
}

- (void)audioChannelReleased{
    for (PWChannelPlayer *player in self.players) {
        [player channelStateChanged:PW_CHANNEL_AUDIO occupied:NO];
    }
}

- (void)dealloc{
    NSLog(@"PWChannelManager dealloc");
}

@end
