package com.qd.peiwen.dcsframework.pinyinsearch;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.provider.Telephony;


import com.qd.peiwen.dcsframework.pinyinsearch.entity.CNMSGBody;
import com.qd.peiwen.dcsframework.pinyinsearch.entity.CNMSGPhone;
import com.qd.peiwen.dcsframework.pinyinsearch.entity.SMSMessage;
import com.qd.peiwen.dcsframework.pinyinsearch.listener.ISMSSearchListener;
import com.qd.peiwen.dcsframework.pinyinsearch.pinyin.CN;
import com.qd.peiwen.dcsframework.pinyinsearch.pinyin.CNPinyin;
import com.qd.peiwen.dcsframework.pinyinsearch.pinyin.CNPinyinFactory;
import com.qd.peiwen.dcsframework.pinyinsearch.pinyin.CNPinyinIndex;
import com.qd.peiwen.dcsframework.pinyinsearch.pinyin.CNPinyinIndexFactory;

import java.util.LinkedList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by nick on 2018/3/6.
 */

public class SMSSearch<T extends CN> {
    private long time = 0;
    private T object = null;
    private int totalCount = 0;
    private int resolverCount = 0;
    private ContentResolver resolver = null;
    private ISMSSearchListener listener = null;
    private List<SMSMessage> messages = new LinkedList<>();

    public SMSSearch(Context context) {
        this.resolver = context.getContentResolver();
    }

    public SMSSearch object(T object) {
        this.object = object;
        return this;
    }

    public SMSSearch listener(ISMSSearchListener listener) {
        this.listener = listener;
        return this;
    }

    public void search() {
        if (null != this.object) {
            this.rxmatchAllMessages(object);
            this.time = System.currentTimeMillis();
        }
    }

    public void rxmatchAllMessages(CN keyword) {
        Observable.just(keyword)
                .flatMap(new Function<CN, ObservableSource<CN>>() {
                    @Override
                    public ObservableSource<CN> apply(@io.reactivex.annotations.NonNull CN keyword) throws Exception {
                        matchAllMessages(CNPinyinFactory.createCNPinyin(keyword));
                        return Observable.fromArray(keyword);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe();
    }


    public void matchAllMessages(CNPinyin keyword) {
        Cursor cursor = resolver.query(Telephony.Sms.CONTENT_URI, null, null, null, "date desc");
        if (cursor == null || cursor.getCount() == 0) {
            this.fireMessageEmpty();
            return;
        }
        cursor.moveToFirst();
        this.totalCount = cursor.getCount();
        int dateColumn = cursor.getColumnIndex(Telephony.Sms.DATE);
        int typeColumn = cursor.getColumnIndex(Telephony.Sms.TYPE);
        int bodyColumn = cursor.getColumnIndex(Telephony.Sms.BODY);
        int phoneColumn = cursor.getColumnIndex(Telephony.Sms.ADDRESS);
        do {
            SMSMessage message = new SMSMessage();
            message.setType(cursor.getInt(typeColumn));
            message.setDate(cursor.getLong(dateColumn));

            CNMSGBody body = new CNMSGBody();
            body.setBody(cursor.getString(bodyColumn));
            message.setBody(body);

            CNMSGPhone phone = new CNMSGPhone();
            phone.setPhone(formatPhoneNumber(cursor.getString(phoneColumn)));
            message.setPhone(phone);
            this.rxmatchMessage(message, keyword);
        } while (cursor.moveToNext());
        cursor.close();
    }

    private void rxmatchMessage(final SMSMessage message, CNPinyin keyword) {
        Observable.just(keyword)
                .flatMap(new Function<CNPinyin, ObservableSource<SMSMessage>>() {
                    @Override
                    public ObservableSource<SMSMessage> apply(@io.reactivex.annotations.NonNull CNPinyin keyword) throws Exception {
                        matchMessage(message, keyword);
                        return Observable.just(message);
                    }
                })
                .observeOn(Schedulers.io())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<SMSMessage>() {
                    @Override
                    public void accept(SMSMessage message) throws Exception {
                        resolverMessage(message);
                    }
                });
    }


    private void matchMessage(SMSMessage message, CNPinyin keyword) {
        CNMSGBody body = message.getBody();
        CNMSGPhone phone = message.getPhone();
        phone.setName(queryNameByPhone(resolver, phone.getPhone()));
        List<CNPinyinIndex> indicesBody = CNPinyinIndexFactory.indices(CNPinyinFactory.createCNPinyin(body), keyword);
        List<CNPinyinIndex> indicesPerson = CNPinyinIndexFactory.indices(CNPinyinFactory.createCNPinyin(phone), keyword);
        if (!indicesBody.isEmpty() || !indicesPerson.isEmpty()) {
            body.addIndices(indicesBody);
            phone.addIndices(indicesPerson);
        }
    }

    private String formatPhoneNumber(String number) {
        return number.replace(" ", "").replace("+86", "");
    }

    private String queryNameByPhone(ContentResolver resolver, String phone) {
        Uri uri = Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI, phone);
        Cursor cursor = resolver.query(uri, new String[]{ContactsContract.PhoneLookup.DISPLAY_NAME}, null, null, null);
        if (cursor == null || !cursor.moveToFirst()) {
            return "";
        }
        String name = cursor.getString(0);
        cursor.close();
        return name;
    }

    private synchronized void resolverMessage(SMSMessage message) {
        if (!message.getBody().getIndices().isEmpty() || !message.getPhone().getIndices().isEmpty()) {
            messages.add(message);
        }
        this.resolverCount++;
        if (this.resolverCount == totalCount) {
            if (this.messages.isEmpty()) {
                this.fireMessageEmpty();
            } else {
                this.fireFindMessages();
            }
        }
    }

    private void fireMessageEmpty() {
        if (null != listener) {
            listener.onMessageEmpty(object);
        }
    }

    private void fireFindMessages() {
        if (null != listener) {
            listener.onFindMessages(object, messages);
        }
    }
}
