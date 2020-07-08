package com.example.kotlintest

import android.os.Bundle
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.get
import androidx.core.view.size
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import kotlinx.android.synthetic.main.activity_viewpager.*

class ViewPagerAct : AppCompatActivity() {
    private val titles = arrayListOf<String>("我们的爱", "你", "哈哈", "呵可呵")
    private val fragments = arrayListOf<Fragment>(
        TestFragment(),
        TestFragment(),
        TestFragment(),
        TestFragment()

    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_viewpager)

        initView()
        initTitle()
    }

    private fun initView() {
        val pagerAdapter = MyPagerAdapter(titles, supportFragmentManager, fragments);
        viewPager.adapter = pagerAdapter
        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {

            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                val left = layoutContainer.get(position) as TrackTextView
                left.setDirection(TrackTextView.Direction.RIGHT_TO_LEFT)
                left.setCurrentProgress(1 - positionOffset)


                if (position+1 == layoutContainer.size) {
                    return
                }
                val right = layoutContainer.get(position+1) as TrackTextView
                right.setDirection(TrackTextView.Direction.LEFT_TO_RIGHT)
                right.setCurrentProgress(positionOffset)
            }

            override fun onPageSelected(position: Int) {

            }
        })
    }

    private fun initTitle() {
        for (i in titles.indices) {
            val params = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            params.weight = 1f
            val tv = TrackTextView(this)
            tv.layoutParams = params
            tv.text = titles[i]
            layoutContainer.addView(tv)
        }
    }
}