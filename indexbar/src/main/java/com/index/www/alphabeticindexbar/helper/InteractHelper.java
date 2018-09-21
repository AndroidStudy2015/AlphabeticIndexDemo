package com.index.www.alphabeticindexbar.helper;

import android.support.v7.widget.LinearLayoutManager;

import com.index.www.alphabeticindexbar.bean.ElementInfo;
import com.index.www.alphabeticindexbar.view.IndexBarView;

import java.util.List;

public class InteractHelper {

    /**
     * 滚动过程中，改变indexBar的字母颜色
     *
     * @param indexBarView
     * @param layoutManager
     * @param sortList         排好序的元素列表
     * @param indexLetterList indexBar里的索引列表
     */
    public static void updateIndexBar(IndexBarView indexBarView, LinearLayoutManager layoutManager, List<ElementInfo> sortList, List<String> indexLetterList) {
        int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();
        String firstLetter = sortList.get(firstVisibleItemPosition).getFirstLetter();
        indexBarView.setCurrentIndex(indexLetterList.indexOf(firstLetter));
    }

    /**
     * 让RecyclerView滚动到指定索引位置，并让这个位置处于RecyclerView的顶部
     * @param index indexBar触摸后，返回的那个索引
     * @param layoutManager
     * @param mSortList
     * @param indexLetterList
     * @return  如果手指触摸的字母，RecyclerView里面没有包含以这个字母开头的元素，那么返回false，
     */
    public static boolean letRecyclerviewScrollToIndexPostion(int index, LinearLayoutManager layoutManager, List<ElementInfo> mSortList, List<String> indexLetterList) {
//                https://blog.csdn.net/shanshan_1117/article/details/78780137 定位到指定位置，并且第一位显示

//        从indexBarView里得到这个索引所对应的字母
        String firstLetter = indexLetterList.get(index);
//        传入排序好的list，得到所有元素的首字母做成的集合（这个集合，可以看成是所有元素的一个精简列表）
        List<String> firstLetterListBySorted = SortHelper.getFirstLetterListBySorted(mSortList);
//      从精简列表里找到第一步得到的那个字母的位置，这就是要滚动到的位置
        int position = firstLetterListBySorted.indexOf(firstLetter);
        if (position == -1) {
            return false;
        } else {
            layoutManager.scrollToPositionWithOffset(position, 0);
            return true;
        }


    }
}
