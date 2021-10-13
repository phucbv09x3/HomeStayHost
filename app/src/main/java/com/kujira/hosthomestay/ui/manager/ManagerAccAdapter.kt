package com.kujira.hosthomestay.ui.manager

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.kujira.hosthomestay.data.model.response.AccUser
import com.kujira.hosthomestay.databinding.ItemManagerAccBinding
import kotlinx.android.synthetic.main.item_manager_acc.view.*

class ManagerRoomAdapter(var listRoom: MutableList<AccUser>, var click: IClick) :
    RecyclerView.Adapter<ManagerRoomAdapter.ManagerHolder>() {
    fun setList(mutableList: MutableList<AccUser>) {
        listRoom = mutableList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ManagerRoomAdapter.ManagerHolder {
        val inflate = LayoutInflater.from(parent.context)
        val dataBinding = ItemManagerAccBinding.inflate(inflate, parent, false)
        return ManagerHolder(dataBinding)
    }

    override fun onBindViewHolder(holder: ManagerRoomAdapter.ManagerHolder, position: Int) {
        holder.setUp(listRoom[position])
        holder.clickShow(listRoom[position])
        holder.clickBlockAcc(listRoom[position])
    }

    override fun getItemCount(): Int = listRoom.size
    inner class ManagerHolder(
        var binding: ViewDataBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun setUp(itemData: AccUser) {
            itemView.name_acc_user.text = itemData.userName
            itemView.show_email.text = itemData.mail
            itemView.show_phone.text = itemData.phone
        }

        fun clickShow(accUser: AccUser) {
            itemView.setOnClickListener {
                click.clickShowReport(accUser)
            }
        }

        fun clickBlockAcc(accUser: AccUser) {
            itemView.btn_block_acc.setOnClickListener {
                click.clickBlockAcc(accUser)
            }
        }
    }
}

interface IClick {
    fun clickShowReport(accUser: AccUser)
    fun clickBlockAcc(accUser: AccUser)
}