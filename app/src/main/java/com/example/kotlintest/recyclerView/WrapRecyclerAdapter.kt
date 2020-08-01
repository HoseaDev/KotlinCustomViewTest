package com.example.kotlintest.recyclerView

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder


/**
 * description: 可以添加头部底部的 WrapRecyclerAdapter
 * author: Darren on 2017/9/25 09:54
 * email: 240336124@qq.com
 * version: 1.0
 */
class WrapRecyclerAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private var mRealAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>
    lateinit var mHeaderViews // 头部
            : ArrayList<View>
    lateinit var mFooterViews // 底部
            : ArrayList<View>

    constructor (adapter: RecyclerView.Adapter<RecyclerView.ViewHolder>) {
        mRealAdapter = adapter
        mHeaderViews = ArrayList()
        mFooterViews = ArrayList()

    }


    override fun onCreateViewHolder(parent: ViewGroup, position: Int): RecyclerView.ViewHolder {

        // Header (negative positions will throw an IndexOutOfBoundsException)

        // Header (negative positions will throw an IndexOutOfBoundsException)
        val numHeaders: Int = getHeadersCount()

        if (position < numHeaders) {
            return createFooterHeaderViewHolder(mHeaderViews[position])
        }

        // Adapter

        // Adapter
        val adjPosition: Int = position - numHeaders
        var adapterCount = 0
        if (mRealAdapter != null) {
            adapterCount = mRealAdapter.itemCount
            if (adjPosition < adapterCount) {
                return mRealAdapter.onCreateViewHolder(
                    parent,
                    mRealAdapter.getItemViewType(adjPosition)
                )
            }
        }

        // Footer (off-limits positions will throw an IndexOutOfBoundsException)

        // Footer (off-limits positions will throw an IndexOutOfBoundsException)
        return createFooterHeaderViewHolder(mFooterViews[adjPosition - adapterCount])
    }

    private fun createFooterHeaderViewHolder(view: View): ViewHolder {
        return object : ViewHolder(view) {}
    }


    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        val numHeaders = getHeadersCount()
        if (position < numHeaders) {
            return
        }
        // Adapter
        // Adapter
        val adjPosition = position - numHeaders
        if (mRealAdapter != null) {
            val adapterCount = mRealAdapter.itemCount
            if (adjPosition < adapterCount) {
                mRealAdapter.onBindViewHolder(holder, position)
            }
        }
    }

    fun getHeadersCount(): Int {
        return mHeaderViews.size
    }

    /**
     * 添加底部View
     * @param view
     */
    fun addFooterView(view: View) {
        if (!mFooterViews.contains(view)) {
            mFooterViews.add(view)
            notifyDataSetChanged()
        }
    }

    /**
     * 添加头部View
     * @param view
     */
    fun addHeaderView(view: View) {
        if (!mHeaderViews.contains(view)) {
            mHeaderViews.add(view)
            notifyDataSetChanged()
        }
    }

    /**
     * 移除底部View
     * @param view
     */
    fun removeFooterView(view: View) {
        if (mFooterViews.contains(view)) {
            mFooterViews.remove(view)
            notifyDataSetChanged()
        }
    }

    /**
     * 移除头部View
     * @param view
     */
    fun removeHeaderView(view: View) {
        if (mHeaderViews.contains(view)) {
            mHeaderViews.remove(view)
            notifyDataSetChanged()
        }
    }

    override fun getItemCount(): Int {
        return mRealAdapter.itemCount + mHeaderViews.size + mFooterViews.size
    }

}