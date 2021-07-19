package com.kujira.hosthomestay.ui.manager

import androidx.recyclerview.widget.LinearLayoutManager
import com.kujira.hosthomestay.R
import com.kujira.hosthomestay.databinding.FragmentMessageBinding
import com.kujira.hosthomestay.ui.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_manager_room.*


class ManagerRoomFragment : BaseFragment<ManagerRoomViewModel, FragmentMessageBinding>() {
    override fun createViewModel(): Class<ManagerRoomViewModel> {
        return ManagerRoomViewModel::class.java
    }

    override fun getResourceLayout(): Int = R.layout.fragment_manager_room

    override fun initView() {
        rcy_manager.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = ManagerRoomAdapter(mutableListOf())
        }

    }

    override fun bindViewModel() {
        viewModel.managerListRoom()
        viewModel.listRoomLiveData.observe(this, {
            (rcy_manager.adapter as ManagerRoomAdapter).setList(it)
        })
    }
}