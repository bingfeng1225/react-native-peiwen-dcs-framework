package com.qd.peiwen.dcsframework.download;

import java.io.IOException;

/**
 * Created by jeffreyliu on 16/11/8.
 */

public interface IFileDownloaderListerer {
    void onStarted(FileDownloader downloader, Object downloadObject, long totalsize) throws IOException;

    void onByteReaded(FileDownloader downloader, Object downloadObject, byte[] bytes, int length) throws IOException;

    void onCompleted(FileDownloader downloader, Object downloadObject);

    void onFailured(FileDownloader downloader, Object downloadObject, boolean cancel);
}
