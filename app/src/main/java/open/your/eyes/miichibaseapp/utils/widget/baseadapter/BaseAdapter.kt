package open.your.eyes.miichibaseapp.utils.widget.baseadapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import open.your.eyes.miichibaseapp.BR
import open.your.eyes.miichibaseapp.R
import open.your.eyes.miichibaseapp.utils.printLog

/**
 * Created by OpenYourEyes on 11/19/20
 */

class BaseAdapter<M>(
    context: Context,
    @LayoutRes
    private val layoutRes: Int,
    private val onItemClick: ((M) -> Unit)? = null,
) : RecyclerView.Adapter<BaseViewHolder<M>>() {

    private val layoutInflater: LayoutInflater = LayoutInflater.from(context)
    private var mList = mutableListOf<M>()
    private lateinit var recyclerView: RecyclerView


    var onItemClickWithSharedElement: ((model: M, rootView: View) -> Unit)? =
        null

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        this.recyclerView = recyclerView as LoadMoreRecyclerView
    }

    fun updateList(list: MutableList<M>) {
        (recyclerView as? LoadMoreRecyclerView)?.setLoaded()
        mList = list
        notifyDataSetChanged()
    }

    private fun getItem(position: Int): M {
        return mList[position]
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<M> {
        val view: ViewDataBinding =
            DataBindingUtil.inflate(layoutInflater, layoutRes, parent, false)

        val localItemClick: (M, View) -> Unit = { model, rootView ->
            onItemClick?.invoke(model) ?: kotlin.run {
                onItemClickWithSharedElement?.invoke(model, rootView)
            }
        }
        return BaseViewHolder(view, localItemClick)
    }

    override fun onBindViewHolder(holder: BaseViewHolder<M>, position: Int) {
        holder.bindData(getItem(position))
    }

    override fun getItemCount(): Int {
        return mList.size
    }

}

open class BaseViewHolder<M>(
    private val rootView: ViewDataBinding,
    private val onItemClick: ((M, View) -> Unit)?
) :
    RecyclerView.ViewHolder(rootView.root) {

    fun bindData(model: M) {
        rootView.root.setOnClickListener {
            onItemClick?.invoke(model, rootView.root)
        }
        rootView.setVariable(BR.model, model)
        rootView.executePendingBindings()
    }
}
