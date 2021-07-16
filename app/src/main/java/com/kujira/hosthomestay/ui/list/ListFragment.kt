package com.kujira.hosthomestay.ui.list

import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import kotlinx.android.synthetic.main.list_fragment.*
import com.kujira.hosthomestay.R
import com.kujira.hosthomestay.data.model.RepoModel
import com.kujira.hosthomestay.databinding.ListFragmentBinding
import com.kujira.hosthomestay.ui.base.BaseFragment
import com.kujira.hosthomestay.utils.printLog
import com.kujira.hosthomestay.utils.widget.baseadapter.*

/**
 * Created by OpenYourEyes on 11/2/20
 */
class ListFragment : BaseFragment<ListViewModel, ListFragmentBinding>(),
    SwipeRefreshLayout.OnRefreshListener {
    lateinit var repoAdapter: BaseAdapter<RepoModel>
    override fun createViewModel(): Class<ListViewModel> {
        return ListViewModel::class.java
    }

    override fun getResourceLayout(): Int {
        return R.layout.list_fragment
    }

    override fun initView() {
        with(dataBinding) {
            swipeRefreshLayout.setOnRefreshListener(this@ListFragment)
            recyclerView.apply {
                addListenerLoadMore(viewModel as ListViewModel)
                setHasFixedSize(true)
                layoutManager = LinearLayoutManager(requireContext())
                addItemDecoration(DividerItemDecoration(activity, LinearLayoutManager.VERTICAL))
                repoAdapter = BaseAdapter(
                    context = requireContext(),
                    layoutRes = R.layout.view_repo_list_item,
                ) {
                    replaceFragment(R.id.aboutFragment)
                }
                adapter = ConcatAdapter(repoAdapter)
            }
        }
    }


    override fun bindViewModel() {
        viewModel.listRepo.observe(viewLifecycleOwner, {
            repoAdapter.updateList(it)
            val adapter = dataBinding.recyclerView.adapter as ConcatAdapter
            adapter.checkLoading(viewModel)
        })
        if (!viewModel.listRepoHasValue()) {
            viewModel.onRefresh()
        }
    }

    override fun onRefresh() {
        dataBinding.swipeRefreshLayout.isRefreshing = false
        viewModel.onRefresh()
    }
}
