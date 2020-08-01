package com.example.kotlintest

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Color
import android.opengl.Visibility
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.animation.addListener
import java.lang.RuntimeException

class ScreenList : LinearLayout {
    private val ANIMATION_DURATION: Long = 1000

    private lateinit var mContext: Context
    private lateinit var mAdapter: BaseScreenAdapter
    private lateinit var mTableLayout: LinearLayout
    private lateinit var mMenuLayout: FrameLayout
    private lateinit var mContainerLayout: FrameLayout
    private lateinit var mShadowView: ShadowView
    private val shadowColor: String = "#88888888"
    private var currentShowPosition = -1
    private var isAnimation = false
    private var isOpen = false

    constructor(context: Context?) : this(context, null)
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        mContext = context!!
        orientation = VERTICAL
        initLayout()
    }

    private fun initLayout() {
        //tableLayout

        mTableLayout = LinearLayout(mContext)
        val tableLayoutParams = LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        mTableLayout.layoutParams = tableLayoutParams
        //加入tableLayout
        addView(mTableLayout)

        //创建下面的MenuLayout
        mMenuLayout = FrameLayout(context)
        val menuLayoutParams = LayoutParams(
            LayoutParams.MATCH_PARENT,
            LayoutParams.MATCH_PARENT
        )
        mMenuLayout.layoutParams = menuLayoutParams

        mShadowView = ShadowView(context)
        mShadowView.name = "shadowView"
        mShadowView.visibility = View.GONE
        mShadowView.alpha = 0f
        mShadowView.isClickable = true
        mShadowView.setBackgroundColor(Color.parseColor(shadowColor))
        //加入阴影
        mMenuLayout.addView(mShadowView)
        mContainerLayout = FrameLayout(context)
        val mContainerLayoutParams = FrameLayout.LayoutParams(
            FrameLayout.LayoutParams.MATCH_PARENT,
            FrameLayout.LayoutParams.MATCH_PARENT
        )
        mContainerLayout.setBackgroundColor(Color.parseColor("#ff0000"))
        mContainerLayout.layoutParams = mContainerLayoutParams
        mMenuLayout.addView(mContainerLayout)
        //加入menuLayout
        addView(mMenuLayout)

    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        Log.i("hltag", "onMeasure")
        val height = measuredHeight
        val menuHeight = height * 0.75f
        val layoutParams = mContainerLayout.layoutParams
        layoutParams.height = menuHeight.toInt()
        mContainerLayout.layoutParams = layoutParams
    }

    fun setAdapter(adapter: BaseScreenAdapter?) {
        if (adapter == null) {
            throw RuntimeException("adapter cant not null")
            return
        }
        this.mAdapter = adapter;
        val count = mAdapter.getCount();
        for (index in 0 until count) {
            val tableView = mAdapter.getTabLayout(index, mTableLayout)
            mTableLayout.addView(tableView)
            val layoutParams = tableView.layoutParams as LinearLayout.LayoutParams
            layoutParams.weight = 1f
            tableView.layoutParams = layoutParams
            tableView.setOnClickListener {
                openMenu(index, tableView)
            }
            val menuLayout = mAdapter.getMenuLayout(index, mContainerLayout)
            mContainerLayout.addView(menuLayout)
            menuLayout.visibility = View.GONE
        }

        mShadowView.setOnClickListener {
//            Toast.makeText(context, "阴影", Toast.LENGTH_SHORT).show()
            closeMenu()
        }


    }

    private fun openMenu(index: Int, tableView: View) {
        Log.i("hltag", "openMenu  = ${isAnimation}")
        if (isAnimation) {
            return
        }

        if (isOpen) {
            if (index == currentShowPosition) {
                closeMenu()
            } else {
                //切换
                mContainerLayout.getChildAt(currentShowPosition).visibility = View.GONE
                mContainerLayout.getChildAt(index).visibility = View.VISIBLE
            }
            return
        }

        val translationY =
            ObjectAnimator.ofFloat(
                mContainerLayout,
                "translationY",
                -mContainerLayout.height.toFloat(),
                0f
            )

        val alpha =
            ObjectAnimator.ofFloat(mShadowView, "alpha", 0f, 1f)
        mContainerLayout.getChildAt(index).visibility = View.VISIBLE
        mShadowView.visibility = View.VISIBLE
//        translationY.duration = ANIMATION_DURATION
//        alpha.duration = ANIMATION_DURATION
//        alpha.start()
//        translationY.start()

        val animatorSet = AnimatorSet()
        animatorSet.duration = ANIMATION_DURATION
        animatorSet.playTogether(translationY, alpha)
        animatorSet.addListener(object : Animator.AnimatorListener {
            override fun onAnimationRepeat(animation: Animator?) {
                TODO("Not yet implemented")
            }

            override fun onAnimationEnd(animation: Animator?) {
                currentShowPosition = index
                isAnimation = false
                isOpen = true
                Log.i("hltag", "openMenu onAnimationEnd = ${isAnimation}")
            }

            override fun onAnimationCancel(animation: Animator?) {
                TODO("Not yet implemented")
            }

            override fun onAnimationStart(animation: Animator?) {
                isAnimation = true;

                Log.i("hltag", " openMenu onAnimationStart = ${isAnimation}")
            }

        })
        animatorSet.start()


    }

    private fun closeMenu() {
        Log.i("hltag", "closeMenu  = ${isAnimation}")
        if (isAnimation || currentShowPosition == -1) {
            return
        }
        val translationY =
            ObjectAnimator.ofFloat(
                mContainerLayout,
                "translationY",
                0f,
                -mContainerLayout.height.toFloat()
            )

        val alpha =
            ObjectAnimator.ofFloat(mShadowView, "alpha", 1f, 0f)
        mContainerLayout.getChildAt(currentShowPosition).visibility = View.VISIBLE
        mShadowView.visibility = View.VISIBLE
        val animatorSet = AnimatorSet()
        animatorSet.duration = ANIMATION_DURATION
        animatorSet.playTogether(translationY, alpha)
        animatorSet.addListener(object : Animator.AnimatorListener {
            override fun onAnimationRepeat(animation: Animator?) {
                TODO("Not yet implemented")
            }

            override fun onAnimationEnd(animation: Animator?) {
                currentShowPosition = -1
                isAnimation = false
                isOpen = false
                Log.i("hltag", " closeMenu onAnimationEnd = ${isAnimation}")
            }

            override fun onAnimationCancel(animation: Animator?) {
                TODO("Not yet implemented")
            }

            override fun onAnimationStart(animation: Animator?) {
                isAnimation = true;
                Log.i("hltag", " closeMenu onAnimationStart = ${isAnimation}")
            }

        })

        animatorSet.start()


    }

}