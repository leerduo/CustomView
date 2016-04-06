package me.jarvischen.customviewviewgroup.taglayout;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chenfuduo on 2016/4/5.
 */
public class TagLayout extends ViewGroup {


    private static final String TAG = TagLayout.class.getSimpleName();

    public TagLayout(Context context) {
        this(context, null);
    }

    public TagLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TagLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    //存储所有的View 一行一行的存储
    private List<List<View>> allViews = new ArrayList<>();

    //存储每一行的高度
    private List<Integer> lineHeights = new ArrayList<>();



    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
        int modeWidth = MeasureSpec.getMode(widthMeasureSpec);
        int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);
        int modeHeight = MeasureSpec.getMode(heightMeasureSpec);

        //wrap_content
        int width = 0;
        int height = 0;

        //记录每一行的宽度和高度
        int lineWidth = 0;
        int lineHeight = 0;

        int childCount = getChildCount();

        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            //测量子View的宽和高
            measureChild(child, widthMeasureSpec, heightMeasureSpec);
            //得到子View的LayoutParams
            MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();
            //子View占据的宽度
            int childWidth = child.getMeasuredWidth() + lp.leftMargin + lp.rightMargin;
            //子View占据的高度
            int childHeight = child.getMeasuredHeight() + lp.topMargin + lp.bottomMargin;

            //换行
            if (lineWidth + childWidth > sizeWidth - getPaddingLeft() - getPaddingRight()) {
                //对比得到最大的宽度
                width = Math.max(width, lineWidth);
                //重置lineWidth
                lineWidth = childWidth;
                //记录行高
                height += lineHeight;
                lineHeight = childHeight;
            } else {//未换行
                //叠加行宽
                lineWidth += childWidth;
                //得到当前行最大的高度
                lineHeight = Math.max(lineHeight, childHeight);
            }
            //到达最后一个控件
            if (i == childCount - 1) {
                width = Math.max(lineWidth, width);
                height += lineHeight;
            }
        }


        Log.e(TAG, "onMeasure: sizeWidth=" + sizeWidth);
        Log.e(TAG, "onMeasure: sizeHeight=" + sizeHeight);
        Log.e(TAG, "onMeasure: modeWidth=" + modeWidth);
        Log.e(TAG, "onMeasure: modeHeight=" + modeHeight);

        setMeasuredDimension(modeWidth == MeasureSpec.EXACTLY ? sizeWidth : width + getPaddingRight() + getPaddingLeft(),
                modeHeight == MeasureSpec.EXACTLY ? sizeHeight : height + getPaddingBottom() + getPaddingTop());
    }


    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        allViews.clear();
        lineHeights.clear();

        //当前ViewGroup的宽度
        int width = getWidth();
        int linwWidth = 0;
        int lineHeight = 0;

        List<View> lineViews = new ArrayList<>();
        int childCount = getChildCount();

        for (int i = 0; i < childCount; i++) {
            View view = getChildAt(i);
            MarginLayoutParams lp = (MarginLayoutParams) view.getLayoutParams();
            int measuredWidth = view.getMeasuredWidth();
            int measuredHeight = view.getMeasuredHeight();

            //如果需要换行
            if (measuredWidth + linwWidth + lp.leftMargin + lp.rightMargin > width - getPaddingLeft() - getPaddingRight()) {
                lineHeights.add(lineHeight);
                allViews.add(lineViews);

                //重置行宽和行高
                linwWidth = 0;
                lineHeight = measuredHeight + lp.topMargin + lp.bottomMargin;

                lineViews = new ArrayList<>();
            }
            linwWidth += measuredWidth + lp.leftMargin + lp.rightMargin;
            lineHeight = Math.max(lineHeight, measuredHeight + lp.topMargin + lp.bottomMargin);
            lineViews.add(view);
        }

        //这两行代码处理最后一行的情况
        lineHeights.add(lineHeight);
        allViews.add(lineViews);

        int left = getPaddingLeft();
        int top = getPaddingTop();
        //行数
        int lineNumbers = allViews.size();

        for (int i = 0; i < lineNumbers; i++) {
            //当前行的所有的View
            lineViews = allViews.get(i);
            //当前行的高度
            lineHeight = lineHeights.get(i);
            for (int j = 0; j < lineViews.size(); j++) {
                View child = lineViews.get(j);
                //判断child的状态
                if (child.getVisibility() == View.GONE) {
                    continue;
                }
                MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();
                //lc=left child
                int lc = left + lp.leftMargin;
                int tc = top + lp.topMargin;
                int rc = lc + child.getMeasuredWidth();
                int bc = tc + child.getMeasuredHeight();
                //为子View进行布局
                child.layout(lc, tc, rc, bc);
                left += child.getMeasuredWidth() + lp.leftMargin + lp.rightMargin;
            }

            //下一行的话
            left = getPaddingLeft();
            top += lineHeight;
        }
    }


    /**
     * 与当前ViewGroup对应的LayoutParams
     *
     * @PARAM ATTRS
     * @RETURN
     */
    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }
}
