package com.example.kotlintest

import android.content.Context
import android.content.res.Resources
import android.util.AttributeSet
import android.util.DisplayMetrics
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.HorizontalScrollView
import androidx.core.view.GestureDetectorCompat
import androidx.core.view.ViewCompat

class SlidingMenu : HorizontalScrollView {
    private var mMenuWidth: Int = 0;
    private lateinit var menuLayout: View
    private lateinit var homeLayout: View
    private lateinit var mGestureDetector: GestureDetectorCompat;
    private var TAG = "hltag"
    val MIN_DISTANCE = 100
    val VELOCITY_X = 2000
    private var drawerState: Boolean = false

    constructor(context: Context?) : this(context, null)
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        val typedArray = context!!.obtainStyledAttributes(attrs, R.styleable.SlidingMenu);
        val marginWidth =
            typedArray.getDimension(R.styleable.SlidingMenu_marginRightWidth, dp2px(context, 50f))
        mMenuWidth = (getScreenWidth(context) - marginWidth).toInt();
        typedArray.recycle()
        mGestureDetector =
            GestureDetectorCompat(context, object : GestureDetector.OnGestureListener {
                override fun onShowPress(e: MotionEvent?) {
                    Log.i(TAG, "onShowPress" + e?.action)

                }

                override fun onSingleTapUp(e: MotionEvent?): Boolean {
                    Log.i(TAG, "onSingleTapUp" + e?.action)
                    return false
                }

                override fun onDown(e: MotionEvent?): Boolean {

                    Log.i(TAG, "onDown" + e?.action)
                    return false
                }

                override fun onFling(
                    e1: MotionEvent,
                    e2: MotionEvent,
                    velocityX: Float,
                    velocityY: Float
                ): Boolean {
                    Log.i(
                        TAG,
                        "onFling e1 = ${e1?.action}   e2= ${e2?.action}  velocityX=$velocityX  velocityY = $velocityY"
                    )
                    Log.i(
                        TAG,
                        "onFling e1 = ${e1?.x}   e2= ${e2?.x}  velocityX=$velocityX  velocityY = $velocityY"
                    )
                    if (e1.x - e2.x > MIN_DISTANCE && Math.abs(velocityX) > VELOCITY_X) {
                        Log.i(
                            TAG,
                            "左滑"
                        )
                        closeMenu()
                        return true
                    } else if (e2.x - e1.x > MIN_DISTANCE && Math.abs(velocityX) > VELOCITY_X) {
                        Log.i(
                            TAG,
                            "右滑"
                        )
                        openMenu()
                        return true
                    }


                    return false
                }

                override fun onScroll(
                    e1: MotionEvent?,
                    e2: MotionEvent?,
                    distanceX: Float,
                    distanceY: Float
                ): Boolean {

                    Log.i(
                        TAG,
                        "onScroll e1 = ${e1?.action}   e2= ${e2?.action}  distanceX=$distanceX  distanceY = $distanceY"
                    )
                    return false
                }

                override fun onLongPress(e: MotionEvent?) {
                    Log.i(TAG, "onLongPress" + e?.action)
                }
            })
    }

    override fun onFinishInflate() {
        Log.i("hltag", "onFinishInflate")
        super.onFinishInflate()
        val container = getChildAt(0) as ViewGroup

        menuLayout = container.getChildAt(0)
        homeLayout = container.getChildAt(1)
        val menuLayoutParams = menuLayout.layoutParams;

        menuLayoutParams.width = mMenuWidth.toInt()
        menuLayout.layoutParams = menuLayoutParams
        val homeLayoutParams = homeLayout.layoutParams;
        homeLayoutParams.width = getScreenWidth(context)
        homeLayout.layoutParams = homeLayoutParams;


        //初始化进来是关闭的
//        scrollTo(mMenuWidth,0)

    }

    override fun onTouchEvent(ev: MotionEvent): Boolean {
        if (mGestureDetector?.onTouchEvent(ev)) {
            return true
        }

//        Log.i("hltag", "onTouchEvent   ${ev.action }     $scrollX         ${mMenuWidth / 2}")
        when (ev.action) {
            MotionEvent.ACTION_UP -> {
                //只需要管手指抬起，根据 我们当前滚动的距离来判断。
                val currentScrollX = scrollX;

                if (currentScrollX > mMenuWidth / 2) {
                    closeMenu();
                } else {
                    openMenu()
                }
                return true
            }
        }


        return super.onTouchEvent(ev)
    }

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        if (drawerState) {
            ev.x
        } else {

        }

        return super.onInterceptTouchEvent(ev)
    }

    override fun onScrollChanged(l: Int, t: Int, oldl: Int, oldt: Int) {
        super.onScrollChanged(l, t, oldl, oldt)
//        Log.i("hltag", "left:$l top:$t    oldl${oldl}   oldt${oldt}")
        var scale = 1f * l / mMenuWidth
        val rightScale = 0.7f + 0.3f * scale


        //右边缩放
        homeLayout.pivotX = 0f
        homeLayout.pivotY = (measuredHeight / 2).toFloat()
        homeLayout.scaleX = rightScale
        homeLayout.scaleY = rightScale


        //左边缩放
        val leftScale = 0.7f + 0.3f * (1 - scale)
        menuLayout.pivotX = measuredWidth.toFloat()
        menuLayout.pivotY = (measuredHeight / 2).toFloat()
        menuLayout.scaleX = leftScale
        menuLayout.scaleY = leftScale
        //左边alpha
        val leftAlpha = 0.5f + 0.5f * (1 - scale)
        menuLayout.alpha = leftAlpha

        //左边平移
        menuLayout.setTranslationX(0.2f * l)

    }


    private fun openMenu() {
        smoothScrollTo(0, 0)
        drawerState = true
    }

    private fun closeMenu() {
        smoothScrollTo(mMenuWidth, 0)
        drawerState = false
    }


    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        super.onLayout(changed, l, t, r, b)
        scrollTo(mMenuWidth, 0)
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

    private fun getScreenWidth(context: Context): Int {
        val resources: Resources = this.resources
        val dm: DisplayMetrics = resources.getDisplayMetrics()

        return dm.widthPixels

    }

}