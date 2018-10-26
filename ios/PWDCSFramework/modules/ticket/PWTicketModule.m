//
//  PWTicketModule.m
//  PWDCSFramework
//
//  Created by Nick on 2018/10/26.
//  Copyright © 2018 hisense. All rights reserved.
//

#import "PWConstants.h"
#import "PWTicketModule.h"

@implementation PWTicketModule
- (instancetype)init{
    if(self = [super init]){
        self.name = PWTicketModuleName;
        self.nameSpace = PWTicketModuleNameSpace;
    }
    return self;
}

-(BOOL)isAvailableDirective:(NSString *)name{
    return ([PWTicketModuleTrain isEqualToString:name] || [PWTicketModuleFlight isEqualToString:name]);
}

- (void)process:(NSString *)name payload:(NSDictionary *)payload{
    if([PWTicketModuleTrain isEqualToString:name]){
        [self fireRecvTrainPayload:payload];
    }else if([PWTicketModuleFlight isEqualToString:name]){
        [self fireRecvFlightPayload:payload];
    }
}

- (void)fireRecvTrainPayload:(NSDictionary *)payload{
    if(self.delegate && [self.delegate respondsToSelector:@selector(onRecvTrainPayload:)]){
        [self.delegate onRecvTrainPayload:payload];
    }
}

- (void)fireRecvFlightPayload:(NSDictionary *)payload{
    if(self.delegate && [self.delegate respondsToSelector:@selector(onRecvFlightPayload:)]){
        [self.delegate onRecvFlightPayload:payload];
    }
}

- (void)dealloc{
    NSLog(@"PWTicketModule dealloc");
}
@end
