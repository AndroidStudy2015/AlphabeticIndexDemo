package com.index.www.alphabeticindexbar.util;

import com.github.promeg.pinyinhelper.Pinyin;
import com.index.www.alphabeticindexdemo.right.bean.ElementInfo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SortHelper {

    /**
     * 返回一个按照首字母排序完成后的列表
     *
     * @param rawArray 未排序的string数组
     * @return
     */
    public static List<ElementInfo> sortByLetter(String[] rawArray, List<String> indexList, String otherFirstLetter) {

        List<ElementInfo> sortList = new ArrayList<>();


        for (int i = 0; i < rawArray.length; i++) {

            String city = rawArray[i];
            String pinyin = Pinyin.toPinyin(city, "");


            ElementInfo elementInfo = new ElementInfo();
            elementInfo.setName(city);
            elementInfo.setPinyin(pinyin);

            String firstLetter = pinyin.substring(0, 1);

            if (!indexList.contains(firstLetter)) {
                firstLetter = otherFirstLetter;

            }
            elementInfo.setFirstLetter(firstLetter);

            int code = indexList.indexOf(firstLetter);
            elementInfo.setIndex(code);


            sortList.add(elementInfo);
        }

        Collections.sort(sortList, new Comparator<ElementInfo>() {

            @Override
            public int compare(ElementInfo c1, ElementInfo c2) {
//                return c1.getIndex() - c2.getIndex();
                return c1.getPinyin().compareTo(c2.getPinyin());

            }
        });

        Collections.sort(sortList, new Comparator<ElementInfo>() {

            @Override
            public int compare(ElementInfo c1, ElementInfo c2) {
                return c1.getIndex() - c2.getIndex();

            }
        });


        return sortList;
    }

    /**
     * 返回一个按照首字母排序完成后的列表
     *
     * @param rawList 未排序的stringList
     * @return
     */
    public static List<ElementInfo> sortByLetter(List<String> rawList, List<String> indexList,String otherFirstLetter) {


        List<ElementInfo> sortList = new ArrayList<>();


        for (int i = 0; i < rawList.size(); i++) {

            String city = rawList.get(i);
            String pinyin = Pinyin.toPinyin(city, "");


            ElementInfo elementInfo = new ElementInfo();
            elementInfo.setName(city);
            elementInfo.setPinyin(pinyin);

            String firstLetter = pinyin.substring(0, 1);

            if (!indexList.contains(firstLetter)) {
                firstLetter = otherFirstLetter;

            }
            elementInfo.setFirstLetter(firstLetter);

            int code = indexList.indexOf(firstLetter);
            elementInfo.setIndex(code);


            sortList.add(elementInfo);
        }

        Collections.sort(sortList, new Comparator<ElementInfo>() {

            @Override
            public int compare(ElementInfo c1, ElementInfo c2) {
//                return c1.getIndex() - c2.getIndex();
                return c1.getPinyin().compareTo(c2.getPinyin());

            }
        });

        Collections.sort(sortList, new Comparator<ElementInfo>() {

            @Override
            public int compare(ElementInfo c1, ElementInfo c2) {
                return c1.getIndex() - c2.getIndex();

            }
        });


        return sortList;
    }

    /**
     * 传入排序好的list，得到所有元素的首字母做成的集合
     *
     * @param sortList 按照首字母排序后的集合
     * @return
     */
    public static List<String> getFirstLetterListBySorted(List<ElementInfo> sortList) {
        List<String> firstLetterList = new ArrayList<>();
        for (int i = 0; i < sortList.size(); i++) {
            firstLetterList.add(sortList.get(i).getFirstLetter());
        }

        return firstLetterList;
    }




}
