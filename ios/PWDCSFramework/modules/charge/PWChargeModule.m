//
//  PWChargeModule.m
//  PWDCSFramework
//
//  Created by Nick on 2018/10/26.
//  Copyright © 2018 hisense. All rights reserved.
//

#import "PWConstants.h"
#import "PWChargeModule.h"

@implementation PWChargeModule
- (instancetype)init{
    if(self = [super init]){
        self.name = PWChargeModuleName;
        self.nameSpace = PWChargeModuleNameSpace;
    }
    return self;
}

-(BOOL)isAvailableDirective:(NSString *)name{
    return [PWChargeModulePhoneCharge isEqualToString:name];
}

- (void)process:(NSString *)name payload:(NSDictionary *)payload{
    
}

- (void)dealloc{
    NSLog(@"PWChargeModule dealloc");
}

@end
