//
//  PWScreenModule.h
//  PWDCSFramework
//
//  Created by Nick on 2018/9/26.
//  Copyright © 2018年 hisense. All rights reserved.
//

#import "PWBaseModule.h"

@protocol PWScreenModuleDelegate <NSObject>

- (void)onRecvTextCard:(NSDictionary *)payload;

- (void)onRecvListCard:(NSDictionary *)payload;

- (void)onRecvServiceCard:(NSDictionary *)payload;

- (void)onRecvStandardCard:(NSDictionary *)payload;

- (void)onRecvImageListCard:(NSDictionary *)payload;

- (void)onRecvServiceListCard:(NSDictionary *)payload;

@end

@interface PWScreenModule : PWBaseModule

@property (nonatomic,weak) id<PWScreenModuleDelegate> delegate;

@end
