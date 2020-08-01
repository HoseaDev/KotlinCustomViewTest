package com.example.kotlintest.recyclerView

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class WrapRecyclerView :RecyclerView{


    // 支持添加头部和底部的 RecyclerView.Adapter
    private var mWrapAdapter: WrapRecyclerAdapter? = null

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )


    override fun setAdapter(adapter: Adapter<*>?) {
        mWrapAdapter = WrapRecyclerAdapter(adapter as Adapter<ViewHolder>)
        super.setAdapter(mWrapAdapter)
    }

    /**
     * 添加头部View
     * @param view
     */
    fun addHeaderView(view: View) {
        if (mWrapAdapter != null) {
            mWrapAdapter!!.addHeaderView(view)
        }
    }

    /**
     * 添加底部View
     * @param view
     */
    fun addFooterView(view: View) {
        if (mWrapAdapter != null) {
            mWrapAdapter!!.addFooterView(view)
        }
    }

    /**
     * 移除头部View
     * @param view
     */
    fun removeHeaderView(view: View) {
        if (mWrapAdapter != null) {
            mWrapAdapter!!.removeHeaderView(view)
        }
    }

    /**
     * 移除底部View
     * @param view
     */
    fun removeFooterView(view: View) {
        if (mWrapAdapter != null) {
            mWrapAdapter!!.removeFooterView(view)
        }
    }
}