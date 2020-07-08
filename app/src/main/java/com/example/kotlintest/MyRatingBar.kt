package com.example.kotlintest

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import java.lang.Exception

class MyRatingBar : View {
    lateinit var selectPatternBitmap: Bitmap
    lateinit var normalPatternBitmap: Bitmap


    lateinit var mSelectPaint: Paint

    var starNum: Int = 0

    var currentGrade: Int = 2

    var space: Int = 35


    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        Log.i("hltag", "init....")
        val attr = context.obtainStyledAttributes(attrs, R.styleable.MyRatingBar)
        val selectPatternRes = attr.getResourceId(R.styleable.MyRatingBar_selectPattern, 0);
        val normalPatternRes = attr.getResourceId(R.styleable.MyRatingBar_normalPattern, 0);

        starNum = attr.getInteger(R.styleable.MyRatingBar_starNumb, 0);


        if (normalPatternRes == 0) {
            throw RuntimeException("need normalPattern res.")
        }
        if (selectPatternRes == 0) {
            throw RuntimeException("need selectPatternRes res.")
        }
        selectPatternBitmap = BitmapFactory.decodeResource(resources, selectPatternRes);
        normalPatternBitmap = BitmapFactory.decodeResource(resources, normalPatternRes);


        attr.recycle()
        mSelectPaint = Paint()

        mSelectPaint.isAntiAlias = true

//        mStartPaint.strokeWidth = myBorderWidth.toFloat()
//        mStartPaint.color = myStartColor
//        mStartPaint.style = Paint.Style.STROKE
//        mStartPaint.strokeJoin = Paint.Join.ROUND
//        mStartPaint.strokeCap = Paint.Cap.ROUND
//
//        mEndPaint = Paint()
//        mEndPaint.isAntiAlias = true
//        mEndPaint.strokeWidth = myBorderWidth.toFloat()
//        mEndPaint.color = myEndColor
//        mEndPaint.style = Paint.Style.STROKE
//        mEndPaint.strokeJoin = Paint.Join.ROUND
//        mEndPaint.strokeCap = Paint.Cap.ROUND


    }

    /**
    14      * convert dp to its equivalent px
    15      *
    16      * 将dp转换为与之相等的px
    17      */
    fun dp2px(context: Context, dp: Int): Int {
        val scale = context.getResources().getDisplayMetrics().density;
        return (dp * scale + 0.5f).toInt()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        val width = selectPatternBitmap.width * starNum + ((starNum - 1) * space);
        val height = selectPatternBitmap.height;



        setMeasuredDimension(width, height)
//        setMeasuredDimension(1000, 600)
    }


    override fun onDraw(canvas: Canvas) {
//        super.onDraw(canvas)

        for (index in 1 until starNum + 1) {
            Log.i("hltag", "index=${index}")
            var x = 0
            x = ((index - 1) * (selectPatternBitmap.width+space));
//            x=  if (index == 1) x else (x + space*2)

            if (index <= currentGrade) {
                canvas.drawBitmap(
                    selectPatternBitmap,
                    x.toFloat(),
                    0f,
                    mSelectPaint
                )

            } else {
                canvas.drawBitmap(
                    normalPatternBitmap,
                    x.toFloat(),
                    0f,
                    mSelectPaint
                )
            }
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        Log.i("hltag", "touch action = ${event?.action}")

        if (event.action == MotionEvent.ACTION_DOWN
            || event.action == MotionEvent.ACTION_MOVE
            || event.action == MotionEvent.ACTION_UP
            || event.action == MotionEvent.ACTION_CANCEL
        ) {
            Log.i("hltag", "touch event.x =${event.x}")
            var touchX = (event.x / (selectPatternBitmap.width+space)).toInt()
            if (touchX <= 0) {
                touchX = 0
            }
            if (touchX >= starNum) {
                touchX = starNum;
            }

            currentGrade = touchX
//                if (starNum ==touchX){
//                    return true
//                }
            Log.i("hltag", "touch currentGrade =${currentGrade}")
            invalidate()
            return true;

        }

        return super.onTouchEvent(event)


    }
}