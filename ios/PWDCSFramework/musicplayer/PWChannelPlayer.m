//
//  PWChannelPlayer.m
//  PWDCSFramework
//
//  Created by Nick on 2018/9/21.
//  Copyright © 2018年 hisense. All rights reserved.
//

#import "PWChannelPlayer.h"
#import "PWChannelParameters.h"

@interface PWChannelPlayer ()
@property (nonatomic,strong) PWChannelParameters *parameters;
@end

@implementation PWChannelPlayer
- (instancetype)initWithChannelType:(PWChannelType)channelType{
    if(self = [super init]){
        self.parameters = [[PWChannelParameters alloc] initWithChannelType:channelType];
    }
    return self;
}

- (void)enterBackground{
    
}

- (void)becomeForeground{
    
}

- (void)audioRecordStarted{
    
}

- (void)audioRecordFinished{
    
}

- (void)dialogChannelReleased{
    
}

- (void)dialogChannelOccupied{
    
}

- (void)speakChannelReleased{
    
}

- (void)speakChannelOccupied{
    
}

- (void)audioChannelReleased{
    
}

- (void)audioChannelOccupied{
    
}


- (void)dealloc{
    NSLog(@"PWChannelPlayer dealloc");
}

@end
