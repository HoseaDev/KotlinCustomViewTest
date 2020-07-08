package com.example.kotlintest

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.View
import kotlin.math.min

class QQStepView : View {
    private var myBorderWidth: Int = 20;
    private var myStartColor: Int = Color.BLUE;
    private var myEndColor: Int = Color.BLUE;
    private var myTextSize: Int = 20;
    private var myTextColor: Int = Color.RED;

    private var mStepMax: Int = 100;
    private var mCurrentStep: Int = 90;


    private lateinit var mStartPaint: Paint;
    private lateinit var mEndPaint: Paint;
    private lateinit var mTextPaint: Paint;

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        val attr = context.obtainStyledAttributes(attrs, R.styleable.QQStepView)
        myTextSize =
            attr.getDimension(R.styleable.QQStepView_myCentTextSize, sp2px(context, 25f)).toInt()
        myBorderWidth =
            attr.getDimension(R.styleable.QQStepView_myBorderWidth, dp2px(context, 8f)).toInt()
        myStartColor = attr.getColor(
            R.styleable.QQStepView_myStartColor,
            context.resources.getColor(R.color.colorAccent)
        );
        myEndColor = attr.getColor(
            R.styleable.QQStepView_myEndColor,
            context.resources.getColor(R.color.colorPrimary)
        );
        myTextColor = attr.getColor(
            R.styleable.QQStepView_myCentTextColor,
            context.resources.getColor(R.color.colorAccent)
        );

        attr.recycle()
        mStartPaint = Paint()
        mStartPaint.isAntiAlias = true
        mStartPaint.strokeWidth = myBorderWidth.toFloat()
        mStartPaint.color = myStartColor
        mStartPaint.style = Paint.Style.STROKE
        mStartPaint.strokeJoin = Paint.Join.ROUND
        mStartPaint.strokeCap = Paint.Cap.ROUND

        mEndPaint = Paint()
        mEndPaint.isAntiAlias = true
        mEndPaint.strokeWidth = myBorderWidth.toFloat()
        mEndPaint.color = myEndColor
        mEndPaint.style = Paint.Style.STROKE
        mEndPaint.strokeJoin = Paint.Join.ROUND
        mEndPaint.strokeCap = Paint.Cap.ROUND

        mTextPaint = Paint()
        mTextPaint.isAntiAlias = true
        mTextPaint.textSize = myTextSize.toFloat()
        mTextPaint.color = myTextColor
        mTextPaint.style = Paint.Style.FILL


    }

    fun sp2px(context: Context, spValue: Float): Float {
        val fontScale = context.resources.displayMetrics.scaledDensity
        return (spValue * fontScale + 0.5f).toFloat()
    }

    /**
    14      * convert dp to its equivalent px
    15      *
    16      * 将dp转换为与之相等的px
    17      */
    fun dp2px(context: Context, dp: Float): Float {
        val scale = context.getResources().getDisplayMetrics().density;
        return (dp * scale + 0.5f)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val width = MeasureSpec.getSize(widthMeasureSpec);
        val height = MeasureSpec.getSize(heightMeasureSpec);

        setMeasuredDimension(width.coerceAtMost(height), min(width, height));
    }


    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)


        val center = width / 2;
        Log.i("QQStepView", "center= ${center}")
        val radius = center - myBorderWidth / 2;
        Log.i("QQStepView", "radius= ${radius}")
        Log.i("QQStepView", "myBorderWidth= ${myBorderWidth}")
        Log.i("QQStepView", "myBorderWidth/2= ${myBorderWidth / 2}")

        val arcRect = RectF(
            (center - radius).toFloat(),
            (center - radius).toFloat(),
            (center + radius).toFloat(),
            (center + radius).toFloat()
        )
        //这个轴是顺时针的

        //画开始圆
        canvas?.drawArc(arcRect, 135f, 270f, false, mStartPaint)

        if (mStepMax == 0) {
            return
        }

        val sweepAngle = mCurrentStep.div(mStepMax.toFloat())
        Log.i("QQStepView", "sweepAngle = ${sweepAngle}")
        //画结束 圆
        canvas?.drawArc(arcRect, 135f, sweepAngle * 270f, false, mEndPaint)


        //画文字
        val stepText = mCurrentStep.toString()
        val textRect = Rect()
        mTextPaint.getTextBounds(stepText, 0, stepText.length, textRect)
        val fontMetrics = mTextPaint.fontMetrics;
        val dy = (fontMetrics.bottom - fontMetrics.top) / 2 - fontMetrics.bottom
        Log.i("QQStepView", "dy = ${dy}")
        val baseLine = height / 2 + dy
        canvas?.drawText(stepText, center - (textRect.width() / 2).toFloat(), baseLine, mTextPaint)
    }
    public fun setStepMax(num:Int){
        mStepMax = num
    }

    public fun setCurrentStep(num:Int){
        mCurrentStep = num
        invalidate()
    }
}