//
//  PWModuleManager.m
//  PWDCSFramework
//
//  Created by Nick on 2018/9/21.
//  Copyright © 2018年 hisense. All rights reserved.
//

#import "PWBaseModule.h"
#import "PWModuleManager.h"

@interface PWModuleManager ()
@property (nonatomic,strong) NSMutableDictionary *modules;
@end

@implementation PWModuleManager


- (instancetype)init{
    if(self = [super init]){
        //TODO 创建各个端能力对象并加入字典
        self.modules = [NSMutableDictionary dictionary];
        self.base1 = [[PWBaseModule alloc] init];
        self.base1.nameSpace = @"base1";
        [self.modules setObject:self.base1 forKey:self.base1.nameSpace];
        
        self.base2 = [[PWBaseModule alloc] init];
        self.base2.nameSpace = @"base2";
        [self.modules setObject:self.base2 forKey:self.base2.nameSpace];
        
        self.base3 = [[PWBaseModule alloc] init];
        self.base3.nameSpace = @"base3";
        [self.modules setObject:self.base3 forKey:self.base3.nameSpace];
    }
    return self;
}


- (void)initManager{
    for (PWBaseModule *module in self.modules.allValues) {
        [module initModule];
    }
}

- (void)processMessage:(id)message{
    
}

- (void)releaseManager{
    for (PWBaseModule *module in self.modules.allValues) {
        [module initModule];
    }
}

- (void)dealloc{
    NSLog(@"PWModuleManager dealloc");
    [self.modules removeAllObjects];
}
@end
