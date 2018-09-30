//
//  PWAudioPlayerModule.h
//  PWDCSFramework
//
//  Created by Nick on 2018/9/30.
//  Copyright © 2018年 hisense. All rights reserved.
//

#import "PWBaseModule.h"

@protocol PWAudioPlayerModuleDelegate <NSObject>

- (void)onAudioChannelOccupied;

- (void)onAudioChannelReleased;

@end

@class PWChannelPlayer;

@interface PWAudioPlayerModule : PWBaseModule

@property (nonatomic,strong) PWChannelPlayer* player;
@property (nonatomic,weak) id<PWAudioPlayerModuleDelegate> delegate;

@end
