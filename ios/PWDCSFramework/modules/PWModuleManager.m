//
//  PWModuleManager.m
//  PWDCSFramework
//
//  Created by Nick on 2018/9/21.
//  Copyright © 2018年 hisense. All rights reserved.
//


#import "PWModuleManager.h"
#import "PWScreenModule.h"
#import "PWSystemModule.h"
#import "PWVoiceRecognizeModule.h"
#import "PWSpeakControllerModule.h"

@interface PWModuleManager ()
@property (nonatomic,strong) NSMutableDictionary *modules;
@end

@implementation PWModuleManager


- (instancetype)init{
    if(self = [super init]){
        //TODO 创建各个端能力对象并加入字典
        self.modules = [NSMutableDictionary dictionary];
        self.screenModule = [[PWScreenModule alloc] init];
        [self.modules setObject:self.screenModule forKey:self.screenModule.nameSpace];
        
        self.systemModule = [[PWSystemModule alloc] init];
        [self.modules setObject:self.systemModule forKey:self.systemModule.nameSpace];

        self.voiceRecognizeModule = [[PWVoiceRecognizeModule alloc] init];
        [self.modules setObject:self.voiceRecognizeModule forKey:self.voiceRecognizeModule.nameSpace];
        
//        self.speakControllerModule = [[PWSpeakControllerModule alloc] init];
//        [self.modules setObject:self.speakControllerModule forKey:self.speakControllerModule.nameSpace];
    }
    return self;
}


- (void)initManager{
    for (PWBaseModule *module in self.modules.allValues) {
        [module initModule];
    }
}

- (void)processDirective:(NSDictionary *)directive{
    NSDictionary *payload = directive[@"payload"];
    NSString *name = directive[@"header"][@"name"];
    NSString *namespace = directive[@"header"][@"namespace"];
    
    PWBaseModule *module = [self.modules objectForKey:namespace];
    [module process:name payload:payload];
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
