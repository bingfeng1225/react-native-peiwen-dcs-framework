package com.qd.peiwen.dcsframework.devices;

import com.qd.peiwen.dcsframework.entity.payload.BasePayload;

import java.util.HashMap;

/**
 * Created by nick on 2017/11/30.
 */

public class PayloadManager {
    private static PayloadManager instance = null;
    private static final Object lock = new Object();
    private HashMap<String, Class<?>> payloads = new HashMap<>();

    public static PayloadManager getInstance() {
        if (null == instance) {
            synchronized (lock) {
                instance = new PayloadManager();
            }
        }
        return instance;
    }

    private PayloadManager() {

    }

    public void insertPayload(String namespace, String name, Class<? extends BasePayload> type) {
        String key = namespace + name;
        this.payloads.put(key, type);
    }

    public void insertPayload(String namespace, String name, String ptype, Class<? extends BasePayload> type) {
        String key = namespace + name + ptype;
        this.payloads.put(key, type);
    }

    public Class<?> findPayloadClass(String namespace, String name) {
        String key = namespace + name;
        return this.payloads.get(key);
    }

    public Class<?> findPayloadClass(String namespace, String name, String ptype) {
        String key = namespace + name + ptype;
        return this.payloads.get(key);
    }
}
