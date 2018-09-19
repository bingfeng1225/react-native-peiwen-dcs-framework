package com.qd.peiwen.dcsframework.musicplayer;

import android.content.Context;
import android.text.TextUtils;


import com.qd.peiwen.dcsframework.download.FileDownloader;
import com.qd.peiwen.dcsframework.download.IFileDownloaderListerer;
import com.qd.peiwen.dcsframework.musicplayer.enmudefine.ErrorType;
import com.qd.peiwen.dcsframework.musicplayer.enmudefine.PlayerState;
import com.qd.peiwen.dcsframework.tools.FileUtils;
import com.qd.peiwen.dcsframework.tools.LogUtils;

import java.io.IOException;

import okhttp3.OkHttpClient;

/**
 * Created by nick on 2018/3/23.
 */

public class SynthesisPlayer extends ChannelPlayer implements IFileDownloaderListerer {
    private String filepaht;
    private OkHttpClient httpClient;
    private FileDownloader downloader;

    public SynthesisPlayer(Context context) {
        super(context);
    }

    public String getFilepaht() {
        return filepaht;
    }

    public void setFilepaht(String filepaht) {
        this.filepaht = filepaht;
    }

    public OkHttpClient getHttpClient() {
        return httpClient;
    }

    public void setHttpClient(OkHttpClient httpClient) {
        this.httpClient = httpClient;
    }

    @Override
    public void load(String url) {
        try {
            if (TextUtils.isEmpty(url)) {
                throw new RuntimeException("play url is null");
            }
            this.setPlayerState(PlayerState.PLAYER_PREPAREING);
            this.downloader = new FileDownloader(httpClient, lastObject(), this);
            this.downloader.start(url);
        } catch (Exception e) {
            e.printStackTrace();
            this.setErrorType(ErrorType.MEDIA_ERROR_INTERNAL_DEVICE_ERROR);
        }
    }

    @Override
    public void stop() {
        this.downloader = null;
        super.stop();
    }


    @Override
    public void unload() {
        this.downloader = null;
        super.unload();
    }

    @Override
    public void onStarted(FileDownloader downloader, Object downloadObject, long totalsize) throws IOException {
        LogUtils.e("FileDownloader onStarted");
        if (!downloader.equals(this.downloader)) {
            downloader.cancel();
        }
    }

    @Override
    public void onByteReaded(FileDownloader downloader, Object downloadObject, byte[] bytes, int length) throws IOException {
        LogUtils.e("FileDownloader onByteReaded");
        if (!downloader.equals(this.downloader)) {
            downloader.cancel();
        } else {
            FileUtils.writeFile(this.filepaht, bytes, length);
        }
    }

    @Override
    public void onCompleted(FileDownloader downloader, Object downloadObject) {
        LogUtils.e("FileDownloader onCompleted");
        if (downloader.equals(this.downloader)) {
            super.load(this.filepaht);
        }
    }

    @Override
    public void onFailured(FileDownloader downloader, Object downloadObject, boolean cancel) {
        LogUtils.e("FileDownloader onFailured,cancel = " + cancel);
        if (!cancel) {
            this.setErrorType(ErrorType.MEDIA_ERROR_INTERNAL_DEVICE_ERROR);
        }
    }
}
