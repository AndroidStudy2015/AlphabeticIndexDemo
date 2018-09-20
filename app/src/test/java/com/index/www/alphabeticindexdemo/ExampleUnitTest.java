package com.index.www.alphabeticindexdemo;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {


//// 添加自定义词典
//        Pinyin.init(Pinyin.newConfig()
//                .with(new PinyinMapDict() {
//                    @Override
//                    public Map<String, String[]> mapping() {
//                        HashMap<String, String[]> map = new HashMap<String, String[]>();
//                        map.put("重庆",  new String[]{"CHONG", "QING"});
//                        map.put("乐山",  new String[]{"yue", "shan"});
//                        return map;
//                    }
//                }));
//
//        String s = Pinyin.toPinyin("重庆", "");
//        String s5 = Pinyin.toPinyin("CHONGQIng", "");
//        String s1 = Pinyin.toPinyin("乐山", "");
//        String s2 = Pinyin.toPinyin("武汉", "");
//        String s3 = Pinyin.toPinyin("安庆", "");
//        String s4 = Pinyin.toPinyin("安按", "");
////        System.out.print(s+"   "+s1);
//        List<String> list=new ArrayList<>();
//        list.add(s);
//        list.add(s1);
//        list.add(s2);
//        list.add(s3);
//        list.add(s4);
//        list.add(s5);
//
//        Comparator cmp= Collator.getInstance(Locale.CHINA);
//        Collections.sort(list,cmp);
//        for (int i = 0; i < list.size(); i++) {
//            System.out.println(list.get(i));
//        }
//


//        List<String> list=new ArrayList<>();
//        list.add("A");
//        list.add("A");
//        list.add("A");
//        list.add("A");
//        list.add("A");
//        list.add("A");
//        list.add("A");
//        list.add("B");
//        list.add("B");
//        list.add("B");
//        list.add("C");
//        list.add("C");
//        list.add("C");
//        list.add("C");
//        list.add("C");
//        list.add("C");
//        list.add("D");
//        list.add("D");
//        list.add("D");
//        list.add("D");
//        list.add("D");
//        list.add("D");
//
//
//        int start = list.indexOf("A");
//        int end = list.lastIndexOf("A");
//            System.out.println(start+"   "+end);
//            System.out.println("B:="+(end-start+1));
//String lastLetter="A";


        List<String> c = new ArrayList();
        c.add("l");
        c.add("o");
        c.add("v");
        c.add("e");
        c.add("1");
        System.out.println(c);
//        Collections.sort(c);
//        System.out.println(c);

//        Collections.shuffle(c);


//        Collections.fill(c, "青鸟52T25小龙");


Collections.rotate(c,3);

        System.out.println(c);











        assertEquals(4, 2 + 2);
    }
}