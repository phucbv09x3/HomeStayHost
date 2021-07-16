package com.kujira.hosthomestay.utils.widget.baseadapter

import android.content.Context
import android.util.AttributeSet
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kujira.hosthomestay.utils.printLog

fun ConcatAdapter.checkLoading(pagingDelegate: PagingDelegate) {
    printLog("checkLoading ${adapters.size}")
    if (adapters.size == 1) {
        addAdapter(LoadingAdapter())
    }
    if (pagingDelegate.isEndOf && adapters.size == 2) {
        removeAdapter(adapters[1])
    }
}

/**
 * Created by OpenYourEyes on 11/20/20
 */
class LoadMoreRecyclerView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    def: Int = 0
) : RecyclerView(context, attrs, def) {
    private val visibleThresholds = 5
    lateinit var pagingDelegate: PagingDelegate

    private val pagingType: PagingType
        get() {
            return pagingDelegate.triggerPaging.value!!
        }
    val isLoading: Boolean
        get() {
            return pagingType != PagingType.None
        }

    val isEndOf: Boolean
        get() {
            return pagingDelegate.isEndOf
        }

    fun setLoaded() {
        pagingDelegate.triggerPaging.onNext(PagingType.None)
    }

    /**
     * init trigger load more
     * callback to ViewModel
     */
    fun addListenerLoadMore(triggerLoadMore: PagingDelegate) {
        triggerLoadMore.initPaging()
        this.pagingDelegate = triggerLoadMore
        addOnScrollListener(object : OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy > 0) {
                    val isBottom = isBottom(layoutManager!!)
                    if (!isLoading && !isEndOf && isBottom) {
                        printLog("triggerLoadMore")
                        triggerLoadMore.triggerPaging.onNext(PagingType.LoadMore)
                    }
                }
            }
        })
    }

    fun isBottom(layoutManager: LayoutManager): Boolean {
        val childCount = layoutManager.childCount
        val itemCount = layoutManager.itemCount
        var itemVisible = 0
        if (layoutManager is LinearLayoutManager) {
            itemVisible = layoutManager.findFirstVisibleItemPosition()
        }
        return (childCount + itemVisible + visibleThresholds) >= itemCount
    }

}

