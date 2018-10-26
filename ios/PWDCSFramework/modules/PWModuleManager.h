//
//  PWModuleManager.h
//  PWDCSFramework
//
//  Created by Nick on 2018/9/21.
//  Copyright © 2018年 hisense. All rights reserved.
//

#import <Foundation/Foundation.h>

@class PWTicketModule;
@class PWChargeModule;
@class PWScreenModule;
@class PWSystemModule;
@class PWNavigationModule;
@class PWVoiceOutputModule;
@class PWAudioPlayerModule;
@class PWVoiceRecognizeModule;
@class PWSpeakControllerModule;
@interface PWModuleManager : NSObject

@property (nonatomic,strong) PWTicketModule *ticketModule;
@property (nonatomic,strong) PWChargeModule *chargeModule;
@property (nonatomic,strong) PWScreenModule *screenModule;
@property (nonatomic,strong) PWSystemModule *systemModule;
@property (nonatomic,strong) PWNavigationModule *navigationModule;
@property (nonatomic,strong) PWVoiceOutputModule *voiceOutputModule;
@property (nonatomic,strong) PWAudioPlayerModule *audioPlayerModule;
@property (nonatomic,strong) PWVoiceRecognizeModule *voiceRecognizeModule;
@property (nonatomic,strong) PWSpeakControllerModule *speakControllerModule;

- (void)initManager;

- (BOOL)isAvailable:(NSString *)nameSpace directive:(NSString *)name;

- (void)processDirective:(NSDictionary *)directive;

- (void)releaseManager;

@end
