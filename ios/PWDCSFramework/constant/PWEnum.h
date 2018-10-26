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
    PW_PLAYER_UNKNOWN,
    PW_PLAYER_IDLE,
    PW_PLAYER_PREPAREING,
    PW_PLAYER_PREPARED,
    PW_PLAYER_PLAYING,
    PW_PLAYER_PAUSED
};

/**
 *  播放器状态
 *
 *  peiwen
 */
typedef NS_ENUM(NSInteger, PWPlayerErrorType) {
    PW_PLAYER_ERROR_NONE,
    PW_PLAYER_ERROR_UNKNOWN
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
    ON_RECV_NAVIGATION,
    ON_RECV_TRAIN_TICKET,
    ON_RECV_FLIGHT_TICKET,
    ON_RECEIVE_TEXT_CARD,
    ON_RECEIVE_LIST_CARD,
    ON_RECEIVE_SERVICE_CARD,
    ON_RECEIVE_STANDARD_CARD,
    ON_RECEIVE_IMAGE_LIST_CARD,
    ON_RECEIVE_SERVICE_LIST_CARD,
};

#endif /* PWEnum_h */
