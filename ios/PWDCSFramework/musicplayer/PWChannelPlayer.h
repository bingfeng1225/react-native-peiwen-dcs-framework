//
//  PWChannelPlayer.h
//  PWDCSFramework
//
//  Created by Nick on 2018/9/21.
//  Copyright © 2018年 hisense. All rights reserved.
//

#import "PWEnum.h"

@class PWChannelPlayer;

@protocol PWChannelPlayerDelegate <NSObject>

- (void)onPlayerStoped:(PWChannelPlayer *) player object:(id)object;

- (void)onPlayerCompleted:(PWChannelPlayer *) player object:(id)object;

- (void)onPlayerBufferingStart:(PWChannelPlayer *) player object:(id)object;

- (void)onPlayerBufferingEnded:(PWChannelPlayer *) player object:(id)object;

- (void)onBufferingUpdated:(PWChannelPlayer *) player object:(id)object bufferPercentage:(NSInteger)bufferPercentage;

- (void)onPlayerErrorOccurred:(PWChannelPlayer *) player object:(id)object error:(PWPlayerErrorType)error;

- (void)onPlayerStateChanged:(PWChannelPlayer *) player object:(id)object state:(PWPlayerState) state;

- (void)onPlayerProgressChanged:(PWChannelPlayer *) player object:(id)object position:(NSInteger) position duration:(NSInteger)duration;

@end

@interface PWChannelPlayer : NSObject

@property (nonatomic,assign) BOOL ended;
@property (nonatomic,assign) BOOL stoped;
@property (nonatomic,assign) BOOL buffing;
@property (nonatomic,assign) NSInteger seekTime;
@property (nonatomic,assign) PWPlayerState playerState;
@property (nonatomic,assign) PWPlayerErrorType errorType;
@property (nonatomic,assign,readonly) NSInteger position;
@property (nonatomic,assign,readonly) NSInteger duration;
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
