//
//  PWBaseModule.h
//  PWDCSFramework
//
//  Created by Nick on 2018/9/21.
//  Copyright © 2018年 hisense. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface PWBaseModule : NSObject

@property (nonatomic,weak) NSString *name;
@property (nonatomic,weak) NSString *nameSpace;

- (void)initModule;

- (BOOL)isAvailableDirective:(NSString *)name;

- (void)process:(NSString *)name payload:(NSDictionary *)payload;

- (void)releaseModule;

@end
