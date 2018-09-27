//
//  PWUUIDManager.m
//  PWDCSFramework
//
//  Created by Nick on 2018/9/26.
//  Copyright © 2018年 hisense. All rights reserved.
//

#import "PWUUIDManager.h"

@implementation PWUUIDManager

-(void)initManager{
    self.lastSession = @"";
}

- (NSString *)createActiveRequest {
    self.activeRequest = [NSUUID UUID].UUIDString;
    return self.activeRequest;
}

- (BOOL)isActiveRequest:(NSString *)uuid{
    return [uuid isEqualToString:self.activeRequest];
}

- (void)releaseManager{
    
}

- (void)dealloc{
    NSLog(@"PWUUIDManager dealloc");
}
@end
