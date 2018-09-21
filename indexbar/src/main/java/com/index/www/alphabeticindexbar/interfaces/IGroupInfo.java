package com.index.www.alphabeticindexbar.interfaces;

import com.index.www.alphabeticindexbar.bean.GroupInfo;

/**
     * StickItemDecoration需要通过GroupInfo，得知在整个list里位于position位置的item，在自己的小组内的具体信息
     * 从而得知它属于那个索引，他是该小组第一位时，要显示头部，位于最后一位时，滑动出页面时候，要有推移效果
     */
    public interface IGroupInfo {

        GroupInfo getGroupInfo(int position);
    }
