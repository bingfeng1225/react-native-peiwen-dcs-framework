package com.qd.peiwen.dcsframework.pinyinsearch.pinyin;

import android.support.annotation.NonNull;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nick on 2018/3/2.
 */

public class CNChar implements Comparable<CNChar> {
    char cn;
    int index;
    List<String> pinyins = new ArrayList<>();

    public CNChar(char cn, int index) {
        this.cn = cn;
        this.index = index;
    }

    CNChar toPinyin() {
        try {
            HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
            format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
            String[] pinyins = PinyinHelper.toHanyuPinyinStringArray(this.cn, format);
            if(pinyins.length == 0){
                this.pinyins.add(String.valueOf(this.cn));
            }else{
                for (String item: pinyins) {
                    if(!this.pinyins.contains(item)){
                        this.pinyins.add(item);
                    }
                }
            }
        } catch (BadHanyuPinyinOutputFormatCombination badHanyuPinyinOutputFormatCombination) {
            badHanyuPinyinOutputFormatCombination.printStackTrace();
        }
        return this;
    }

    @Override
    public int compareTo(@NonNull CNChar other) {
        for (String src:this.pinyins) {
            for (String dest:other.pinyins) {
                if(src.equals(dest)){
                    return 0;
                }
            }
        }
        return -1;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if(this.pinyins.size() == 1){
            sb.append(this.pinyins.get(0));
        }else if(this.pinyins.size() > 1){
            for (int i = 0; i < pinyins.size(); i++) {
                if (i == 0) {
                    sb.append("[");
                }
                sb.append(pinyins.get(i));
                if (i < pinyins.size() - 1) {
                    sb.append(",");
                } else {
                    sb.append("]");
                }
            }
        }
        return sb.toString();
    }
}
