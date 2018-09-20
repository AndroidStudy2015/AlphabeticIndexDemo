package com.index.www.alphabeticindexdemo.zotherJunkDevider;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

public class GridDividerItemDecoration extends RecyclerView.ItemDecoration {


    private int mHorizonalDividerHeight;
    private int mVerticalDividerWidth;
    private int mDividerColor ;
    int mSpanCount;
    private Paint mPaint;

    public GridDividerItemDecoration(int horizonalDividerHeight, int verticalDividerWidth, int dividerColor, int spanCount) {

        mHorizonalDividerHeight = horizonalDividerHeight;
        mVerticalDividerWidth = verticalDividerWidth;
        mDividerColor = dividerColor;
        mSpanCount = spanCount;

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(mDividerColor);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);

//        //第一个ItemView不需要在上面绘制分割线
        if (parent.getChildAdapterPosition(view) <2) {
            //这里直接硬编码为1px
            outRect.top = 0;
        }else {
            outRect.top = mHorizonalDividerHeight;

        }
        outRect.left = mVerticalDividerWidth;
        outRect.right = mVerticalDividerWidth;

//        if (parent.getChildAdapterPosition(view)% mSpanCount==0){
//            outRect.left = 0;
//            outRect.right = mVerticalDividerWidth;
//
//        }else
//        if (parent.getChildAdapterPosition(view)% mSpanCount==mSpanCount-1){
//            outRect.right = 0;
//            outRect.left = mVerticalDividerWidth;
//
//        }else {
//            outRect.left = mVerticalDividerWidth;
//            outRect.right = mVerticalDividerWidth;
//        }

    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);

        int childCount = parent.getChildCount();
        Log.e("ccc-==", "onDraw: " + childCount);
        for (int i = 0; i < childCount; i++) {
            View view = parent.getChildAt(i);
//
            int index = parent.getChildAdapterPosition(view);
//            //第一个ItemView不需要绘制
//            if (index == 0||index==1) {
//                continue;
//            }

            float dividerTop = view.getTop() - mHorizonalDividerHeight;
            float dividerLeft = parent.getPaddingLeft();
            float dividerBottom = view.getBottom();//主要是这里，原来是top,只是绘制上半部分，现在是bottom表示绘制所有位置
            float dividerRight = parent.getWidth() - parent.getPaddingRight();

            c.drawRect(dividerLeft, dividerTop, dividerRight, dividerBottom, mPaint);
        }
    }

}
