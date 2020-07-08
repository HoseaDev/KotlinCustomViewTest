package com.example.kotlintest

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.view.View
import android.widget.TextView

class TrackTextView : TextView {
    private var currentProgress: Float = 0f
    private var mOriginalColor: Int = 0
    private var mChangeColor: Int = 0

    private lateinit var mOriginalPaint: Paint;
    private lateinit var mChangePaint: Paint;
    private var direction: Direction = Direction.RIGHT_TO_LEFT;

    enum class Direction {
        LEFT_TO_RIGHT, RIGHT_TO_LEFT

    }

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        val attr = context.obtainStyledAttributes(attrs, R.styleable.TrackTextView)
        mOriginalColor = attr.getColor(
            R.styleable.TrackTextView_myOriginalColor,
            context.resources.getColor(R.color.black)
        );
        mChangeColor = attr.getColor(
            R.styleable.TrackTextView_myChangeColor,
            context.resources.getColor(R.color.colorAccent)
        );

        attr.recycle()
        mOriginalPaint = getPaint(mOriginalColor)
        mChangePaint = getPaint(mChangeColor)
    }

    fun getPaint(color: Int): Paint {
        val paint = Paint()
        paint.isAntiAlias = true
        paint.color = color
        paint.isDither = true
        paint.textSize = textSize
        return paint
    }

    override fun onDraw(canvas: Canvas) {
        //不要用系统的。会去调textView
//        super.onDraw(canvas)
        val middle = (currentProgress * width).toInt()

        if (direction == Direction.LEFT_TO_RIGHT) {
            drawText(canvas, mOriginalPaint, middle, width)
            drawText(canvas, mChangePaint, 0, middle)
        } else {
            drawText(canvas, mOriginalPaint, 0, width - middle)
            drawText(canvas, mChangePaint, width - middle, width)
        }


    }

    private fun drawText(canvas: Canvas, paint: Paint, start: Int, end: Int) {
        //先保存画布。后面再恢复。clip才会有用
        canvas.save()
        val rect = Rect(start, 0, end, height);
        canvas.clipRect(rect)
        val text = text.toString();
        val textBound = Rect()
        paint.getTextBounds(text, 0, text.length, textBound)
        val x = width / 2 - textBound.width() / 2
        val fontMetrics = paint.getFontMetrics()
        val dy = (fontMetrics.bottom - fontMetrics.top) / 2 - fontMetrics.bottom
        val baseLine = height / 2 + dy
        canvas.drawText(text, x.toFloat(), baseLine, paint)
        canvas.restore()
    }

    public fun setDirection(direction: Direction) {
        this.direction = direction;
    }

    public fun setCurrentProgress(process: Float) {
        currentProgress = process;
        invalidate()
    }
}