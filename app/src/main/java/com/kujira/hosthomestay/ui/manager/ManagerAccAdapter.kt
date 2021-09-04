package com.kujira.hosthomestay.ui.manager

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.databinding.library.baseAdapters.BR
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
//        holder.click(listRoom[position])
//        holder.longClick(listRoom[position])
//        holder.clickExitRoom(listRoom[position])
    }

    override fun getItemCount(): Int = listRoom.size
    inner class ManagerHolder(
        var binding: ViewDataBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun setUp(itemData: AccUser) {
            binding.executePendingBindings()
            binding.setVariable(BR.model, itemData)
            itemView.name_acc_user.text = itemData.userName
            itemView.show_email.text = itemData.mail
        }

//        fun click(addRoomModel: AddRoomModel) {
//            itemView.btn_remove_room.setOnClickListener {
//                click.click(addRoomModel)
//            }
//
//        }
//
//        fun longClick(addRoomModel: AddRoomModel) {
//            itemView.setOnLongClickListener {
//                click.longClick(addRoomModel)
//                return@setOnLongClickListener true
//            }
//        }
//
//        fun clickExitRoom(addRoomModel: AddRoomModel) {
//            itemView.tv_tra.setOnClickListener {
//                click.clickExitRoom(addRoomModel)
//            }
//        }
    }
}

interface IClick {
//    fun clickExitRoom(addRoomModel: AddRoomModel)
//    fun click(addRoomModel: AddRoomModel)
//    fun longClick(addRoomModel: AddRoomModel)
}