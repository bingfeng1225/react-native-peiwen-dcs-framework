//
//  PWMessageQueue.h
//  PWDCSFramework
//
//  Created by Nick on 2018/9/21.
//  Copyright © 2018年 hisense. All rights reserved.
//

#import <Foundation/Foundation.h>

@protocol PWMessageQueueDelegate <NSObject>
- (void)processMessage:(id)message;
@end

@interface PWMessageQueue : NSObject

@property (nonatomic,weak) id<PWMessageQueueDelegate> messageQueueDelegate;

- (void)initQueue;

- (void)releaseQueue;

- (void)insertMessage:(id)message;

@end
