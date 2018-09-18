package com.index.www.alphabeticindexdemo.right;

public class GroupInfo {
    //组号
    private int mGroupID;
    // Header 的 title
    private String mTitle;

    //ItemView 在组内的位置
    private int position;
    // 组的成员个数
    private int mGroupLength;


    public GroupInfo(int groupId, String title) {
        mGroupID = groupId;
        mTitle = title;
    }

    public int getGroupID() {
        return mGroupID;
    }

    public void setGroupID(int groupID) {
        mGroupID = groupID;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public int getPosition() {
        return position;
    }


    public void setPosition(int position) {
        this.position = position;
    }

    public boolean isFirstViewInGroup() {
        return position == 0;
    }

    public boolean isLastViewInGroup() {
        return position == mGroupLength - 1 && position >= 0;
    }


    public void setGroupLength(int groupLength) {
        this.mGroupLength = groupLength;
    }


}