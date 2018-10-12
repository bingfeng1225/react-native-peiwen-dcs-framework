//
//  PWScreenModule.m
//  PWDCSFramework
//
//  Created by Nick on 2018/9/26.
//  Copyright © 2018年 hisense. All rights reserved.
//

#import "PWConstants.h"
#import "PWScreenModule.h"


@implementation PWScreenModule

- (instancetype)init{
    if(self = [super init]){
        self.name = PWScreenModuleName;
        self.nameSpace = PWScreenModuleNameSpace;
    }
    return self;
}

-(BOOL)isAvailableDirective:(NSString *)name{
    return [PWScreenModuleRenderCard isEqualToString:name];
}

- (void)process:(NSString *)name payload:(NSDictionary *)payload{
    if(![PWScreenModuleRenderCard isEqualToString:name]){
        return;
    }
    NSString *type = payload[@"type"];
    if([PWScreenModuleRenderTextCard isEqualToString:type]){
        [self fireRecvTextCard:payload];
    }else if([PWScreenModuleRenderListCard isEqualToString:type]){
        [self fireRecvListCard:payload];
    }else if([PWScreenModuleRenderServiceCard isEqualToString:type]){
         [self fireRecvServiceCard:payload];
    }else if([PWScreenModuleRenderStandardCard isEqualToString:type]){
        [self fireRecvStandardCard:payload];
    }else if([PWScreenModuleRenderImageListCard isEqualToString:type]){
        [self fireRecvImageListCard:payload];
    }else if([PWScreenModuleRenderServiceListCard isEqualToString:type]){
        [self fireRecvServiceListCard:payload];
    }
}

- (void)fireRecvTextCard:(NSDictionary *)payload{
    if(self.delegate && [self.delegate respondsToSelector:@selector(onRecvTextCard:)]){
        [self.delegate onRecvTextCard:payload];
    }
}

- (void)fireRecvListCard:(NSDictionary *)payload{
    if(self.delegate && [self.delegate respondsToSelector:@selector(onRecvListCard:)]){
        [self.delegate onRecvListCard:payload];
    }
}

- (void)fireRecvServiceCard:(NSDictionary *)payload{
    if(self.delegate && [self.delegate respondsToSelector:@selector(onRecvServiceCard:)]){
        [self.delegate onRecvServiceCard:payload];
    }
}

- (void)fireRecvStandardCard:(NSDictionary *)payload{
    if(self.delegate && [self.delegate respondsToSelector:@selector(onRecvStandardCard:)]){
        [self.delegate onRecvStandardCard:payload];
    }
}

- (void)fireRecvImageListCard:(NSDictionary *)payload{
    if(self.delegate && [self.delegate respondsToSelector:@selector(onRecvImageListCard:)]){
        [self.delegate onRecvImageListCard:payload];
    }
}

- (void)fireRecvServiceListCard:(NSDictionary *)payload{
    if(self.delegate && [self.delegate respondsToSelector:@selector(onRecvServiceListCard:)]){
        [self.delegate onRecvServiceListCard:payload];
    }
}

- (void)dealloc{
    NSLog(@"PWScreenModule dealloc");
}
@end
