//
//  PWSystemModule.h
//  PWDCSFramework
//
//  Created by Nick on 2018/9/26.
//  Copyright © 2018年 hisense. All rights reserved.
//

#import "PWBaseModule.h"
@protocol PWSystemModuleDelegate <NSObject>

- (void)onSessionChanged:(NSString *)session;

@end

@interface PWSystemModule : PWBaseModule

@property (nonatomic,weak) id<PWSystemModuleDelegate> delegate;

@end
