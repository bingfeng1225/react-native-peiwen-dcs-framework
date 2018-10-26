//
//  PWNavigationModule.h
//  PWDCSFramework
//
//  Created by Nick on 2018/10/26.
//  Copyright © 2018 hisense. All rights reserved.
//

#import "PWBaseModule.h"

@protocol PWNavigationModuleDelegate <NSObject>

- (void)onRecvNavigationPayload:(NSDictionary *)payload;

@end

@interface PWNavigationModule : PWBaseModule

@property (nonatomic,weak) id<PWNavigationModuleDelegate> delegate;

@end



