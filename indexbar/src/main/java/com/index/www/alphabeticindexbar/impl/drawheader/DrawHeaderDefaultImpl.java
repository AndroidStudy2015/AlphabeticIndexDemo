package com.index.www.alphabeticindexbar.impl.drawheader;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.index.www.alphabeticindexbar.bean.GroupInfo;
import com.index.www.alphabeticindexbar.interfaces.IDrawHeader;

public class DrawHeaderDefaultImpl implements IDrawHeader {

    @Override
    public void drawHeaderRect(Canvas canvas, Paint paint, GroupInfo groupinfo, int left, int top, int right, int bottom) {
        int bgColor = Color.CYAN;
        int textColor = Color.YELLOW;
        int textSize = 20;
        int textOffsetX = 30;

        //绘制Header
        paint.setColor(bgColor);
        canvas.drawRect(left, top, right, bottom, paint);


        //绘制Title
        paint.setColor(textColor);
        paint.setTextSize(textSize);
        Paint.FontMetrics fontMetrics = paint.getFontMetrics();
        float titleX = left + textOffsetX;
        float titleY = ((bottom + top - fontMetrics.bottom - fontMetrics.top) / 2);//竖直居中的位置
        String title = groupinfo.getTitle();
        canvas.drawText(title, titleX, titleY, paint);

    }
}
