package com.index.www.alphabeticindexbar.decoration;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.index.www.alphabeticindexbar.bean.GroupInfo;
import com.index.www.alphabeticindexbar.impl.drawheader.DrawHeaderDefaultImpl;
import com.index.www.alphabeticindexbar.interfaces.IDrawHeader;
import com.index.www.alphabeticindexbar.interfaces.IGroupInfo;

public class StickItemDecoration extends RecyclerView.ItemDecoration {
// 参考资料：https://blog.csdn.net/briblue/article/details/70211942
// RecyclerView探索之通过ItemDecoration实现StickyHeader效果


    private int mDividerHeight;
    private int mHeaderHeight;
    private IGroupInfo mIGroupInfo;
    private final IDrawHeader mIDrawHeader;
    private Paint mPaint;

    public StickItemDecoration(int dividerHeight, int headerHeight, IGroupInfo iGroupInfo, IDrawHeader iDrawHeader) {

        mDividerHeight = dividerHeight;
        mHeaderHeight = headerHeight;
        mIGroupInfo = iGroupInfo;
        mIDrawHeader = iDrawHeader;
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);

        int position = parent.getChildAdapterPosition(view);

        if (mIGroupInfo != null) {
            GroupInfo groupInfo = mIGroupInfo.getGroupInfo(position);

            //如果是组内的第一个则将间距撑开为一个Header的高度，不然就是普通的分割线高度
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


            if (mIGroupInfo != null) {

                GroupInfo groupinfo = mIGroupInfo.getGroupInfo(index);
                int left = parent.getPaddingLeft();
                int right = parent.getWidth() - parent.getPaddingRight();
                //屏幕上第一个可见的 ItemView 时，i == 0;
                if (i != 0) {
                    //只有组内的第一个ItemView之上才绘制
                    if (groupinfo.isFirstViewInGroup()) {
                        int top = view.getTop() - mHeaderHeight;
                        int bottom = view.getTop();
                        mIDrawHeader.drawHeaderRect(c, mPaint, groupinfo, left, top, right, bottom);
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

                    mIDrawHeader.drawHeaderRect(c, mPaint, groupinfo, left, top, right, bottom);

                }
            }
        }
    }


    public static class Builder {


        private int mDividerHeight = 1;
        private int mHeaderHeight = 150;

        private IGroupInfo mIGroupInfo;
        private IDrawHeader mIDrawHeader = new DrawHeaderDefaultImpl();


        public Builder dividerHeight(int dividerHeight) {
            mDividerHeight = dividerHeight;
            return this;
        }

        public Builder headerHeight(int headerHeight) {
            mHeaderHeight = headerHeight;
            return this;

        }


        public Builder iGroupInfo(IGroupInfo iGroupInfo) {
            mIGroupInfo = iGroupInfo;
            return this;

        }

        public Builder iDrawHeader(IDrawHeader iDrawHeader) {
            mIDrawHeader = iDrawHeader;
            return this;

        }

        public StickItemDecoration build() {
            if (mIGroupInfo == null) {
                throw new RuntimeException("必须通过iGroupInfo(IGroupInfo)设置分组信息,你可以传入默认实现类GroupInfoDefalutImpl(mSortList)");
            }
            return new StickItemDecoration(mDividerHeight, mHeaderHeight, mIGroupInfo, mIDrawHeader);
        }


    }


}
