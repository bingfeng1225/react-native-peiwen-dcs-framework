//
//  PWChannelPlayer.m
//  PWDCSFramework
//
//  Created by Nick on 2018/9/21.
//  Copyright © 2018年 hisense. All rights reserved.
//

#import "PWChannelState.h"
#import "PWChannelPlayer.h"
#import "PWChannelParameters.h"
#import <AVFoundation/AVFoundation.h>

@interface PWChannelPlayer ()

@property (nonatomic,strong) AVPlayer *player;
@property (nonatomic,strong) AVURLAsset *urlAssert;
@property (nonatomic,strong) AVPlayerItem *playerItem;

@property (nonatomic,assign) BOOL buffing;
@property (nonatomic,assign) NSInteger bufferPercentage;

@property (nonatomic,strong) PWChannelParameters *parameters;
@end

@implementation PWChannelPlayer
- (instancetype)initWithChannelType:(PWChannelType)channelType{
    if(self = [super init]){
        self.parameters = [[PWChannelParameters alloc] initWithChannelType:channelType];
    }
    return self;
}

#pragma mark Getter And Setter
- (void)setPlayer:(AVPlayer *)player{
    if (_player == player) {
        return;
    }
    if(_player){
        // 暂停
        [_player pause];
        // 替换PlayerItem为nil
        [_player replaceCurrentItemWithPlayerItem:nil];
    }
    _player = player;
}

- (void)setPlayerItem:(AVPlayerItem *)playerItem
{
    if (_playerItem == playerItem) {
        return;
    }
    if (_playerItem) {
        [[NSNotificationCenter defaultCenter] removeObserver:self name:AVPlayerItemDidPlayToEndTimeNotification object:_playerItem];
        
        [_playerItem removeObserver:self forKeyPath:@"status"];
        
        [_playerItem removeObserver:self forKeyPath:@"loadedTimeRanges"];
        
        [_playerItem removeObserver:self forKeyPath:@"playbackLikelyToKeepUp"];
    }
    _playerItem = playerItem;
    if (playerItem) {
        [playerItem addObserver:self forKeyPath:@"status" options:NSKeyValueObservingOptionNew context:nil];
        //缓冲区填充数据
        [playerItem addObserver:self forKeyPath:@"loadedTimeRanges" options:NSKeyValueObservingOptionNew context:nil];
        // 缓冲区有足够数据可以播放了
        [playerItem addObserver:self forKeyPath:@"playbackLikelyToKeepUp" options:NSKeyValueObservingOptionNew context:nil];
        
        [[NSNotificationCenter defaultCenter] addObserver:self selector:@selector(playerCompleted:) name:AVPlayerItemDidPlayToEndTimeNotification object:playerItem];
    }
}



#pragma mark PWChannelParameters操作
- (void)enterBackground{
    if(!self.parameters.enterBackground){
        self.parameters.enterBackground = YES;
        [self parametersChanged];
    }
}

- (void)becomeForeground{
    if(self.parameters.enterBackground){
        self.parameters.enterBackground = NO;
        [self parametersChanged];
    }
}

- (void)audioRecordStarted{
    if(!self.parameters.audioRecordStarted){
        self.parameters.audioRecordStarted = YES;
        [self parametersChanged];
    }
}

- (void)audioRecordFinished{
    if(self.parameters.audioRecordStarted){
        self.parameters.audioRecordStarted = NO;
        [self parametersChanged];
    }
}

- (void)dialogChannelOccupied{
    if(!self.parameters.dialogChannelOccupied){
        self.parameters.dialogChannelOccupied = YES;
        [self parametersChanged];
    }
}
- (void)dialogChannelReleased{
    if(self.parameters.dialogChannelOccupied){
        self.parameters.audioRecordStarted = NO;
        [self parametersChanged];
    }
}

- (void)channelStateChanged:(PWChannelType)type occupied:(BOOL)occupied{
    PWChannelState *state = [self.parameters findChannelState:type];
    if(state.channelOccupied != occupied){
        state.channelOccupied = occupied;
        [self parametersChanged];
    }
}

- (void)parametersChanged {
//    if (this.parameters.isConditionsMeetRequirements()) {
//        if ([self canResume]) {
//            [self startTimer];
//            this.startTimer();
//            this.mediaPlayer.start();
//            this.setPlayerState(PlayerState.PLAYER_PLAYING);
//        }
//    } else {
//        if (this.canPause()) {
//            this.stopTimer();
//            this.mediaPlayer.pause();
//            this.setPlayerState(PlayerState.PLAYER_PAUSED);
//        }
//    }
}

#pragma mark 状态校验接口


#pragma mark 播放器周期接口
//- (void)load:(NSString *) url {
//    if([url hasPrefix:@"http"]){
//        self.urlAssert = [AVURLAsset assetWithURL:[NSURL URLWithString:url]];
//    }else{
//        self.urlAssert = [AVURLAsset assetWithURL:[NSURL fileURLWithPath:url]];
//    }
//    // 初始化playerItem
//    self.playerItem = [AVPlayerItem playerItemWithAsset:self.urlAssert];
//    // 每次都重新创建Player，替换replaceCurrentItemWithPlayerItem:，该方法阻塞线程
//    self.player = [AVPlayer playerWithPlayerItem:self.playerItem];
//
//    self.playerState = PVR_EXPLAYER_PREPAREING;
//}
//
//- (void)stop {
//    if (this.hasPlayContent()) {
//        this.stopTimer();
//        this.stoped = true;
//        this.mediaPlayer.reset();
//        this.firePlayerStoped();
//    }
//}
//
//- (void)unload {
//    this.stopTimer();
//    this.mediaPlayer.reset();
//    this.ended = false;
//    this.stoped = false;
//    this.buffing = false;
//    this.seekTime = 0;
//    this.setErrorType(ErrorType.MEDIA_ERROR_NONE);
//    this.setPlayerState(PlayerState.PLAYER_IDLE);
//}
//
//- (void)rewind {
//    if (this.canSeek()) {
//        int cur = mediaPlayer.getCurrentPosition();
//        if (cur > 5 * 1000) {
//            seekToTime((cur - 5 * 1000));
//        } else {
//            seekToTime(0);
//        }
//    }
//}
//
//- (void)fastForward {
//    if (this.canSeek()) {
//        int cur = mediaPlayer.getCurrentPosition();
//        int total = mediaPlayer.getDuration();
//        if (total - cur > 5 * 1000) {
//            seekToTime((cur + 5 * 1000));
//        } else {
//            seekToTime(total);
//        }
//    }
//}
//
//- (void)seekToTime:(NSInteger)time {
//    if (this.canSeek()) {
//        mediaPlayer.seekTo(msec);
//    }
//}
//
//- (void)pause {
//    if (!this.parameters.pauseByUser) {
//        this.parameters.pauseByUser = true;
//        this.parametersChanged();
//    }
//}
//
//- (void)resume {
//    if (!this.parameters.pauseByUser) {
//        this.parameters.pauseByUser = false;
//        this.parametersChanged();
//    }
//}
//
//#pragma mark 视频监听接口
//- (void)playerCompleted:(NSNotification *)notification{
//
//}
//- (void)videoPrepared:(AVPlayerItem *)playerItem {
//    self.playerState = PVR_EXPLAYER_READY;
//    if(self.seekTime > 0){
//        [self seekToTime:self.seekTime];
//    }
//    if ([self.parameters isConditionsMeetRequirements]) {
//        startTimer();
//        [self startTimer];
//        [self.player play];
//        this.setPlayerState(PlayerState.PLAYER_PLAYING);
//    } else {
//        [self.player pause];
//        this.setPlayerState(PlayerState.PLAYER_PAUSED);
//    }
//}
//
//- (void)observeValueForKeyPath:(NSString *)keyPath ofObject:(id)object change:(NSDictionary *)change context:(void *)context {
//    if (object == self.player.currentItem) {
//        if ([keyPath isEqualToString:@"status"]) {
//            if(!self.isPrepareProcessed){
//                if (self.player.currentItem.status == AVPlayerItemStatusReadyToPlay) {
//                    [self videoPrepared:self.player.currentItem];
//                } else if (self.player.currentItem.status == AVPlayerItemStatusFailed || self.player.currentItem.status == AVPlayerItemStatusUnknown) {
//                    PVRLog(@"%@",self.player.currentItem.error);
//                    self.errorCode = PVR_CODEC_ERROR_MALFORMED;
//                }
//            }
//        } else if ([keyPath isEqualToString:@"loadedTimeRanges"]) {
//            // 计算缓冲进度
//            NSTimeInterval timeInterval = [self availableDuration];
//            CGFloat totalDuration       = CMTimeGetSeconds(self.playerItem.duration);
//            self.bufferPercentage = (NSInteger)(timeInterval * 100 / totalDuration);
//        } else if ([keyPath isEqualToString:@"playbackLikelyToKeepUp"]) {
//            PVRLog(@"playbackLikelyToKeepUp ----------------- ");
//            if(self.playerItem.isPlaybackLikelyToKeepUp){
//                // 当缓冲好的时候
//                PVRLog(@"playbackLikelyToKeepUp ----------------- 0");
//                if(self.buffing){
//                    self.buffing = NO;
//                    if(self.playerDelegate &&  [self.playerDelegate respondsToSelector:@selector(playerBufferingEnded)]){
//                        [self.playerDelegate playerBufferingEnded];
//                    }
//                }
//            }else{
//                PVRLog(@"playbackLikelyToKeepUp ----------------- 1");
//                if(!self.buffing){
//                    self.buffing = true;
//                    if(self.playerDelegate && [self.playerDelegate respondsToSelector:@selector(playerBufferingStart)]){
//                        [self.playerDelegate playerBufferingStart];
//                    }
//                }
//            }
//        }
//    }
//}
//
//#pragma mark 事件触发器
//
//#pragma mark 功能函数
///**
// *  计算缓冲进度
// *
// *  @return 缓冲进度
// */
//- (NSTimeInterval)availableDuration {
//    NSArray *loadedTimeRanges = [[_player currentItem] loadedTimeRanges];
//    CMTimeRange timeRange     = [loadedTimeRanges.firstObject CMTimeRangeValue];// 获取缓冲区域
//    float startSeconds        = CMTimeGetSeconds(timeRange.start);
//    float durationSeconds     = CMTimeGetSeconds(timeRange.duration);
//    NSTimeInterval result     = startSeconds + durationSeconds;// 计算缓冲总进度
//    return result;
//}

- (void)dealloc{
    NSLog(@"PWChannelPlayer dealloc");
}

@end
