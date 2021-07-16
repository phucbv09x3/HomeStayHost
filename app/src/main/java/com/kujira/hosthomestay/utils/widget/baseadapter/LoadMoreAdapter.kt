package com.kujira.hosthomestay.utils.widget.baseadapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kujira.hosthomestay.R
import com.kujira.hosthomestay.data.model.RepoModel
import com.kujira.hosthomestay.utils.printLog

/**
 * Created by OpenYourEyes on 11/19/20
 */

class LoadMoreAdapter<M>(
    context: Context,
    @LayoutRes
    private val layoutRes: Int,
    private val pagingDelegate: PagingDelegate,
    private val onItemClick: ((M) -> Unit)? = null,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val visibleThresholds = 5
    private val layoutInflater: LayoutInflater = LayoutInflater.from(context)
    private var mList = mutableListOf<M>()
    private val isLoadMore: Boolean
        get() {
            return pagingDelegate.triggerPaging.value != PagingType.None
        }

    private lateinit var recyclerView: RecyclerView

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        this.recyclerView = recyclerView
    }

    fun updateList(list: MutableList<M>) {
        pagingDelegate.triggerPaging.onNext(PagingType.None)
        mList = list
        notifyDataSetChanged()
    }

    private fun getItem(position: Int): M {
        return mList[position]
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == R.layout.item_load_more) {
            val loadMoreView = layoutInflater.inflate(R.layout.item_load_more, parent, false)
            return LoadMoreVH(loadMoreView)
        }
        val view: ViewDataBinding =
            DataBindingUtil.inflate(layoutInflater, layoutRes, parent, false)

        val localItemClick: (M, View) -> Unit = { model, rootView ->
            onItemClick?.invoke(model)
        }
        return BaseViewHolder(view, localItemClick)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        printLog("onBindViewHolder $position")
        (holder as? BaseViewHolder<M>)?.bindData(getItem(position))
        if (!isLoadMore && !pagingDelegate.isEndOf && isEndOfItem(position)) {
            pagingDelegate.triggerPaging.onNext(PagingType.LoadMore)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (isLoadMore && position == itemCount - 1) {
            printLog("getItemViewType isLoadMore $position")
            R.layout.item_load_more
        } else {
            layoutRes
        }
    }

    override fun getItemCount(): Int {
        if (mList.size == 0) return 0
        return mList.size + if (isLoadMore) 1 else 0
    }

    private fun isEndOfItem(position: Int): Boolean {
        return position + visibleThresholds == itemCount - 1
    }

}


class LoadMoreVH(itemView: View) : RecyclerView.ViewHolder(itemView) {}
