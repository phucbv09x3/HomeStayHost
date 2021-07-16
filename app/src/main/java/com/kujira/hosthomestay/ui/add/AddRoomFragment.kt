package com.kujira.hosthomestay.ui.add

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kujira.hosthomestay.R
import com.kujira.hosthomestay.databinding.FragmentAddRoomBinding
import com.kujira.hosthomestay.ui.base.BaseFragment

class AddRoomFragment : BaseFragment<AddRoomViewModel,FragmentAddRoomBinding>() {
    override fun createViewModel(): Class<AddRoomViewModel> {
        return AddRoomViewModel::class.java
    }

    override fun getResourceLayout(): Int = R.layout.fragment_add_room

    override fun initView() {

    }

    override fun bindViewModel() {

    }
}