//
//  PWNavigationModule.m
//  PWDCSFramework
//
//  Created by Nick on 2018/10/26.
//  Copyright © 2018 hisense. All rights reserved.
//

#import "PWConstants.h"
#import "PWNavigationModule.h"

@implementation PWNavigationModule
- (instancetype)init{
    if(self = [super init]){
        self.name = PWNavigationModuleName;
        self.nameSpace = PWNavigationModuleNameSpace;
    }
    return self;
}

-(BOOL)isAvailableDirective:(NSString *)name{
    return [PWNavigationModuleNavigation isEqualToString:name];
}

- (void)process:(NSString *)name payload:(NSDictionary *)payload{
    if([PWNavigationModuleNavigation isEqualToString:name]){
        [self fireRecvNavigationPayload:payload];
    }
}

- (void)fireRecvNavigationPayload:(NSDictionary *)payload{
    if(self.delegate && [self.delegate respondsToSelector:@selector(onRecvNavigationPayload:)]){
        [self.delegate onRecvNavigationPayload:payload];
    }
}

- (void)dealloc{
    NSLog(@"PWNavigationModule dealloc");
}
@end
