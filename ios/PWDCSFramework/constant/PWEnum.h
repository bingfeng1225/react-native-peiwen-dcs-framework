//
//  PVREXEnum.h
//  Picovr
//
//  Created by Nick on 2017/4/15.
//  Copyright © 2017年 PicoVR. All rights reserved.
//
#ifndef PWEnum_h
#define PWEnum_h

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


/**
 *  通道类型
 *
 *  peiwen
 */
typedef NS_ENUM(NSInteger, PWReactEventType) {
    TEXT_INPUT_STARTED,
    TEXT_INPUT_FAILURED,
    INPUT_EVENT_SUCCESSED,
    VOICE_RECOGNIZE_STARTED,
    VOICE_RECOGNIZE_FAILURED,
    VOICE_RECOGNIZE_SUCCESSED,
    ON_RECEIVE_TEXT_CARD,
    ON_RECEIVE_LIST_CARD,
    ON_RECEIVE_SERVICE_CARD,
    ON_RECEIVE_STANDARD_CARD,
    ON_RECEIVE_IMAGE_LIST_CARD,
    ON_RECEIVE_SERVICE_LIST_CARD,
};

#endif /* PWEnum_h */
