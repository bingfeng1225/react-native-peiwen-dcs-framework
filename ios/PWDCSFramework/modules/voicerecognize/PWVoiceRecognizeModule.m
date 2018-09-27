//
//  PWVoiceRecognizeModule.m
//  PWDCSFramework
//
//  Created by Nick on 2018/9/26.
//  Copyright © 2018年 hisense. All rights reserved.
//

#import "PWConstants.h"
#import "PWVoiceRecognizeModule.h"

@implementation PWVoiceRecognizeModule

- (instancetype)init{
    if(self = [super init]){
        self.name = PWVoiceRecognizeModuleName;
        self.nameSpace = PWVoiceRecognizeModuleNameSpace;
    }
    return self;
}

- (void)process:(NSString *)name payload:(NSDictionary *)payload{
    if([PWVoiceRecognizeModuleVoiceRecognize isEqualToString:name]){
        [self fireRecvVoiceRecognize:payload];
    }
}

- (void)fireRecvVoiceRecognize:(NSDictionary *)payload{
    if(self.delegate && [self.delegate respondsToSelector:@selector(onRecvVoiceRecognize:)]){
        [self.delegate onRecvVoiceRecognize:payload];
    }
}

- (void)dealloc{
    NSLog(@"PWVoiceRecognizeModule dealloc");
}
@end
