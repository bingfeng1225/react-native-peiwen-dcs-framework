//
//  PWTicketModule.h
//  PWDCSFramework
//
//  Created by Nick on 2018/10/26.
//  Copyright © 2018 hisense. All rights reserved.
//

#import "PWBaseModule.h"

@protocol PWTicketModuleDelegate <NSObject>

- (void)onRecvTrainPayload:(NSDictionary *)payload;

- (void)onRecvFlightPayload:(NSDictionary *)payload;

@end

@interface PWTicketModule : PWBaseModule

@property (nonatomic,weak) id<PWTicketModuleDelegate> delegate;

@end

