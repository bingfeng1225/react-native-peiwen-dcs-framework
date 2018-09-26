//
//  PWMessageQueue.m
//  PWDCSFramework
//
//  Created by Nick on 2018/9/21.
//  Copyright © 2018年 hisense. All rights reserved.
//

#import "PWMessageQueue.h"

@interface PWMessageQueue ()
@property (nonatomic,strong) NSOperationQueue *queue;
@end

@implementation PWMessageQueue

- (void)initQueue{
    if(!self.queue){
        self.queue = [[NSOperationQueue alloc] init];
        self.queue.maxConcurrentOperationCount = 1;
    }
}

- (void)releaseQueue{
    if(self.queue){
        [self.queue cancelAllOperations];
        self.queue = nil;
    }
}

- (void)insertMessage:(id)message{
    NSInvocationOperation *operation = [[NSInvocationOperation alloc] initWithTarget:self selector:@selector(processOperation:) object:message];
    [self.queue addOperation:operation];
}

- (void)processOperation:(id)message {
    if(self.messageQueueDelegate && [self.messageQueueDelegate respondsToSelector:@selector(processMessage:)]){
        [self.messageQueueDelegate processMessage:message];
    }
}

- (void)dealloc{
    NSLog(@"PWMessageQueue dealloc");
}
@end
