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
        self.playObject = nil;
        self.pauseByUser = NO;
        self.enterBackground = NO;
        self.audioRecordStarted = NO;
        self.dialogChannelOccupied = NO;
        self.channelType = channelType;
        self.channelStates = [NSMutableArray array];
        [self.channelStates addObject:[[PWChannelState alloc] initWithChannelType:PW_CHANNEL_AUDIO]];
        [self.channelStates addObject:[[PWChannelState alloc] initWithChannelType:PW_CHANNEL_SPEAK]];
    }
    return self;
}

- (PWChannelState *)findChannelState:(PWChannelType)channelType {
    PWChannelState *result = nil;
    for (PWChannelState *state in self.channelStates) {
        if(channelType == state.channelType){
            result = state;
            break;
        }
    }
    return result;
}


- (BOOL)isConditionsMeetRequirements {
    if (self.pauseByUser) {
        return NO;
    } else if (self.enterBackground) {
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
        return YES;
    }
    return NO;
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
