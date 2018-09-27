//
//  PWChannelParameters.m
//  PWDCSFramework
//
//  Created by Nick on 2018/9/21.
//  Copyright © 2018年 hisense. All rights reserved.
//

#import "PWChannelState.h"
#import "PWChannelParameters.h"

@interface PWChannelParameters ()

@property (nonatomic,assign) PWChannelType channelType;

@property (nonatomic,strong) NSMutableArray *channelStates;

@end

@implementation PWChannelParameters
-(instancetype)initWithChannelType:(PWChannelType) channelType{
    if(self = [super init]){
        self.duration = 0;
        self.position = 0;
        self.playObject = nil;
        self.enterBackground = false;
        self.audioRecordStarted = false;
        self.dialogChannelOccupied = false;
        self.channelType = channelType;
        [self initChannelStates];
    }
    return self;
}

- (void)initChannelStates {
    self.channelStates = [NSMutableArray array];
    [self.channelStates addObject:[[PWChannelState alloc] initWithChannelType:PW_CHANNEL_AUDIO]];
    [self.channelStates addObject:[[PWChannelState alloc] initWithChannelType:PW_CHANNEL_SPEAK]];
}

- (BOOL)isConditionsMeetRequirements {
    if (self.enterBackground) {
        return NO;
    } else if (self.audioRecordStarted) {
        return NO;
    } else if (self.dialogChannelOccupied) {
        return NO;
    }
    return [self isConditionOfChannelState];
}

- (BOOL)isConditionOfChannelState {
    PWChannelState *channelState  = [self findHeightChannnelState];
    if (self.channelType >= channelState.channelType) {
        return true;
    }
    return false;
}

- (PWChannelState *)findHeightChannnelState {
    PWChannelState *channelState = nil;
    for (PWChannelState *item in self.channelStates) {
        if(!channelState){
            channelState = item;
        }else if(item.channelType > channelState.channelType){
            channelState = item;
        }
    }
    return channelState;
}
- (void)dealloc{
    NSLog(@"PWChannelParameters dealloc");
}
@end
