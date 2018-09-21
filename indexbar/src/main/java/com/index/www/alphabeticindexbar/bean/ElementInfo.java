package com.index.www.alphabeticindexbar.bean;

public class ElementInfo {
    private String firstLetter;
    private String name;
    private String pinyin;
    private int index;//指的是这个元素的首字母，在右侧的indexbar里的字母中的索引位置，这个值是所有元素排序的唯一依据


    /**
     * ★很有用：该元素在原来未排序的集合（数组）中的位置，
     * 当点击条目时候，让原始的集合根据这个rawPostion去找对应的属性，例如：经、纬度、城市代码
     */
    private int rawPostion;




    public ElementInfo() {
    }

    public ElementInfo(String firstLetter) {
        this.firstLetter = firstLetter;
    }


    public String getFirstLetter() {
        return firstLetter;
    }

    public void setFirstLetter(String firstLetter) {
        this.firstLetter = firstLetter;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPinyin() {
        return pinyin;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }


    public int getRawPostion() {
        return rawPostion;
    }

    public void setRawPostion(int rawPostion) {
        this.rawPostion = rawPostion;
    }
}