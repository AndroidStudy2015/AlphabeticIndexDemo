package com.index.www.alphabeticindexbar.interfaces;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.index.www.alphabeticindexbar.bean.GroupInfo;

public interface IDrawHeader {
    void drawHeaderRect(Canvas canvas, Paint paint, GroupInfo groupinfo,
                        int left, int top, int right, int bottom);

}
