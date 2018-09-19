package com.qd.peiwen.dcsframework.pinyinsearch;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;

import com.qd.peiwen.dcsframework.pinyinsearch.entity.CNContact;
import com.qd.peiwen.dcsframework.pinyinsearch.listener.IPhoneSearchListener;
import com.qd.peiwen.dcsframework.pinyinsearch.pinyin.CN;
import com.qd.peiwen.dcsframework.pinyinsearch.pinyin.CNPinyin;
import com.qd.peiwen.dcsframework.pinyinsearch.pinyin.CNPinyinFactory;
import com.qd.peiwen.dcsframework.pinyinsearch.pinyin.CNPinyinIndex;
import com.qd.peiwen.dcsframework.pinyinsearch.pinyin.CNPinyinIndexFactory;

import java.util.ArrayList;
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

public class PhoneSearch<T extends CN> {
    private long time = 0;
    private T object = null;
    private int totalCount = 0;
    private int resolverCount = 0;
    private ContentResolver resolver = null;
    private IPhoneSearchListener listener = null;
    private List<CNContact> contacts = new LinkedList<>();

    public PhoneSearch(Context context) {
        this.resolver = context.getContentResolver();
    }

    public PhoneSearch object(T object) {
        this.object = object;
        return this;
    }

    public PhoneSearch listener(IPhoneSearchListener listener) {
        this.listener = listener;
        return this;
    }

    public void search() {
        this.rxmatchAllContacts(object);
        this.time = System.currentTimeMillis();
    }

    private void rxmatchAllContacts(CN keyword) {
        Observable.just(keyword)
                .flatMap(new Function<CN, ObservableSource<CN>>() {
                    @Override
                    public ObservableSource<CN> apply(@io.reactivex.annotations.NonNull CN keyword) throws Exception {
                        matchAllContacts(CNPinyinFactory.createCNPinyin(keyword));
                        return Observable.just(keyword);
                    }
                })
                .observeOn(Schedulers.io())
                .subscribeOn(Schedulers.io())
                .subscribe();
    }

    private void matchAllContacts(CNPinyin keyword) {
        Cursor cursor = resolver.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
        if (cursor == null || cursor.getCount() == 0) {
            this.fireContactEmpty();
            return;
        }
        cursor.moveToFirst();
        this.totalCount = cursor.getCount();
        int idColumn = cursor.getColumnIndex(ContactsContract.Contacts._ID);
        int displayNameColumn = cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);
        do {
            CNContact contact = new CNContact();
            contact.setName(cursor.getString(displayNameColumn));
            contact.setContactId(cursor.getString(idColumn));
            this.rxmatchContact(contact, keyword);
        } while (cursor.moveToNext());
        cursor.close();
    }

    private void rxmatchContact(final CNContact contact, CNPinyin keyword) {
        Observable.just(keyword)
                .flatMap(new Function<CNPinyin, ObservableSource<CNContact>>() {
                    @Override
                    public ObservableSource<CNContact> apply(@io.reactivex.annotations.NonNull CNPinyin keyword) throws Exception {
                        matchContact(contact, keyword);
                        return Observable.just(contact);
                    }
                })
                .observeOn(Schedulers.io())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<CNContact>() {
                    @Override
                    public void accept(CNContact contact) throws Exception {
                        resolverContact(contact);
                    }
                });
    }


    private void matchContact(CNContact contact, CNPinyin keyword) {
        CNPinyin pinyin = CNPinyinFactory.createCNPinyin(contact);
        List<CNPinyinIndex> indices = CNPinyinIndexFactory.indices(pinyin, keyword);
        if (!indices.isEmpty()) {
            contact.addIndices(indices);
            contact.addPhones(queryContactNumbers(contact.getContactId()));
        }
    }

    private List<String> queryContactNumbers(String contactId) {
        List<String> phones = new ArrayList<>();
        Cursor cursor = resolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=" + contactId, null, null);
        if (cursor == null || !cursor.moveToFirst()) {
            return phones;
        }
        int phoneColumn = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
        do {
            phones.add(formatPhoneNumber(cursor.getString(phoneColumn)));
        } while (cursor.moveToNext());
        cursor.close();
        return phones;
    }

    private String formatPhoneNumber(String number) {
        return number.replace(" ", "").replace("+86", "");
    }

    private synchronized void resolverContact(CNContact contact) {
        this.resolverCount++;
        if (!contact.getIndices().isEmpty() && !contact.getPhones().isEmpty()) {
            this.contacts.add(contact);
        }
        if (this.resolverCount == totalCount) {
            if (this.contacts.isEmpty()) {
                this.fireContactEmpty();
            } else {
                this.fireFindContacts();
            }
        }
    }


    private void fireContactEmpty() {
        if (null != listener) {
            listener.onContactEmpty(this.object);
        }
    }

    private void fireFindContacts() {
        if (null != listener) {
            listener.onFindContacts(this.object, contacts);
        }
    }
}
