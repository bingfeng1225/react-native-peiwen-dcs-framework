//
//  PWModuleManager.m
//  PWDCSFramework
//
//  Created by Nick on 2018/9/21.
//  Copyright © 2018年 hisense. All rights reserved.
//


#import "PWTicketModule.h"
#import "PWChargeModule.h"
#import "PWModuleManager.h"
#import "PWScreenModule.h"
#import "PWSystemModule.h"
#import "PWNavigationModule.h"
#import "PWVoiceOutputModule.h"
#import "PWAudioPlayerModule.h"
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
        
        self.ticketModule = [[PWTicketModule alloc] init];
        [self.modules setObject:self.ticketModule forKey:self.ticketModule.nameSpace];
        
        self.chargeModule = [[PWChargeModule alloc] init];
        [self.modules setObject:self.chargeModule forKey:self.chargeModule.nameSpace];
        
        self.screenModule = [[PWScreenModule alloc] init];
        [self.modules setObject:self.screenModule forKey:self.screenModule.nameSpace];
        
        self.systemModule = [[PWSystemModule alloc] init];
        [self.modules setObject:self.systemModule forKey:self.systemModule.nameSpace];

        self.navigationModule = [[PWNavigationModule alloc] init];
        [self.modules setObject:self.navigationModule forKey:self.navigationModule.nameSpace];
        
        self.audioPlayerModule = [[PWAudioPlayerModule alloc] init];
        [self.modules setObject:self.audioPlayerModule forKey:self.audioPlayerModule.nameSpace];
        
        self.voiceOutputModule = [[PWVoiceOutputModule alloc] init];
        [self.modules setObject:self.voiceOutputModule forKey:self.voiceOutputModule.nameSpace];
        
        self.voiceRecognizeModule = [[PWVoiceRecognizeModule alloc] init];
        [self.modules setObject:self.voiceRecognizeModule forKey:self.voiceRecognizeModule.nameSpace];
    }
    return self;
}


- (void)initManager{
    for (PWBaseModule *module in self.modules.allValues) {
        [module initModule];
    }
}

- (BOOL)isAvailable:(NSString *)nameSpace directive:(NSString *)name{
    PWBaseModule *module = [self.modules objectForKey:nameSpace];
    if(module){
        return [module isAvailableDirective:name];
    }
    return NO;
}

- (void)processDirective:(NSDictionary *)directive{
    NSDictionary *payload = directive[@"payload"];
    NSString *name = directive[@"header"][@"name"];
    NSString *nameSpace = directive[@"header"][@"namespace"];
    PWBaseModule *module = [self.modules objectForKey:nameSpace];
    [module process:name payload:payload];
}

- (void)releaseManager{
    for (PWBaseModule *module in self.modules.allValues) {
        [module releaseModule];
    }
}

- (void)dealloc{
    NSLog(@"PWModuleManager dealloc");
    [self.modules removeAllObjects];
}
@end
