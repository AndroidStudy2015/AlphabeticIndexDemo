package com.index.www.alphabeticindexdemo.right.util;

import com.index.www.alphabeticindexdemo.right.decoration.StickItemDecoration;
import com.index.www.alphabeticindexdemo.right.bean.ElementInfo;
import com.index.www.alphabeticindexdemo.right.bean.GroupInfo;

import java.util.List;

public class GroupInfoCallbackFactory {



    public static StickItemDecoration.GroupInfoCallback getInstance(final List<ElementInfo> sortList, final List<String> indexLetterList, final List<String> firstLetterList) {

//        这个方法，主要是把所有的元素列表进行分组（按照首字母分组）
//        例如：AAAAAAABBBBBBBCCCCC,分为3组，得到分组信息
//

        StickItemDecoration.GroupInfoCallback callback = new StickItemDecoration.GroupInfoCallback() {
            @Override
            public GroupInfo getGroupInfo(int position) {

                //        给定一个在全部List里的position
                ElementInfo elementInfo = sortList.get(position);
                //        得到这个元素的首字母
                String firstLetter = elementInfo.getFirstLetter();
                //  new 分组对象
                GroupInfo groupInfo = new GroupInfo(firstLetter);
                // 最主要的在这里，
                int startIndex = firstLetterList.indexOf(firstLetter);
                int endIndex = firstLetterList.lastIndexOf(firstLetter);
                // 计算出所在分组的组内成员个数（截止位置-起始位置+1）
                groupInfo.setGroupLength(endIndex - startIndex + 1);
                // 计算出当前position的元素，在自己的小组内的位置
                groupInfo.setPosition(position - firstLetterList.indexOf(firstLetter));

//                以上定义了分组信息，主要是想利用 isFirstViewInGroup  和  isLastViewInGroup 来控制粘性头部的显示问题

//                /**
//                 * 分组逻辑，这里为了测试每10个数据为一组。大家可以在实际开发中
//                 * 替换为真正的需求逻辑
//                 */
//                int groupId = position / 10;//groupId没有用
//                int index = position % 10;
//                GroupInfo groupInfo = new GroupInfo(groupId, mIndexLetterList.get(groupId));
//                groupInfo.setPosition(index);
//                groupInfo.setGroupLength(10);
                return groupInfo;
            }
        };

        return callback;
    }


}
