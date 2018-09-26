//
//  PWUUIDManager.h
//  PWDCSFramework
//
//  Created by Nick on 2018/9/26.
//  Copyright © 2018年 hisense. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface PWUUIDManager : NSObject
@property (nonatomic,copy) NSString *lastSession;
@property (nonatomic,copy) NSString *activeRequest;

- (void)initManager;

- (NSString *)createActiveRequest;

- (BOOL)isActiveRequest:(NSString *)uuid;

- (void)releaseManager;
@end
