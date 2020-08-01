package com.example.kotlintest

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.graphics.Point
import android.os.Message
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import com.example.kotlintest.util.MathUtil
import java.util.ArrayList
import java.util.logging.Handler

class LockPatternView : View {
    private val mPointers = Array<Array<Pointer?>>(3) {
        Array<Pointer?>(3) { null }
    }


    private var mDotRadius: Int = 0
    private var isInit: Boolean = false

    // 画笔
    private var mArrowPaint: Paint? = null
    private var mLinePaint: Paint? = null
    private var mPressedPaint: Paint? = null
    private var mNormalPaint: Paint? = null
    private var mErrorPaint: Paint? = null

    // 颜色
    private val mOuterPressedColor = 0xff8cbad8.toInt()
    private val mInnerPressedColor = 0xff0596f6.toInt()
    private val mOuterNormalColor = 0xffd9d9d9.toInt()
    private val mInnerNormalColor = 0xff929292.toInt()
    private val mOuterErrorColor = 0xff901032.toInt()
    private val mInnerErrorColor = 0xffea0945.toInt()
    private var unLockState: Int = 0

    private val MIN_PWD_NUMBER = 4
    private var isIntercept = false

    var myHandler = android.os.Handler(android.os.Handler.Callback { true })

    /**
     * 获取按下的点
     * @return 当前按下的点
     */

    private val currentPointer: Pointer?
        get() {
            for (i in mPointers.indices) {
                for (j in mPointers[i].indices) {
                    val point = mPointers[i][j]
                    if (point != null) {
                        if (MathUtil.checkInRound(
                                point.centerX.toFloat(),
                                point.centerY.toFloat(),
                                mDotRadius.toFloat(),
                                mMovingX,
                                mMovingY
                            )
                        ) {
                            return point
                        }
                    }
                }
            }
            return null
        }

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {

    }

    override fun onDraw(canvas: Canvas) {
        if (!isInit) {
            initDot()
            initPaint()
            isInit = true
        }
        canvasDot(canvas)
        drawLine(canvas)
    }

    private fun drawLine(canvas: Canvas) {
        if (mSelectPoint.size >= 1) {
            var lastPointer = mSelectPoint[0]
            for (index in 1 until mSelectPoint.size) {

                drawLine(lastPointer, mSelectPoint[index], canvas, mLinePaint!!)


                drawArrow(
                    canvas,
                    mArrowPaint!!,
                    lastPointer,
                    mSelectPoint[index],
                    (mDotRadius / 4).toFloat(),
                    38
                )

                lastPointer = mSelectPoint[index]
            }
        }


    }


    /**
     * 画箭头
     */
    private fun drawArrow(
        canvas: Canvas,
        paint: Paint,
        start: Pointer,
        end: Pointer,
        arrowHeight: Float,
        angle: Int
    ) {
        val d = MathUtil.getDistance(
            start.centerX.toDouble(),
            start.centerY.toDouble(),
            end.centerX.toDouble(),
            end.centerY.toDouble()
        )
        val sin_B = ((end.centerX - start.centerX) / d).toFloat()
        val cos_B = ((end.centerY - start.centerY) / d).toFloat()
        val tan_A = Math.tan(Math.toRadians(angle.toDouble())).toFloat()
        val h = (d - arrowHeight.toDouble() - mDotRadius * 1.1).toFloat()
        val l = arrowHeight * tan_A
        val a = l * sin_B
        val b = l * cos_B
        val x0 = h * sin_B
        val y0 = h * cos_B
        val x1 = start.centerX + (h + arrowHeight) * sin_B
        val y1 = start.centerY + (h + arrowHeight) * cos_B
        val x2 = start.centerX + x0 - b
        val y2 = start.centerY.toFloat() + y0 + a
        val x3 = start.centerX.toFloat() + x0 + b
        val y3 = start.centerY + y0 - a
        val path = Path()
        path.moveTo(x1, y1)
        path.lineTo(x2, y2)
        path.lineTo(x3, y3)
        path.close()
        if (end.getStatus() == Pointer.STATUS_NORMAL) {
            paint.color = mInnerPressedColor
        } else if (end.getStatus() == Pointer.STATUS_PRESS) {
            paint.color = mInnerPressedColor
        } else {
            paint.color = mOuterErrorColor
        }
        canvas.drawPath(path, paint)
    }

    /**
     * 画线
     */
    private fun drawLine(start: Pointer, end: Pointer, canvas: Canvas, paint: Paint) {
        val d = MathUtil.getDistance(
            start.centerX.toDouble(),
            start.centerY.toDouble(),
            end.centerX.toDouble(),
            end.centerY.toDouble()
        )
        val rx = (((end.centerX - start.centerX) * mDotRadius).toDouble() / 5.0 / d).toFloat()
        val ry = (((end.centerY - start.centerY) * mDotRadius).toDouble() / 5.0 / d).toFloat()
        if (end.getStatus() == Pointer.STATUS_NORMAL) {
            paint.color = mInnerPressedColor
        } else if (end.getStatus() == Pointer.STATUS_PRESS) {
            paint.color = mInnerPressedColor
        } else {
            paint.color = mOuterErrorColor
        }

        canvas.drawLine(
            start.centerX + rx,
            start.centerY + ry,
            end.centerX - rx,
            end.centerY - ry,
            paint
        )
    }

    private fun canvasDot(canvas: Canvas) {
        for (i in 0..2) {
            for (item in mPointers[i]) {
                //绘制外圆
                if (item!!.getStatus() == Pointer.STATUS_PRESS) {
                    mPressedPaint!!.color = mOuterPressedColor
                    canvas.drawCircle(
                        item.centerX.toFloat(),
                        item.centerY.toFloat(),
                        mDotRadius.toFloat(),
                        mPressedPaint
                    )
                    //绘制内圆
                    canvas.drawCircle(
                        item.centerX.toFloat(),
                        item.centerY.toFloat(),
                        mDotRadius / 6.toFloat(),
                        mPressedPaint
                    )
                } else if (item!!.getStatus() == Pointer.STATUS_ERROR) {
                    mPressedPaint!!.color = mOuterErrorColor
                    canvas.drawCircle(
                        item.centerX.toFloat(),
                        item.centerY.toFloat(),
                        mDotRadius.toFloat(),
                        mPressedPaint
                    )
                    //绘制内圆
                    canvas.drawCircle(
                        item.centerX.toFloat(),
                        item.centerY.toFloat(),
                        mDotRadius / 6.toFloat(),
                        mPressedPaint
                    )
                } else {
                    mNormalPaint!!.color = mOuterNormalColor
                    canvas.drawCircle(
                        item.centerX.toFloat(),
                        item.centerY.toFloat(),
                        mDotRadius.toFloat(),
                        mNormalPaint
                    )
                    //绘制内圆
                    canvas.drawCircle(
                        item.centerX.toFloat(),
                        item.centerY.toFloat(),
                        mDotRadius / 6.toFloat(),
                        mNormalPaint
                    )


                }


            }
        }
    }

    private fun initDot() {
        var width = measuredWidth;
        val height = measuredHeight;

        var offsetY = 0
        var offsetX = 0
        if (width > height) {
            //横屏
            offsetX = (width - height) / 2
            width = height
        } else {
            //竖屏
            offsetY = (height - width) / 2
        }
        val squareWidth = width / 3
        mDotRadius = width / 12
        //外圆的大小。根据宽度来计算


        mPointers[0][0] = Pointer(offsetX + squareWidth / 2, offsetY + squareWidth / 2, 1)
        mPointers[0][1] = Pointer(offsetX + squareWidth * 3 / 2, offsetY + squareWidth / 2, 2)
        mPointers[0][2] = Pointer(offsetX + squareWidth * 5 / 2, offsetY + squareWidth / 2, 3)

        mPointers[1][0] = Pointer(offsetX + squareWidth / 2, offsetY + squareWidth * 3 / 2, 4)
        mPointers[1][1] = Pointer(offsetX + squareWidth * 3 / 2, offsetY + squareWidth * 3 / 2, 5)
        mPointers[1][2] = Pointer(offsetX + squareWidth * 5 / 2, offsetY + squareWidth * 3 / 2, 6)

        mPointers[2][0] = Pointer(offsetX + squareWidth / 2, offsetY + squareWidth * 5 / 2, 7)
        mPointers[2][1] = Pointer(offsetX + squareWidth * 3 / 2, offsetY + squareWidth * 5 / 2, 8)
        mPointers[2][2] = Pointer(offsetX + squareWidth * 5 / 2, offsetY + squareWidth * 5 / 2, 9)


    }


    //手指触摸的位置
    private var mMovingX = 0f
    private var mMovingY = 0f
    private val mSelectPoint = ArrayList<Pointer>()
    private var isTouch = false
    override fun onTouchEvent(event: MotionEvent): Boolean {
        mMovingX = event.x;
        mMovingY = event.y;
        if (isIntercept) {
            return true
        }


        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                isTouch = true
                //判断手指是不是按在一个空格上面
                //判断 手指是不是在圆里面，点到圆心的距离
                if (currentPointer == null) {
                    //点的不是点
                } else {
                    currentPointer!!.setStatus(Pointer.STATUS_PRESS)
                    mSelectPoint.add(currentPointer!!)
                }


            }
            MotionEvent.ACTION_MOVE -> {
                isTouch = true
                if (currentPointer == null) {
                    //点的不是点
                } else {
                    if (!mSelectPoint.contains(currentPointer!!)) {
                        currentPointer!!.setStatus(Pointer.STATUS_PRESS)
                        mSelectPoint.add(currentPointer!!)
                    }
                }

            }
            MotionEvent.ACTION_UP -> {
                isIntercept = true
                isTouch = false
                var pwd = ""
                for (item in mSelectPoint) {
                    pwd += item.index
                    item.setStatus(Pointer.STATUS_NORMAL)
                }
                if (mSelectPoint.size < MIN_PWD_NUMBER) {
                    setStatus(Pointer.STATUS_ERROR)
                    return true
                } else {
                    unLockListener?.unLock(pwd)
                }


            }

        }
        invalidate()
        return true
    }

    private fun initPaint() {
        mLinePaint = Paint()
        mLinePaint!!.color = mInnerPressedColor
        mLinePaint!!.style = Paint.Style.STROKE
        mLinePaint!!.isAntiAlias = true
        mLinePaint!!.strokeWidth = (mDotRadius / 9).toFloat()
        // 按下的画笔
        mPressedPaint = Paint()

        mPressedPaint!!.style = Paint.Style.STROKE
        mPressedPaint!!.isAntiAlias = true
        mPressedPaint!!.strokeWidth = (mDotRadius / 6).toFloat()
        // 错误的画笔
        mErrorPaint = Paint()
        mErrorPaint!!.style = Paint.Style.STROKE
        mErrorPaint!!.isAntiAlias = true
        mErrorPaint!!.strokeWidth = (mDotRadius / 6).toFloat()
        // 默认的画笔
        mNormalPaint = Paint()
        mNormalPaint!!.style = Paint.Style.STROKE
        mNormalPaint!!.isAntiAlias = true
        mNormalPaint!!.strokeWidth = (mDotRadius / 9).toFloat()
        // 箭头的画笔
        mArrowPaint = Paint()
        mArrowPaint!!.color = mInnerPressedColor
        mArrowPaint!!.style = Paint.Style.FILL
        mArrowPaint!!.isAntiAlias = true


    }

    class Pointer(val centerX: Int, val centerY: Int, val index: Int) {
        companion object {
            @JvmField
            val STATUS_NORMAL = 0

            @JvmField
            val STATUS_PRESS = 1

            @JvmField
            val STATUS_ERROR = 2
        }

        private var status = STATUS_NORMAL

        public fun setStatus(status: Int) {
            this.status = status
        }

        public fun getStatus(): Int = status

    }

    var unLockListener: UnLockListener? = null


    interface UnLockListener {
        fun unLock(pwd: String)
    }

    fun setStatus(status: Int) {
        unLockState = status
        for (j in mSelectPoint.indices) {
            mSelectPoint[j].setStatus(status)
        }
        handler.postDelayed({
            mSelectPoint.clear()
            unLockState = Pointer.STATUS_NORMAL
            for (i in 0..2) {
                for (j in mPointers.indices) {
                    mPointers[i][j]?.setStatus(unLockState)
                }
            }

            invalidate()
            isIntercept = false
        }, 1000)
        invalidate()
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        myHandler.removeCallbacksAndMessages(null)
    }

}