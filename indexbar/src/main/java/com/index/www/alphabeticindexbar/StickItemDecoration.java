package com.index.www.alphabeticindexbar;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.index.www.alphabeticindexdemo.right.bean.GroupInfo;

public class StickItemDecoration extends RecyclerView.ItemDecoration {
// 参考资料：https://blog.csdn.net/briblue/article/details/70211942
// RecyclerView探索之通过ItemDecoration实现StickyHeader效果

    private final Paint mTextPaint;


    private int mBgColor;
    private int mTextColor;
    private int mDividerHeight;
    private int mHeaderHeight;
    private int mTextsize;
    private float mTextOffsetX;


    private GroupInfoCallback mCallback;

    private Paint mBgPaint;
    private Paint.FontMetrics mFontMetrics;

    public StickItemDecoration(int bgColor, int textColor, int dividerHeight, int headerHeight, int textsize, int textOffsetX, GroupInfoCallback callback) {

        mBgColor = bgColor;
        mTextColor = textColor;
        mTextsize = textsize;
        mDividerHeight = dividerHeight;
        mHeaderHeight = headerHeight;
        mTextOffsetX = textOffsetX;
        mCallback = callback;


        mBgPaint = new Paint();

        mBgPaint.setAntiAlias(true);
        mBgPaint.setColor(mBgColor);


        mTextPaint = new Paint();
        mTextPaint.setColor(mTextColor);
        mTextPaint.setTextSize(mTextsize);
        mFontMetrics = mTextPaint.getFontMetrics();

    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);

        int position = parent.getChildAdapterPosition(view);

        if (mCallback != null) {
            GroupInfo groupInfo = mCallback.getGroupInfo(position);

            //如果是组内的第一个则将间距撑开为一个Header的高度，或者就是普通的分割线高度
            if (groupInfo != null && groupInfo.isFirstViewInGroup()) {
                outRect.top = mHeaderHeight;
            } else {
                outRect.top = mDividerHeight;
            }
        }
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDrawOver(c, parent, state);

        int childCount = parent.getChildCount();

        for (int i = 0; i < childCount; i++) {
            View view = parent.getChildAt(i);

            int index = parent.getChildAdapterPosition(view);


            if (mCallback != null) {

                GroupInfo groupinfo = mCallback.getGroupInfo(index);
                int left = parent.getPaddingLeft();
                int right = parent.getWidth() - parent.getPaddingRight();

                //屏幕上第一个可见的 ItemView 时，i == 0;
                if (i != 0) {


                    //只有组内的第一个ItemView之上才绘制
                    if (groupinfo.isFirstViewInGroup()) {

                        int top = view.getTop() - mHeaderHeight;

                        int bottom = view.getTop();
                        drawHeaderRect(c, groupinfo, left, top, right, bottom);

                    }

                } else {

                    //当 ItemView 是屏幕上第一个可见的View 时，不管它是不是组内第一个View
                    //它都需要绘制它对应的 StickyHeader。

                    // 还要判断当前的 ItemView 是不是它组内的最后一个 View

                    int top = parent.getPaddingTop();


                    if (groupinfo.isLastViewInGroup()) {
                        int suggestTop = view.getBottom() - mHeaderHeight;
                        // 当 ItemView 与 Header 底部平齐的时候，判断 Header 的顶部是否小于
                        // parent 顶部内容开始的位置，如果小于则对 Header.top 进行位置更新，
                        //否则将继续保持吸附在 parent 的顶部
                        if (suggestTop < top) {
                            top = suggestTop;
                        }
                    }

                    int bottom = top + mHeaderHeight;

                    drawHeaderRect(c, groupinfo, left, top, right, bottom);
                }

            }
        }
    }

    private void drawHeaderRect(Canvas c, GroupInfo groupinfo, int left, int top, int right, int bottom) {
        //绘制Header
        c.drawRect(left, top, right, bottom, mBgPaint);

        float titleX = left + mTextOffsetX;
        float titleY = bottom + mFontMetrics.bottom - mHeaderHeight / 2;
//        float titleY = bottom - mHeaderHeight / 2 + mTextsize / 2;

        //绘制Title
        String title = groupinfo.getTitle();

        c.drawText(title, titleX, titleY, mTextPaint);
    }


    /**
     * StickItemDecoration需要通过GroupInfo，得知在整个list里位于position位置的item，在自己的小组内的具体信息
     * 从而得知它属于那个索引，他是该小组第一位时，要显示头部，位于最后一位时，滑动出页面时候，要有推移效果
     */
    public interface GroupInfoCallback {
        GroupInfo getGroupInfo(int position);
    }


    public static class Builder {


        private int mBgColor = Color.LTGRAY;
        private int mTextColor = Color.BLUE;
        private int mDividerHeight = 1;
        private int mHeaderHeight = 150;
        private int mTextsize = 37;
        private int mTextOffsetX = 30;
        private GroupInfoCallback mCallback;


        public Builder dividerHeight(int dividerHeight) {
            mDividerHeight = dividerHeight;
            return this;
        }

        public Builder headerHeight(int headerHeight) {
            mHeaderHeight = headerHeight;
            return this;

        }

        public Builder textsize(int textsize) {
            mTextsize = textsize;
            return this;

        }


        public Builder textOffsetX(int textOffsetX) {
            mTextOffsetX = textOffsetX;
            return this;

        }

        public Builder bgColor(int bgColor) {
            mBgColor = bgColor;
            return this;

        }

        public Builder textColor(int textColor) {
            mTextColor = textColor;
            return this;

        }


        public Builder callback(GroupInfoCallback callback) {
            mCallback = callback;
            return this;

        }

        public StickItemDecoration build() {
            return new StickItemDecoration(mBgColor, mTextColor, mDividerHeight,
                    mHeaderHeight, mTextsize, mTextOffsetX, mCallback);
        }


    }


}
