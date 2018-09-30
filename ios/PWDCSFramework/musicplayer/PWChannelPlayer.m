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

@property (nonatomic, strong) NSTimer *playerTracker;
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
- (NSInteger)position{
    NSInteger position = 0;
    if(self.player && self.playerItem && [self isPrepared]){
        if(self.ended){
            position = roundf(CMTimeGetSeconds(self.playerItem.duration));
        }else{
            position = roundf(CMTimeGetSeconds(self.playerItem.currentTime));
        }
    }
    return position;
}
- (NSInteger)duration{
    NSInteger duration = 0;
    if(self.player && self.playerItem && [self isPrepared]){
        duration = roundf(CMTimeGetSeconds(self.playerItem.duration));
    }
    return duration;
}

- (void)setBufferPercentage:(NSInteger)bufferPercentage{
    if(_bufferPercentage != bufferPercentage){
        _bufferPercentage = bufferPercentage;
        [self fireBufferingUpdated];
    }
}
-(void)setPlayerState:(PWPlayerState)playerState{
    if (_playerState != playerState) {
        _playerState = playerState;
        [self firePlayerStateChanged];
    }
}

- (void)setErrorType:(PWPlayerErrorType)errorType{
    if(_errorType != errorType){
        _errorType = errorType;
        [self firePlayerErrorOccurred];
    }
}

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
    if ([self.parameters isConditionsMeetRequirements]) {
        if ([self canResume]) {
            [self startPlayerTracker];
            [self.player play];
            self.playerState = PW_PLAYER_PLAYING;
        }
    } else {
        if ([self canPause]) {
            [self stopPlayerTracker];
            [self.player pause];
            self.playerState = PW_PLAYER_PAUSED;
        }
    }
}

#pragma mark 状态校验接口
- (BOOL)canSeek {
    if (self.player && self.playerItem && [self isPrepared] && !self.ended && ![self isError] && !self.stoped) {
        return true;
    }
    return false;
}

- (BOOL)canPause {
    if (self.player && self.playerItem && [self isPrepared] && ![self isError] && !self.ended && !self.stoped && ![self isPaused]) {
        return true;
    }
    return false;
}

- (BOOL)canResume {
    if (self.player && self.playerItem && [self isPrepared] && ![self isError] && !self.ended && !self.stoped && ![ self isPlaying]) {
        return true;
    }
    return false;
}

- (BOOL)isError {
    return (self.errorType != PW_PLAYER_ERROR_NONE);
}

- (BOOL)isPaused {
    return (self.playerState == PW_PLAYER_PAUSED);
}

- (BOOL)isPlaying {
    return (self.playerState == PW_PLAYER_PLAYING);
}

- (BOOL)isPrepared {
    return (self.player && self.playerItem && self.playerState >= PW_PLAYER_PREPARED);
}

- (BOOL)isPrepareing {
    return (self.player && self.playerItem && self.playerState >= PW_PLAYER_PREPAREING);
}

- (BOOL)hasPlayContent {
    if (self.player && self.playerItem && !self.stoped && ![self isError] && !self.ended && [self isPrepareing]) {
        return true;
    }
    return false;
}

#pragma mark 播放器周期接口
- (void)load:(NSString *) url {
    if([url hasPrefix:@"http"]){
        self.urlAssert = [AVURLAsset assetWithURL:[NSURL URLWithString:url]];
    }else{
        self.urlAssert = [AVURLAsset assetWithURL:[NSURL fileURLWithPath:url]];
    }
    // 初始化playerItem
    self.playerItem = [AVPlayerItem playerItemWithAsset:self.urlAssert];
    // 每次都重新创建Player，替换replaceCurrentItemWithPlayerItem:，该方法阻塞线程
    self.player = [AVPlayer playerWithPlayerItem:self.playerItem];
    
    self.playerState = PW_PLAYER_PREPAREING;
}

- (void)stop {
    if ([self hasPlayContent]) {
        [self stopPlayerTracker];
        [self unload];
        self.stoped = true;
        [self firePlayerStoped];
    }
}

- (void)unload {
    [self stopPlayerTracker];
    self.urlAssert = nil;
    self.playerItem = nil;
    self.player = nil;
    self.ended = false;
    self.stoped = false;
    self.buffing = false;
    self.seekTime = 0;
    self.bufferPercentage = 0;
    self.errorType = PW_PLAYER_IDLE;
    self.errorType = PW_PLAYER_ERROR_NONE;
}

- (void)rewind {
    if ([self canSeek]) {
        NSTimeInterval cur = CMTimeGetSeconds(self.playerItem.currentTime);
        if(cur > 10){
            [self seekToTime:(cur - 10)];
        }else{
            [self seekToTime:0];
        }
    }
}

- (void)fastForward {
    if ([self canSeek]) {
        NSTimeInterval cur = CMTimeGetSeconds(self.playerItem.currentTime);
        NSTimeInterval total = CMTimeGetSeconds(self.playerItem.duration);
        if(total - cur > 10){
            [self seekToTime:(cur+10)];
        }else{
            [self seekToTime:total];
        }
    }
}

- (void)seekToTime:(NSInteger)time {
    if ([self canSeek]) {
        if(time >= self.duration){
            [self.player seekToTime:CMTimeMake((self.duration - 1), 1)];
        }else{
            [self.player seekToTime:CMTimeMake(time, 1)];
        }
    }
}

- (void)pause {
    if (!self.parameters.pauseByUser) {
        self.parameters.pauseByUser = YES;
        [self parametersChanged];
    }
}

- (void)resume {
    if (!self.parameters.pauseByUser) {
        self.parameters.pauseByUser = NO;
        [self parametersChanged];
    }
}

#pragma mark 视频监听接口
- (void)playerCompleted:(NSNotification *)notification{
    [self stopPlayerTracker];
    self.ended = YES;
    [self firePlayerCompleted];
}

- (void)videoPrepared:(AVPlayerItem *)playerItem {
    self.playerState = PW_PLAYER_PREPARED;
    if(self.seekTime > 0){
        [self seekToTime:self.seekTime];
    }
    [self parametersChanged];
}

- (void)observeValueForKeyPath:(NSString *)keyPath ofObject:(id)object change:(NSDictionary *)change context:(void *)context {
    if (object == self.player.currentItem) {
        if ([keyPath isEqualToString:@"status"]) {
            if (self.player.currentItem.status == AVPlayerItemStatusReadyToPlay) {
                [self videoPrepared:self.player.currentItem];
            } else if (self.player.currentItem.status == AVPlayerItemStatusFailed || self.player.currentItem.status == AVPlayerItemStatusUnknown) {
                [self stopPlayerTracker];
                self.errorType = PW_PLAYER_ERROR_UNKNOWN;
            }
        } else if ([keyPath isEqualToString:@"loadedTimeRanges"]) {
            // 计算缓冲进度
            NSTimeInterval timeInterval = [self availableDuration];
            CGFloat totalDuration       = CMTimeGetSeconds(self.playerItem.duration);
            self.bufferPercentage = (NSInteger)(timeInterval * 100 / totalDuration);
        } else if ([keyPath isEqualToString:@"playbackLikelyToKeepUp"]) {
            if(self.playerItem.isPlaybackLikelyToKeepUp){
                // 当缓冲好的时候
                if(self.buffing){
                    self.buffing = NO;
                    [self firePlayerBufferingEnded];
                }
            }else{
                if(!self.buffing){
                    self.buffing = true;
                    [self firePlayerBufferingStart];
                }
            }
        }
    }
}
#pragma mark Timer操作
- (void)startPlayerTracker{
    if(!self.playerTracker){
        // 创建观看历史定时器
        self.playerTracker = [NSTimer scheduledTimerWithTimeInterval:1.0f target:self selector:@selector(firePlayerProgressChanged) userInfo:nil repeats:YES];
    }
}
- (void)stopPlayerTracker{
    if(self.playerTracker){
        [self.playerTracker invalidate];
        self.playerTracker = nil;
    }
}


#pragma mark 功能函数
/**
 *  计算缓冲进度
 *
 *  @return 缓冲进度
 */
- (NSTimeInterval)availableDuration {
    NSArray *loadedTimeRanges = [[_player currentItem] loadedTimeRanges];
    CMTimeRange timeRange     = [loadedTimeRanges.firstObject CMTimeRangeValue];// 获取缓冲区域
    float startSeconds        = CMTimeGetSeconds(timeRange.start);
    float durationSeconds     = CMTimeGetSeconds(timeRange.duration);
    NSTimeInterval result     = startSeconds + durationSeconds;// 计算缓冲总进度
    return result;
}

#pragma mark 事件触发器
- (void)firePlayerStoped {
    if (self.delegate && [self.delegate respondsToSelector:@selector(onPlayerStoped:object:)]) {
        [self.delegate onPlayerStoped:self object:self.parameters.playObject];
    }
}

- (void)firePlayerCompleted {
    if (self.delegate && [self.delegate respondsToSelector:@selector(onPlayerCompleted:object:)]) {
        [self.delegate onPlayerCompleted:self object:self.parameters.playObject];
    }
}

- (void)firePlayerStateChanged {
    if (self.delegate && [self.delegate respondsToSelector:@selector(onPlayerStateChanged:object:state:)]) {
        [self.delegate onPlayerStateChanged:self object:self.parameters.playObject state:self.playerState];
    }
}

- (void)firePlayerErrorOccurred {
    if (self.delegate && [self.delegate respondsToSelector:@selector(onPlayerErrorOccurred:object:error:)]) {
        [self.delegate onPlayerErrorOccurred:self object:self.parameters.playObject error:self.errorType];
    }
}

- (void)firePlayerBufferingEnded {
    if (self.delegate && [self.delegate respondsToSelector:@selector(onPlayerBufferingEnded:object:)]) {
        [self.delegate onPlayerBufferingEnded:self object:self.parameters.playObject];
    }
}

- (void)firePlayerBufferingStart {
    if (self.delegate && [self.delegate respondsToSelector:@selector(onPlayerBufferingStart:object:)]) {
        [self.delegate onPlayerBufferingStart:self object:self.parameters.playObject];
    }
}

- (void)fireBufferingUpdated {
    if (self.delegate && [self.delegate respondsToSelector:@selector(onBufferingUpdated:object:bufferPercentage:)]) {
        [self.delegate onBufferingUpdated:self object:self.parameters.playObject bufferPercentage:self.bufferPercentage];
    }
}

- (void)firePlayerProgressChanged {
    if (self.delegate && [self.delegate respondsToSelector:@selector(onPlayerProgressChanged:object:position:duration:)]) {
        [self.delegate onPlayerProgressChanged:self object:self.parameters.playObject position:self.position duration:self.duration];
    }
}

- (void)dealloc{
    NSLog(@"PWChannelPlayer dealloc");
}

@end
