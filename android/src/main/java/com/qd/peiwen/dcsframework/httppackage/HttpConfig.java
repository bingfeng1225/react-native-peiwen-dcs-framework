package com.qd.peiwen.dcsframework.httppackage;

/**
 * Created by nick on 2017/12/21.
 */

public class HttpConfig {
    public static String token;
    public static String deviceID;
    public static String eventURL;
    public static String speakDownloadURL;
    public static String voiceRecognizeURL;

    public static class HttpTags {
        // 请求event事件TAG
        public static final String EVENT = "event";
        // 请求授权事件的TAG
        public static final String VOICE_RECOGNIZE = "VoiceRecognize";
    }

    public static class HttpHeaders {
        public static final String DEBUG = "debug";
        public static final String DEBUG_PARAM = "0";
        public static final String BEARER = "Bearer ";
        public static final String SAIYA_LOGID = "saiyalogid";
        public static final String CONTENT_TYPE = "Content-Type";
        public static final String AUTHORIZATION = "Authorization";
        public static final String DUEROS_DEVICE_ID = "dueros-device-id";
    }

    public static class ContentTypes {
        public static final String APPLICATION_JSON = "application/json;charset=UTF-8";
        public static final String FORM_MULTIPART = "multipart/form-data boundary=hisense-boundory";
    }

    public static class Parameters {
        public static final String BOUNDARY = "boundary";
        public static final String DATA_METADATA = "metadata";
    }

    public static class TimeOut {
        public static final long HTTP_DEFAULT = 20 * 1000;
    }

}
