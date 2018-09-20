package com.index.www.alphabeticindexdemo.right.util;

import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;

import com.index.www.alphabeticindexbar.IndexBarView;
import com.index.www.alphabeticindexdemo.right.bean.ElementInfo;

import java.util.List;

public class InteractHelper {


    public static void updateIndexBar(IndexBarView indexBarView, LinearLayoutManager layoutManager, List<ElementInfo> sortList, List<String> indexLetterList) {
        //                滚动过程中，改变indexBar的字母颜色
        int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();
        Log.e("===", firstVisibleItemPosition + "");
        String firstLetter = sortList.get(firstVisibleItemPosition).getFirstLetter();
        indexBarView.setCurrentIndex(indexLetterList.indexOf(firstLetter));
    }


    public static boolean letRecyclerviewScrollToIndexPostion(int index, LinearLayoutManager layoutManager, List<String> firstLetterList, List<String> indexLetterList) {
//                https://blog.csdn.net/shanshan_1117/article/details/78780137 定位到指定位置，并且第一位显示

        int position = firstLetterList.indexOf(indexLetterList.get(index));
        if (position == -1) {

            return false;
        } else {
            layoutManager.scrollToPositionWithOffset(position, 0);
            return true;
        }


    }
}
