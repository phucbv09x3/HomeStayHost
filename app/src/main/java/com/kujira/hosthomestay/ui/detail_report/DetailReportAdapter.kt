package com.kujira.hosthomestay.ui.detail_report

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.kujira.hosthomestay.databinding.ItemReportBinding
import kotlinx.android.synthetic.main.item_report.view.*

class DetailReportAdapter(var listReport: MutableList<ReportModel>) :
    RecyclerView.Adapter<DetailReportAdapter.DetailReportHolder>() {

    fun setList(mutableList: MutableList<ReportModel>) {
        listReport = mutableList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DetailReportAdapter.DetailReportHolder {
        val inflate = LayoutInflater.from(parent.context)
        val dataBinding = ItemReportBinding.inflate(inflate, parent, false)
        return DetailReportHolder(dataBinding)
    }

    override fun onBindViewHolder(holder: DetailReportAdapter.DetailReportHolder, position: Int) {
        holder.setUp(listReport[position])
    }

    override fun getItemCount(): Int = listReport.size

    inner class DetailReportHolder(
        private val binding: ViewDataBinding
    ) : RecyclerView.ViewHolder(
        binding.root
    ) {
        fun setUp(itemData: ReportModel) {
            itemView.tv_IdHost.text = itemData.idHost
            itemView.tv_detail_comment.text = itemData.contentReport
        }

    }
}


data class ReportModel(
    val idHost: String,
    val contentReport: String
)