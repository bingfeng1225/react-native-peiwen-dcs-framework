package com.qd.peiwen.dcsframework.pinyinsearch.pinyin;

/**
 * Created by you on 2017/9/7.
 */

public class CNPinyin <T extends CN> {
    /**
     * 对应的所有字母拼音
     */
    CNChar[] cnchars;

    public final T data;

    CNPinyin(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (CNChar cnchar : cnchars) {
            sb.append(cnchar.toString());
        }
        return sb.toString();
    }
}
