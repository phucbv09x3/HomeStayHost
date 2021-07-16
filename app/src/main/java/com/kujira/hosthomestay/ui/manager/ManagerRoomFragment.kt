package com.kujira.hosthomestay.ui.manager

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kujira.hosthomestay.R
import com.kujira.hosthomestay.databinding.FragmentMessageBinding
import com.kujira.hosthomestay.ui.base.BaseFragment


class ManagerRoomFragment : BaseFragment<ManagerRoomViewModel,FragmentMessageBinding>() {
    override fun createViewModel(): Class<ManagerRoomViewModel> {
        return ManagerRoomViewModel::class.java
    }

    override fun getResourceLayout(): Int = R.layout.fragment_manager_room

    override fun initView() {

    }

    override fun bindViewModel() {

    }
}