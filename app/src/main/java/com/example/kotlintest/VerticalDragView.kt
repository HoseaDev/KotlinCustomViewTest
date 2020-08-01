package com.example.kotlintest

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.FrameLayout
import android.widget.ListView
import androidx.core.widget.ListViewCompat
import androidx.customview.widget.ViewDragHelper

class VerticalDragView : FrameLayout {
    private var downY: Float = 0f
    val TAG = "TAG"
    lateinit var viewDragHelper: ViewDragHelper
    lateinit var topView: View
    lateinit var bottomView: View
    var isOpen = false

    val viewDragHelperCallBack = object : ViewDragHelper.Callback() {
        override fun tryCaptureView(child: View, pointerId: Int): Boolean {
            return topView == child
        }

        override fun clampViewPositionVertical(child: View, top: Int, dy: Int): Int {
            var top = top
            if (top <= 0) {
                top = 0
                isOpen = false
            }
            if (top >= bottomView.height) {
                top = bottomView.height
                isOpen = true
            }
            return top
        }

        override fun onViewReleased(releasedChild: View, xvel: Float, yvel: Float) {
//            super.onViewReleased(releasedChild, xvel, yvel)
            Log.i(TAG, "yvel=${yvel}")
            Log.i(TAG, "(releasedChild.top=${releasedChild.top}")
            Log.i(TAG, "(bottomView.height / 2=${bottomView.height / 2}")
            if (releasedChild.top < bottomView.height / 2) {
                //缩回
                viewDragHelper.settleCapturedViewAt(0, 0)
                isOpen = false
            } else {
                //伸开
                viewDragHelper.settleCapturedViewAt(0, bottomView.height)
                isOpen = true
            }
            invalidate()
            //滚过后调用invalidate()来刷新界面 。 刷新界面 后还没有反应computeScroll()实现这个计算滚动的方法，在里面实时调用刷新界面
        }
    }

    override fun computeScroll() {
        if (viewDragHelper.continueSettling(true)) {
            invalidate()
        }
    }

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {

        viewDragHelper =
            androidx.customview.widget.ViewDragHelper.create(this, viewDragHelperCallBack)

    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        bottomView = getChildAt(0)
        topView = getChildAt(1)
//        topView.parent.requestDisallowInterceptTouchEvent(false)
    }


    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        Log.i(TAG, "onIntercept moveY${downY}  isOpen = ${isOpen}")
        if (!isOpen){
            when (ev.action) {
                MotionEvent.ACTION_DOWN -> {
                    downY = ev.y
                    viewDragHelper.processTouchEvent(ev);
                }
                MotionEvent.ACTION_MOVE -> {

                    if (ev.y - downY > 0&& !canChildScrollUp()) {
                        //用户在下拉。这个时候要拦截
                        return true
                    }

                }
            }

        }else{
            return true
        }

        return super.onInterceptTouchEvent(ev)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
//        ViewDragHelper.
        viewDragHelper.processTouchEvent(event);
        return true
    }

    fun canChildScrollUp(): Boolean {
        return if (topView is ListView) {
            ListViewCompat.canScrollList((topView as ListView?)!!, -1)
        } else topView.canScrollVertically(-1)
    }

}