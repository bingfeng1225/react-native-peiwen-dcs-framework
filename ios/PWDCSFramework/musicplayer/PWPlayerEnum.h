//
//  PVREXEnum.h
//  Picovr
//
//  Created by Nick on 2017/4/15.
//  Copyright © 2017年 PicoVR. All rights reserved.
//
#ifndef PWEXEnum_h
#define PWEXEnum_h

#import <Foundation/Foundation.h>

/**
 *  播放器状态
 *
 *  peiwen
 */
typedef NS_ENUM(NSInteger, PWPlayerState) {
    PW_EXPLAYER_UNKNOWN,
    PW_EXPLAYER_UNREADY,
    PW_EXPLAYER_PARSEURL,
    PW_EXPLAYER_PREPAREING,
    PW_EXPLAYER_READY,
    PW_EXPLAYER_PLAYING,
    PW_EXPLAYER_PAUSED
};

/**
 *  播放器状态
 *
 *  peiwen
 */
typedef NS_ENUM(NSInteger, PWPlayerErrorType) {
    PW_CODEC_ERROR_NONE,
    PW_CODEC_ERROR_WLAN,
    PW_CODEC_ERROR_MALFORMED,
    PW_CODEC_ERROR_RECONTENT,
    PW_CODEC_ERROR_URL_DECODE
};

/**
 *  通道类型
 *
 *  peiwen
 */
typedef NS_ENUM(NSInteger, PWChannelType) {
    PW_CHANNEL_AUDIO,
    PW_CHANNEL_SPEAK
};


#endif /* PWEXEnum_h */
