package com.index.www.alphabeticindexbar;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.List;

public class IndexBarView extends View {
    Context mContext;
    private Paint mPaint;
    /**
     * 整个控件宽度
     */
    private int mTotalWidth;
    /**
     * 整个控件高度
     */
    private int mTotalHeight;
    /**
     * 控件上padding，也就是说，字母在此范围内不能绘制
     */
    private int mPaddingTop = 30;
    /**
     * 控件下padding，也就是说，字母在此范围内不能绘制
     */
    private int mPaddingBottom = 30;
    /**
     * 绘制字母的区域高度：
     * mUsefulHeight = mTotalHeight - mPaddingTop - mPaddingBottom;
     */
    private int mUsefulHeight;

    /**
     * 系统会根据 控件的有效填充高度、所传入的字母集合的数量，计算出每个字母的所属高度
     */
    private float mPerLetterAreaHeight;
    /**
     * 字母高度单位是px
     */
    private float mLetterSize;

    /**
     * 每个字母的高度占据mPerLetterAreaHeight的比例，这样计算出的字母高度，可以适配所有屏幕
     */
    float mLetterSizeScale = 0.85f;
    /**
     * 为了使得文本居中，绘制的辅助矩形
     */
    private Rect mTargetRect;

    /**
     * 不触摸的时候，字母索引的颜色
     */
    int mTextDefaultColor;
    /**
     * 触摸的时候，字母索引的颜色
     */
    int mTextPressColor;
    /**
     * 触摸时候，要不要改变索引字母颜色，默认为false，打开后，每次触摸down和up都会调用invalid去重绘onDraw，绘影响效率
     */
    boolean isTouchChangeColor = false;


    /**
     * 字母索引颜色
     */
    int mTextColor;
    /**
     * 触摸时候，要不要改变选择的那个索引字母颜色，默认为false，打开后，每次触摸down和move都会调用invalid去重绘onDraw，绘影响效率
     */
    boolean isChangeCurrentIndexColor = false;


    /**
     * 当前索引的位置
     */
    private int mCurrentIndex = 0;


    /**
     * 触摸到当前索引时候，字母要改变的颜色值
     */
    private int mCurrentIndexColor;

    /**
     * 控件没有点击时候的默认背景资源
     */
    private int mDefaultBackgroundResource = R.drawable.default_bg;

    /**
     * 控件点击时候的背景资源
     */
    private int mPressBackgroundResource = R.drawable.press_bg;


    /**
     * 所有的字母list
     */
    private List<String> mLetterList;


    /**
     * 字体样式
     */
    private Typeface mTypeface = Typeface.DEFAULT;


    /**
     * 大于1个字符的索引，为了美观，可以让其缩小点尺寸，例如：热门
     * 默认为1.0,即与1个字符的索引字母高度一样
     */
    private float mLongTextSmallScale = 1.0f;


    public IndexBarView(@NonNull Context context) {
        super(context);
        init(context);
    }

    public IndexBarView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);

    }

    private void init(Context context) {
        mContext = context;
        mPaint = new Paint();
        mPaint.setAntiAlias(true);


    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (mLetterList == null || mLetterList.size() == 0) {
            throw new RuntimeException("\n*******************************************************\n"
                    + "mLetterList（字母索引集合）不能为null,元素也不能为0，\n"
                    + "请调用 mIndexBarView.setLetterList(mLetterList) 设置\n"
                    + "**************************************************************\n");
        }
        mTotalWidth = w;
        mTotalHeight = h;
        mUsefulHeight = mTotalHeight - mPaddingTop - mPaddingBottom;
        mPerLetterAreaHeight = mUsefulHeight * 1.0f / mLetterList.size();
        mLetterSize = mPerLetterAreaHeight * mLetterSizeScale;
        mTargetRect = new Rect();
        setBackgroundResource(mDefaultBackgroundResource);
        mTextColor = mTextDefaultColor;

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setTextSize(mLetterSize);

        int l = 0;
        int t;
        int r = mTotalWidth;
        int b;

        for (int i = 0; i < mLetterList.size(); i++) {
            String content = mLetterList.get(i);

            t = (int) (mPerLetterAreaHeight * (i)) + mPaddingTop;
            if (content.length() > 1) {
                b = (int) (t + mPerLetterAreaHeight * mLongTextSmallScale);

            } else {
                b = (int) (t + mPerLetterAreaHeight);

            }
            drawTextInCenterPosition(canvas, mTargetRect, l, t, r, b, content, i);
        }

    }

    /**
     * @param canvas     画布
     * @param targetRect 一个矩形对象，不要再onDraw里去new 这个对象，因为这样很消耗资源
     * @param l          你要绘制的文字所处的矩形的最左侧
     * @param t          最上侧
     * @param r          最右侧
     * @param b          最下侧
     */
    private void drawTextInCenterPosition(Canvas canvas, Rect targetRect, int l, int t, int r, int b, String content, int index) {
        //构建你的文本所处的矩形
        targetRect.set(l, t, r, b);
        mPaint.setColor(Color.TRANSPARENT);
        canvas.drawRect(targetRect, mPaint);

        //实现竖向居中
        Paint.FontMetricsInt fontMetrics = mPaint.getFontMetricsInt();// 转载请注明出处：http://blog.csdn.net/hursing
        int baseline = (targetRect.bottom + targetRect.top - fontMetrics.bottom - fontMetrics.top) / 2;

        // 下面这行是实现水平居中，drawText对应改为传入targetRect.centerX()
        mPaint.setTextAlign(Paint.Align.CENTER);


        //绘制文本
        mPaint.setColor(mTextColor);
        if (index == mCurrentIndex) {
            mPaint.setColor(mCurrentIndexColor);

        }
        if (content.length() > 1) {
            mPaint.setTextSize(mLetterSize * mLongTextSmallScale);
        } else {
            mPaint.setTextSize(mLetterSize);

        }
        mPaint.setTypeface(mTypeface);//设置字体类型
        canvas.drawText(content, targetRect.centerX(), baseline, mPaint);

    }

    int lastIndex = 0;
    int index;
    float y;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:

                if (isTouchChangeColor) {
                    mTextColor = mTextPressColor;
                    invalidate();
                }
                setBackgroundResource(mPressBackgroundResource);

                y = event.getY() - mPaddingTop;
                index = (int) (y / mPerLetterAreaHeight);
                if (index < 0) {
                    index = 0;
                }
                if (index > mLetterList.size() - 1) {
                    index = mLetterList.size() - 1;
                }

                if (mOnTouchIndexBarListener != null) {
                    if (isChangeCurrentIndexColor) {
                        mCurrentIndex = index;
                        invalidate();
                    }


                    mOnTouchIndexBarListener.onTouchIndexBar(index);
                    mOnTouchIndexBarListener.onTouchIndexBarAllTheTime(index, y);
                }
                lastIndex = index;

                break;
            case MotionEvent.ACTION_MOVE:

                y = event.getY() - mPaddingTop;
                index = (int) (y / mPerLetterAreaHeight);
                if (index < 0) {
                    index = 0;
                }
                if (index > mLetterList.size() - 1) {
                    index = mLetterList.size() - 1;
                }


                if (index != lastIndex) {
                    if (mOnTouchIndexBarListener != null) {
                        if (isChangeCurrentIndexColor) {
                            mCurrentIndex = index;
                            invalidate();
                        }
                        mOnTouchIndexBarListener.onTouchIndexBar(index);
                    }
                    lastIndex = index;
                }
                if (mOnTouchIndexBarListener != null) {
                    mOnTouchIndexBarListener.onTouchIndexBarAllTheTime(index, y);
                }


                break;
            case MotionEvent.ACTION_UP:


                if (isTouchChangeColor) {
                    mTextColor = mTextDefaultColor;
                    invalidate();
                }
                setBackgroundResource(mDefaultBackgroundResource);

                if (mOnTouchIndexBarListener != null) {
                    mOnTouchIndexBarListener.onAnctionUp();
                }
                break;

            default:


                break;

        }


        return true;

    }


    public void setLetterList(List<String> letterList) {
        mLetterList = letterList;
    }

    public void setPaddingTop(int paddingTop) {
        mPaddingTop = paddingTop;
    }

    public void setPaddingBottom(int paddingBottom) {
        mPaddingBottom = paddingBottom;
    }



    public void setLetterSizeScale(float letterSizeScale) {
        mLetterSizeScale = letterSizeScale;
    }

    public void setTypeface(Typeface typeface) {
        mTypeface = typeface;
    }

    public void setDefaultBackgroundResource(int defaultBackgroundResource) {
        mDefaultBackgroundResource = defaultBackgroundResource;
    }

    public void setPressBackgroundResource(int pressBackgroundResource) {
        mPressBackgroundResource = pressBackgroundResource;
    }


    public void setTextDefaultColor(int textDefaultColor) {
        mTextDefaultColor = textDefaultColor;
    }

    public void setTextPressColor(int textPressColor) {
        mTextPressColor = textPressColor;
        isTouchChangeColor = true;
    }

    public void setCurrentIndexColor(int currentIndexColor) {
        mCurrentIndexColor = currentIndexColor;
        isChangeCurrentIndexColor = true;
    }

    public void setLongTextSmallScale(float longTextSmallScale) {
        mLongTextSmallScale = longTextSmallScale;
    }

    /**
     * 当RecyclerView滑动时候，通过调用此方法，改变indexbarview的当前选定字母的颜色
     *
     * @param currentIndex
     */
    int mLastCurrentIndex = -1;

    public void setCurrentIndex(int currentIndex) {
        mCurrentIndex = currentIndex;
        if (mCurrentIndex != mLastCurrentIndex) {//为了提高绘制效率，只有当前索引与上次索引不同时，才会触发重绘
            mLastCurrentIndex=mCurrentIndex;
            invalidate();
        }

    }

    public void setOnTouchIndexBarListener(OnTouchIndexBarListener onTouchIndexBarListener) {
        mOnTouchIndexBarListener = onTouchIndexBarListener;
    }


    private OnTouchIndexBarListener mOnTouchIndexBarListener;

    public interface OnTouchIndexBarListener {
        /**
         * 当手指选中1个字母时，在一个字母的区间范围内，只调用1次，提高效率
         * （例如：文本内容、文本圈颜色在自己范围内不要变化，对应的列表moveToPosition方法只调用一次）
         *
         * @param index
         */
        void onTouchIndexBar(int index);

        /**
         * 当手指触摸indexBar时，一直不停地回调此方法，主要是为了让指示文本圈随着手指一直移动
         *
         * @param index
         * @param y
         */
        void onTouchIndexBarAllTheTime(int index, float y);

        /**
         * 当手指松开时，回调此方法，用于隐藏指示文本，indexBar回复透明状态
         */
        void onAnctionUp();
    }
}
