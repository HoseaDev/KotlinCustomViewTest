package com.example.kotlintest

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.ViewGroup
import androidx.core.view.marginTop

class TagLayout : ViewGroup {
    val TAG = "TAGLAYOUT"

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val childCount = childCount;

        //每个子child相加的行宽
        var currentLineWidth = 0
        val width = MeasureSpec.getSize(widthMeasureSpec)
        var lineMaxHeight = 0
        var allHeight = 0;
        var initLineHeight = 0

        for (index in 0 until childCount) {
            //测量子View
            val childView = getChildAt(index)
            val layoutParams = childView.layoutParams as MarginLayoutParams;
            measureChild(childView, widthMeasureSpec, heightMeasureSpec)
            lineMaxHeight = Math.max(
                lineMaxHeight,
                childView.measuredHeight + layoutParams.topMargin + layoutParams.bottomMargin
            )
            //如果子View相加的宽度加于父布局宽度就直接下一行，重置宽度，高度再叠加
            if (currentLineWidth + childView.measuredWidth + layoutParams.leftMargin + layoutParams.rightMargin > width) {
                //换行后把上一行的最高的高度和当前控件给加给所有高度，然后再重置
                allHeight += childView.measuredHeight + layoutParams.topMargin;
                lineMaxHeight=0
                currentLineWidth =
                    childView.measuredWidth + layoutParams.leftMargin + layoutParams.rightMargin;
                initLineHeight = childView.measuredHeight + layoutParams.topMargin
            } else {
                //同一行
                currentLineWidth += childView.measuredWidth + layoutParams.leftMargin + layoutParams.rightMargin
            }

            if (initLineHeight != 0 && initLineHeight < lineMaxHeight) {
                //中间行高变了,找到他们的差值加上
                allHeight += lineMaxHeight - initLineHeight
                initLineHeight = lineMaxHeight
            }
            //只有一行的情况下。
            if (allHeight == 0) {
                allHeight = lineMaxHeight
            }
        }


        Log.i(TAG, "width=${width},height=${allHeight}")
        setMeasuredDimension(width, allHeight)

    }


    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        var top = 0;
        var bottom = 0;
        var left = 0;
        var right = 0;

        //每个子child相加的行宽
        var currentLineWidth = 0
        val width = measuredWidth
        var lineMaxHeight = 0



        for (index in 0 until childCount) {
            val childView = getChildAt(index);
            val layoutParams = childView.layoutParams as MarginLayoutParams;
            lineMaxHeight = Math.max(
                lineMaxHeight,
                childView.measuredHeight + layoutParams.topMargin + layoutParams.bottomMargin
            )
            if (currentLineWidth + childView.measuredWidth + layoutParams.leftMargin + layoutParams.rightMargin > width) {
                //换行后把上一行的最高的高度和当前控件给加给所有高度，然后再重置
                top += lineMaxHeight
                currentLineWidth =
                    childView.measuredWidth + layoutParams.leftMargin + layoutParams.rightMargin
                left = 0
                lineMaxHeight = childView.measuredHeight + layoutParams.topMargin
            } else {
                //同一行
                currentLineWidth += childView.measuredWidth + layoutParams.leftMargin + layoutParams.rightMargin
            }
//            top = childView.measuredHeight + layoutParams.topMargin;
            right =
                left + childView.measuredWidth + layoutParams.rightMargin + layoutParams.leftMargin
            bottom =
                top + childView.measuredHeight + layoutParams.topMargin + layoutParams.bottomMargin;



            Log.i(TAG, "left=${left},top=${top},right=${right},bottom=${bottom}")
            childView.layout(
                left + layoutParams.leftMargin,
                top + layoutParams.topMargin,
                right + layoutParams.rightMargin,
                bottom + layoutParams.bottomMargin
            )
            left += childView.measuredWidth + layoutParams.rightMargin + layoutParams.leftMargin
        }

    }

    override fun generateLayoutParams(attrs: AttributeSet?): LayoutParams {
        return MarginLayoutParams(context, attrs)
    }
}