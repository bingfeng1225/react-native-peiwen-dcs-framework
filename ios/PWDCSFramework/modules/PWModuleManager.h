//
//  PWModuleManager.h
//  PWDCSFramework
//
//  Created by Nick on 2018/9/21.
//  Copyright © 2018年 hisense. All rights reserved.
//

#import <Foundation/Foundation.h>

@class PWBaseModule;
@interface PWModuleManager : NSObject

@property (nonatomic,strong) PWBaseModule *base1;
@property (nonatomic,strong) PWBaseModule *base2;
@property (nonatomic,strong) PWBaseModule *base3;

- (void)initManager;

- (void)processMessage:(id)message;

- (void)releaseManager;

@end
