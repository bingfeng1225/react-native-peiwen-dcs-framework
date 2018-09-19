package com.qd.peiwen.dcsframework.download;


import com.qd.peiwen.dcsframework.tools.LogUtils;

import java.io.IOException;
import java.io.InputStream;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by jeffreyliu on 16/11/8.
 */


public class FileDownloader implements Callback {
    private Call call = null;
    private OkHttpClient client = null;
    private Object downloaderObject = null;
    private IFileDownloaderListerer listener = null;

    public FileDownloader(OkHttpClient client, Object downloaderObject, IFileDownloaderListerer listener) {
        this.client = client;
        this.listener = listener;
        this.downloaderObject = downloaderObject;
    }

    public void cancel() {
        if (null != this.call && !this.call.isCanceled()) {
            this.call.cancel();
        }
    }

    public void start(String url) {
        this.start(url, 0);
    }

    public void start(String url, long offset) {
        Request.Builder build = new Request.Builder();
        build.tag(downloaderObject);
        if (offset > 0) {
            build.url(url)
                    .header("RANGE", "bytes=" + offset + "-")
                    .header("Accept-Encoding", "identity");
        } else {
            build.url(url)
                    .header("Accept-Encoding", "identity");
        }
        this.call = this.client.newCall(build.build());
        call.enqueue(this);
    }

    @Override
    public void onFailure(Call call, IOException e) {
        e.printStackTrace();
        LogUtils.d("Failure:file downloaer task is failed");
        if (null != this.listener) {
            this.listener.onFailured(this, this.downloaderObject, call.isCanceled());
        }
    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {
        InputStream input = null;
        try {
            LogUtils.e("" + call.request().url());
            Headers headers = response.headers();
            LogUtils.e("" + headers);

            MediaType media = response.body().contentType();
            if (!"audio".equals(media.type())) {
                throw new IOException("Content type Error");
            }

            long totalSize = response.body().contentLength();
            if (null != this.listener) {
                this.listener.onStarted(this, this.downloaderObject, totalSize);
            }
            input = response.body().byteStream();
            byte[] bytes = new byte[12 * 1024];
            int readLength = 0;
            while ((readLength = input.read(bytes)) > 0) {
                totalSize -= readLength;
                if (null != this.listener) {
                    this.listener.onByteReaded(this, this.downloaderObject, bytes, readLength);
                }
            }
            if (totalSize == 0) {
                if (null != this.listener) {
                    this.listener.onCompleted(this, this.downloaderObject);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            LogUtils.d("Response:file downloaer task is failed");
            if (null != this.listener) {
                this.listener.onFailured(this, this.downloaderObject, call.isCanceled());
            }
        } finally {
            if (null != input) {
                input.close();
            }
        }
    }
}
