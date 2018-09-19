//
//  PWDCSFramework.m
//  PWDCSFramework
//
//  Created by Nick on 2018/9/14.
//  Copyright © 2018年 hisense. All rights reserved.
//

#if __has_include(<React/RCTConvert.h>)
#import <React/RCTConvert.h>
#elif __has_include("RCTConvert.h")
#import "RCTConvert.h"
#else
#import "React/RCTConvert.h"   // Required when used as a Pod in a Swift project
#endif
#import "PWDCSFramework.h"

@implementation PWDCSFramework

RCT_EXPORT_MODULE();

@end
