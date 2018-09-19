package com.qd.peiwen.dcsframework.pinyinsearch.pinyin;

/**
 * Created by you on 2017/9/8.
 */

public final class CNPinyinFactory {
    public static <T extends CN> CNPinyin<T> createCNPinyin(T t) {
        if (t == null || t.chinese() == null)
            return null;
        String chinese = t.chinese();
        char[] chars = chinese.toCharArray();
        CNPinyin cnPinyin = new CNPinyin(t);
        CNChar[] cnchars = new CNChar[chars.length];
        for (int i = 0; i < chars.length; i++) {
            cnchars[i] = new CNChar(chars[i],i).toPinyin();
        }
        cnPinyin.cnchars = cnchars;
        return cnPinyin;
    }
}
