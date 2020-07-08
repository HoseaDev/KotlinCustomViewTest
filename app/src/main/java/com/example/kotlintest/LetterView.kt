package com.example.kotlintest

import android.content.Context
import android.content.res.Resources
import android.graphics.Canvas
import android.graphics.Paint
import android.os.Message
import android.util.AttributeSet
import android.util.DisplayMetrics
import android.view.MotionEvent
import android.view.View


class LetterView : View {
    // 26个字母
    var selectLetter: String = ""
    var letters = arrayOf(
        "A", "B", "C", "D", "E", "F", "G", "H", "I",
        "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
        "W", "X", "Y", "Z", "#"
    )
    var mSelectColor: Int = 0
    var mTextColor: Int = 0
    lateinit var mTextPaint: Paint
    lateinit var mSelectPaint: Paint
    var isTouch:Boolean =false
    val handle = android.os.Handler(object :android.os.Handler.Callback{
        override fun handleMessage(msg: Message): Boolean {
            return true
        }

    })
    constructor(context: Context?) : this(context, null)
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        val typeArray = context!!.obtainStyledAttributes(attrs, R.styleable.LetterView)
        val textSize = typeArray.getDimension(R.styleable.LetterView_myLetterViewTextSize, 15f)
        mTextColor = typeArray.getColor(R.styleable.LetterView_myLetterViewTextColor, 0)
        mSelectColor = typeArray.getColor(R.styleable.LetterView_myTextSelectColor, 0)

        typeArray.recycle()
        mTextPaint = Paint()
        mTextPaint.isAntiAlias = true
        mTextPaint.textSize = textSize
        mTextPaint.color = mTextColor


        mSelectPaint = Paint()
        mSelectPaint.isAntiAlias = true
        mSelectPaint.textSize = textSize
        mSelectPaint.color = mSelectColor

    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val height = MeasureSpec.getSize(heightMeasureSpec)
        val textWidth = mTextPaint.measureText("W")

        var width = (paddingLeft + paddingRight + textWidth).toInt()
        setMeasuredDimension(width, height)
    }

    override fun onDraw(canvas: Canvas) {
//        super.onDraw(canvas)

        //先得到，每个字母的高度中心位置，然后里面得到每个字的基线，再画出来


        val itemHeight = (height - paddingTop - paddingBottom) / letters.size

        for (index in letters.indices) {
            val letterCenterY = index * itemHeight + itemHeight / 2 + paddingTop
            val frontMetrics = mTextPaint.getFontMetrics()
            val dy = (frontMetrics.bottom - frontMetrics.top) / 2 - frontMetrics.bottom
            val baseLine = letterCenterY + dy
            val textWidth = mTextPaint.measureText(letters[index])
            val x = width / 2 - textWidth / 2
            if (letters[index] == selectLetter) {
                canvas.drawText(letters[index], x, baseLine, mSelectPaint)
            } else {
                canvas.drawText(letters[index], x, baseLine, mTextPaint)
            }


        }


    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN, MotionEvent.ACTION_MOVE -> {

                isTouch=true
                val itemHeight = (height - paddingTop - paddingBottom) / letters.size
                var letterIndex = (event.y / itemHeight).toInt()

                if (letterIndex < 0) {
                    letterIndex = 0
                }
                if (letterIndex > letters.size - 1) {
                    letterIndex = letters.size - 1
                }

                if (letters[letterIndex] == selectLetter) {
                    return true
                }
                selectLetter = letters[letterIndex]
                onTouchLetterListener?.touch(selectLetter, true)
                invalidate()
            }
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                isTouch=false

                handle.postDelayed({
                    if (!isTouch){
                    selectLetter = ""
                    onTouchLetterListener?.touch(selectLetter, false)
                    invalidate()
                    }
                },1000)


            }


        }





        return true


    }

    var onTouchLetterListener: OnTouchLetterListener? = null

    interface OnTouchLetterListener {
        fun touch(letter: String, show: Boolean = false)

    }


    fun release(){
        handle.removeCallbacksAndMessages(null)
    }


}