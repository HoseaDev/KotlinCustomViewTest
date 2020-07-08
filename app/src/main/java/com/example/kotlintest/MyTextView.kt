package com.example.kotlintest

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.TextView
import java.util.jar.Attributes
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.widget.LinearLayout


class MyTextView : LinearLayout {
    private lateinit var mPaint: Paint
    private var mTextSize: Float = 1f
    private var mTextColor: Int = 1
    private var mText: String = ""

    //    constructor(context: Context:attr:Attributes):this{
//
//    }
    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        val attr = context.obtainStyledAttributes(attrs, R.styleable.MyTextView)
        mTextSize = attr.getDimension(R.styleable.MyTextView_myTextSize, sp2px(context, 15f))
        mText = attr.getString(R.styleable.MyTextView_myText) ?: ""
        mTextColor = attr.getColor(
            R.styleable.MyTextView_myTextColor,
            context.resources.getColor(R.color.colorAccent)
        );

        attr.recycle()
        mPaint = Paint()
        mPaint.textSize = mTextSize.toFloat()

    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     *
     * @param spValue
     * @param fontScale
     * （DisplayMetrics类中属性scaledDensity）
     * @return
     */
    fun sp2px(context: Context, spValue: Float): Float {
        val fontScale = context.resources.displayMetrics.scaledDensity
        return (spValue * fontScale + 0.5f).toFloat()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val widthModel = MeasureSpec.getMode(widthMeasureSpec)
        val heightModel = MeasureSpec.getMode(heightMeasureSpec)
        var width = MeasureSpec.getSize(widthMeasureSpec)


        //wrap_content
        if (widthModel == MeasureSpec.AT_MOST) {
            val rect = Rect()

            mPaint.getTextBounds(mText, 0, mText.length, rect)
            width = rect.width() + paddingLeft + paddingRight
        }
        var height = MeasureSpec.getSize(heightMeasureSpec)
        if (heightModel == MeasureSpec.AT_MOST) {
            val rect = Rect()
            mPaint.getTextBounds(mText, 0, mText.length, rect)
            height = rect.height() + paddingTop + paddingBottom
        }
        setMeasuredDimension(width, height)

    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        val fontMetrics = mPaint.fontMetrics;
        Log.i(
            "hltag", "top = ${fontMetrics.top}  frontMetrics.bottom = ${fontMetrics.bottom} " +
                    "frontMetrics.descent = ${fontMetrics.descent}" +
                    "frontMetrics.ascent = ${fontMetrics.ascent}" +
                    ""
        )
        Log.i("hltag", "getHeight()/2 = ${height / 2}")
        val dy = (fontMetrics.bottom - fontMetrics.top) / 2 - fontMetrics.bottom
        Log.i("hltag", "dy = ${dy}")
        val baseLine = height / 2 + dy
        Log.i("hltag", "baseLine = ${baseLine}")
        val x = paddingLeft;
        canvas?.drawText(mText, x.toFloat(), baseLine, mPaint)
    }

}