package com.qd.peiwen.dcsframework.httppackage.request;

import android.net.Uri;

import com.google.gson.Gson;
import com.qd.peiwen.dcsframework.entity.respons.DCSRespons;
import com.qd.peiwen.dcsframework.httppackage.HttpConfig;
import com.qd.peiwen.dcsframework.httppackage.listener.IRequestListener;
import com.qd.peiwen.dcsframework.tools.LogUtils;


import org.apache.commons.fileupload.MultipartStream;
import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by nick on 2017/11/23.
 */

public abstract class HttpRequest implements Callback {
    // url
    protected String url;
    //Json转换器
    protected Gson gson;
    //TAG
    protected String tag;
    //request id
    protected String uuid;
    // okhttpclient
    private OkHttpClient httpClient;
    // 请求监听器
    protected IRequestListener listener;
    // 请求参数
    protected Map<String, String> params = new HashMap<>();
    // 请求header
    protected Map<String, String> headers = new HashMap<>();

    protected static final int BUFFER_SIZE = 8192;

    public HttpRequest() {

    }

    public String uuid() {
        return this.uuid;
    }

    public HttpRequest url(String url) {
        this.url = url;
        return this;
    }

    public HttpRequest gson(Gson gson) {
        this.gson = gson;
        return this;
    }


    public HttpRequest uuid(String uuid) {
        this.uuid = uuid;
        return this;
    }

    public HttpRequest listener(IRequestListener listener) {
        this.listener = listener;
        return this;
    }

    public HttpRequest httpClient(OkHttpClient httpClient) {
        this.httpClient = httpClient;
        return this;
    }

    public HttpRequest params(String key, String value) {
        this.params.put(key, value);
        return this;
    }

    public HttpRequest headers(String key, String value) {
        this.headers.put(key, value);
        return this;
    }

    public void execute() {
        if (null != listener) {
            listener.onStarted(this.uuid, this);
        }
        this.generateCall().enqueue(this);
    }

    protected Call generateCall() {
        return httpClient.newCall(generateRequest());
    }

    protected abstract Request generateRequest();

    protected String generateParams() {
        if (params == null || params.isEmpty()) {
            return url;
        }
        Uri.Builder builder = Uri.parse(url).buildUpon();
        for (String key : params.keySet()) {
            builder.appendQueryParameter(key, params.get(key));
        }
        return builder.build().toString();
    }

    protected Headers generateHeaders() {
        Headers.Builder builder = new Headers.Builder();
        if (headers != null) {
            for (String key : headers.keySet()) {
                builder.add(key, headers.get(key));
            }
        }
        return builder.build();
    }

    @Override
    public void onFailure(Call call, IOException e) {
        e.printStackTrace();
        if (call.isCanceled()) {
            if (null != listener) {
                listener.onCanceled(this.uuid, this);
                listener.onFinished(this.uuid, this);
            }
        } else {
            if (null != listener) {
                listener.onFailured(this.uuid, this);
                listener.onFinished(this.uuid, this);
            }
        }
    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {
        try {
            if (!response.isSuccessful()) {
                throw new IOException("connect failed with code : " + response.code());
            }
            if (null != listener) {
                listener.onSuccessed(this.uuid, this);
            }
            this.parseResponse(response);
        } catch (Exception e) {
            e.printStackTrace();
            if (call.isCanceled()) {
                if (null != listener) {
                    listener.onCanceled(this.uuid, this);
                }
            } else {
                if (null != listener) {
                    listener.onFailured(this.uuid, this);
                }
            }
        } finally {
            if (null != response.body()) {
                response.body().close();
            }
            if (null != listener) {
                listener.onFinished(this.uuid, this);
            }
        }
    }

    protected void parseResponse(Response response) throws IOException {
        String boundary = getBoundary(response);
        if (boundary != null) {
            parseStream(response, response.body().byteStream(), boundary);
        }
    }

    protected void parseStream(Response response, InputStream inputStream, String boundary) throws IOException {
        MultipartStream multipartStream = new MultipartStream(inputStream, boundary.getBytes(), BUFFER_SIZE, null);
        parseMultipartStream(response, multipartStream);
    }

    private void parseMultipartStream(Response response, MultipartStream multipartStream) throws IOException {
        boolean hasNextPart = multipartStream.skipPreamble();
        while (hasNextPart) {
            handlePart(response, multipartStream);
            hasNextPart = multipartStream.readBoundary();
        }
    }

    protected void handlePart(Response response, MultipartStream multipartStream) throws IOException {
        Map<String, String> headers = getPartHeaders(multipartStream);
        LogUtils.d("handlePart: " + headers);
        if (headers != null) {
            parserDCSRespons(response, new String(getPartBytes(multipartStream)));
        }
    }

    public void parserDCSRespons(Response response, String content) {
        LogUtils.d("parserDCSRespons" + content);
        DCSRespons respons = gson.fromJson(content, DCSRespons.class);
        respons.setMessage(content);
        if (null != listener) {
            listener.onDirectiveRecevied(this.uuid, this, respons);
        }
    }

    protected byte[] getPartBytes(MultipartStream multipartStream) throws IOException {
        ByteArrayOutputStream data = new ByteArrayOutputStream();
        multipartStream.readBodyData(data);
        return data.toByteArray();
    }

    protected Map<String, String> getPartHeaders(MultipartStream multipartStream) throws IOException {
        String headers = multipartStream.readHeaders();
        BufferedReader reader = new BufferedReader(new StringReader(headers));
        Map<String, String> headerMap = new HashMap<>();
        for (String line = reader.readLine(); line != null; line = reader.readLine()) {
            line = line.trim();
            if (!StringUtils.isBlank(line) && line.contains(":")) {
                int colon = line.indexOf(":");
                String headerName = line.substring(0, colon).trim();
                String headerValue = line.substring(colon + 1).trim();
                headerMap.put(headerName.toLowerCase(), headerValue);
            }
        }
        return headerMap;
    }

    protected static String getBoundary(Response response) {
        String headerValue = response.header(HttpConfig.HttpHeaders.CONTENT_TYPE);
        String boundary = getHeaderParameter(headerValue, HttpConfig.Parameters.BOUNDARY);
        return boundary;
    }

    protected static String getHeaderParameter(final String headerValue, final String key) {
        if ((headerValue == null) || (key == null)) {
            return null;
        }
        String[] parts = headerValue.split(";");
        for (String part : parts) {
            part = part.trim();
            if (part.startsWith(key)) {
                return part.substring(key.length() + 1).replaceAll("(^\")|(\"$)", "").trim();
            }
        }
        return null;
    }
}
