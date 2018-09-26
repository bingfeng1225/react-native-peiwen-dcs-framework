//
//  PWChannelState.m
//  PWDCSFramework
//
//  Created by Nick on 2018/9/21.
//  Copyright © 2018年 hisense. All rights reserved.
//

#import "PWChannelState.h"

@implementation PWChannelState
- (instancetype)initWithChannelType:(PWChannelType) channelType{
    if(self = [super init]){
        self.channelOccupied = NO;
        self.channelType = channelType;
    }
    return self;
}

- (void)dealloc{
    NSLog(@"PWChannelState dealloc");
}
@end
