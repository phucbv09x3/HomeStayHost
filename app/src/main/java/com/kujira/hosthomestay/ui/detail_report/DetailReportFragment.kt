package com.kujira.hosthomestay.ui.detail_report

import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.kujira.hosthomestay.R
import com.kujira.hosthomestay.data.model.response.AccUser
import com.kujira.hosthomestay.databinding.FragmentDetailReportBinding
import com.kujira.hosthomestay.ui.base.BaseFragment

class DetailReportFragment : BaseFragment<DetailReportViewModel, FragmentDetailReportBinding>() {
    private var uid = ""
    private var permission = ""
    override fun createViewModel(): Class<DetailReportViewModel> {
        return DetailReportViewModel::class.java
    }

    override fun getResourceLayout(): Int {
        return R.layout.fragment_detail_report
    }

    override fun initView() {
        val bundle = arguments
        val data = bundle?.getParcelable<AccUser>("bundle")
        data?.let {
            dataBinding.tvShowName.text = it.userName
            uid = it.uid
            viewModel.uidClient = uid
            permission = it.permission
            viewModel.getListReport(it.uid)
        }
        dataBinding.rcyReport.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = DetailReportAdapter(mutableListOf())
        }
        dataBinding.resetReport.setOnClickListener {
            if (permission == "1"){
                viewModel.resetReportAccHost(uid)
            }
            if (permission == "2"){
                viewModel.resetReportAccClient(uid)
            }

        }
        dataBinding.backOnReport.setOnClickListener {
            navigators.navigateUp()
        }
    }

    override fun bindViewModel() {
        viewModel.listAccLiveData.observe(this,{
            (dataBinding.rcyReport.adapter as DetailReportAdapter).setList(it)
        })

    }
}