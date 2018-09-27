//
//  PWDCSFramework.h
//  PWDCSFramework
//
//  Created by Nick on 2018/9/14.
//  Copyright © 2018年 hisense. All rights reserved.
//

#if __has_include(<React/RCTAssert.h>)
#import <React/RCTBridgeModule.h>
#import <React/RCTEventEmitter.h>
#else
#import "RCTBridgeModule.h"
#import "RCTEventEmitter.h"
#endif


@interface PWDCSFramework : RCTEventEmitter

@end
