//
//  PWSystemModule.m
//  PWDCSFramework
//
//  Created by Nick on 2018/9/26.
//  Copyright © 2018年 hisense. All rights reserved.
//

#import "PWConstants.h"
#import "PWSystemModule.h"

@implementation PWSystemModule

- (instancetype)init{
    if(self = [super init]){
        self.name = PWSystemModuleName;
        self.nameSpace = PWSystemModuleNameSpace;
    }
    return self;
}

- (void)process:(NSString *)name payload:(NSDictionary *)payload{
    if([PWSystemModuleSetSessionid isEqualToString:name]){
        [self fireSessionChanged:payload[@"sessionid"]];
    }
}

- (void)fireSessionChanged:(NSString *)session{
    if(self.delegate && [self.delegate respondsToSelector:@selector(onSessionChanged:)]){
        [self.delegate onSessionChanged:session];
    }
}

- (void)dealloc{
    NSLog(@"PWSystemModule dealloc");
}
@end
