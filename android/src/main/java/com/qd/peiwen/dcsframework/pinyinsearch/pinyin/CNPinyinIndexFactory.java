package com.qd.peiwen.dcsframework.pinyinsearch.pinyin;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by you on 2017/9/8.
 */

public final class CNPinyinIndexFactory {
    /**
     * 所有拼音匹配
     *
     * @param pinyin
     * @param keyword
     * @return
     */
    public static <T extends CN> List<CNPinyinIndex> indices(CNPinyin<T> pinyin, CNPinyin<T> keyword) {
        List<CNPinyinIndex> indexList = new ArrayList<>();
        if (keyword.cnchars.length == 0) {
            return indexList;
        }
        if (pinyin.cnchars.length == 0) {
            return indexList;
        }

        if (keyword.cnchars.length > pinyin.cnchars.length) {
            return indexList;
        }
        for (int i = 0; i < pinyin.cnchars.length - keyword.cnchars.length + 1; i++) {
            CNChar src = pinyin.cnchars[i];
            CNChar dest = keyword.cnchars[0];
            if (src.compareTo(dest) == 0) {
                CNPinyinIndex pinyinIndex = matcherPinyins(pinyin,keyword,i);
                if(null != pinyinIndex) {
                    indexList.add(pinyinIndex);
                }
            }
        }
        return indexList;
    }

    /**
     * 所有拼音匹配
     *
     * @param pinyin
     * @param keyword
     * @return
     */
    public static <T extends CN> CNPinyinIndex matcherPinyins(CNPinyin<T> pinyin, CNPinyin<T> keyword, int index) {
        for (int i = 1; i < keyword.cnchars.length; i++) {
            CNChar src = pinyin.cnchars[i + index];
            CNChar dest = keyword.cnchars[i];
            if (src.compareTo(dest) != 0) {
                return null;
            }
        }
        return new CNPinyinIndex(index,keyword.cnchars.length);
    }
}
