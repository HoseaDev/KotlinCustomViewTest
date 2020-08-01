package com.example.kotlintest

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color.RED
import android.graphics.Paint
import android.graphics.Path
import android.graphics.PointF
import android.hardware.camera2.params.RggbChannelVector.RED
import android.util.AttributeSet
import android.util.Log
import android.util.TypedValue
import android.view.MotionEvent
import android.view.View

class MessageBubbleView : View {
    private lateinit var mFixationPoint: PointF
    private lateinit var mDragPoint: PointF
    private var mDragRadius: Float = 0f
    private var mFixationRadius: Float = 0f
    private var mFixationRadiusMax: Float = 0f
    private var mFixationRadiusMin: Float = 0f
    private var mPointPaint: Paint

    constructor(context: Context?) : this(context, null)
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        mDragRadius = dip2Px(20f)
//        mFixationRadius = dip2Px(20f)
        mFixationRadiusMax = dip2Px(20f)
        mFixationRadiusMin = dip2Px(10f)
        mPointPaint = Paint()
        mPointPaint.color = resources.getColor(R.color.red)
        mPointPaint.isAntiAlias = true
    }

    private fun dip2Px(dip: Float): Float {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dip,
            resources.displayMetrics
        )
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        Log.i("hltag", "event" + event.action)
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                //指定当前的位置。
                val downY = event.y;
                val downX = event.x;
                initPoint(downY, downX)
            }
            MotionEvent.ACTION_MOVE -> {
                //指定当前的位置。
                val moveY = event.y;
                val moveX = event.x;
                updateDragPoint(moveY, moveX)
            }
            MotionEvent.ACTION_UP -> {

            }
        }
        invalidate()
        return true
    }

    private fun updateDragPoint(moveY: Float, moveX: Float) {
        mDragPoint.x = moveX
        mDragPoint.y = moveY
    }


    /**
     * 初始化位置
     */
    private fun initPoint(downY: Float, downX: Float) {
        mFixationPoint = PointF(downX, downY)
        mDragPoint = PointF(downX, downY)
    }

    override fun onDraw(canvas: Canvas) {

        if (!::mDragPoint.isInitialized || !::mFixationPoint.isInitialized) {
            Log.i("hltag", "ondraw:" + !::mDragPoint.isInitialized)
            Log.i("hltag", "mFixationPoint:" + !::mFixationPoint.isInitialized)
            return
        }

        //画拖拽圆
        canvas.drawCircle(mDragPoint.x, mDragPoint.y, mDragRadius, mPointPaint)
        //画固定圆,有一个初始化的大小，距离越远越小。小到一定距离就消失了
        val distance = getDistance(mDragPoint, mFixationPoint)
        mFixationRadius = (mFixationRadiusMax - distance / 14).toFloat()
        Log.i("hltag", "distance/14 = ${distance / 14}")
        Log.i("hltag", "mFixationRadiusMax = ${mFixationRadiusMax}")
        Log.i("hltag", "mFixationRadiusMin = ${mFixationRadiusMin}")
        Log.i("hltag", "mFixationRadius = ${mFixationRadius}")

        val beizeierPath = getBezeierPath()
        beizeierPath?.run {
            canvas.drawCircle(
                mFixationPoint.x,
                mFixationPoint.y,
                mFixationRadius,
                mPointPaint
            )
            canvas.drawPath(this, mPointPaint)
        }


    }

    private fun getBezeierPath(): Path? {
        if (mFixationRadius < mFixationRadiusMin) {
            return null
        }
        val dy = (mDragPoint.y - mFixationPoint.y)
        val dx = (mDragPoint.x - mFixationPoint.x)
        val tanA = dy / dx
        //角a的角度
        val arcTanA = Math.atan(tanA.toDouble())


        //p0
        val p0x = mFixationPoint.x + mFixationRadius * Math.sin(arcTanA).toFloat()
        val p0y = mFixationPoint.y - mFixationRadius * Math.cos(arcTanA) .toFloat()

        //p1
        val p1x = mDragPoint.x + mDragRadius * Math.sin(arcTanA) .toFloat()
        val p1y = mDragPoint.y - mDragRadius * Math.cos(arcTanA) .toFloat()


        //p2
        val p2x = mDragPoint.x - mDragRadius * Math.sin(arcTanA) .toFloat()
        val p2y = mDragPoint.y + mDragRadius * Math.cos(arcTanA) .toFloat()
        //p3
        val p3x = mFixationPoint.x - mFixationRadius * Math.sin(arcTanA) .toFloat()
        val p3y = mFixationPoint.y + mFixationRadius * Math.cos(arcTanA) .toFloat()

        //拼装贝塞尔路径
        val bezeierPath = Path()
        bezeierPath.moveTo(p0x, p0y)
        //画中心点 控制点
        val controlPoint = getControlPoint()

        bezeierPath.quadTo(controlPoint.x, controlPoint.y, p1x, p1y)


        bezeierPath.lineTo(p2x, p2y)

        bezeierPath.quadTo(controlPoint.x, controlPoint.y, p3x, p3y)
        bezeierPath.close()

        return bezeierPath
    }

    private fun getControlPoint(): PointF {
        return PointF((mDragPoint.x + mFixationPoint.x) / 2, (mDragPoint.y + mFixationPoint.y) / 2)

    }

    private fun getDistance(point1: PointF, point2: PointF): Double {
        return Math.sqrt((point1.x - point2.x) * (point1.x - point2.x) + (point1.y - point2.y) * (point1.y - point2.y).toDouble())
    }
}