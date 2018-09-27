//
//  PWBaseModule.m
//  PWDCSFramework
//
//  Created by Nick on 2018/9/21.
//  Copyright © 2018年 hisense. All rights reserved.
//

#import "PWBaseModule.h"

@implementation PWBaseModule

- (void)initModule{
    
}

- (void)process:(NSString *)name payload:(NSDictionary *)payload{
    [self doesNotRecognizeSelector:@selector(process:payload:)];
}

- (void)releaseModule{
    
}

- (void)dealloc{
    NSLog(@"PWBaseModule dealloc");
}

@end
