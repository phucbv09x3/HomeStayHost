package com.kujira.hosthomestay.ui.myacc

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kujira.hosthomestay.R
import com.kujira.hosthomestay.databinding.FragmentMyAccountBinding
import com.kujira.hosthomestay.ui.base.BaseFragment

class MyAccountFragment : BaseFragment<MyAccountViewModel, FragmentMyAccountBinding>() {
    override fun createViewModel(): Class<MyAccountViewModel> {
        return MyAccountViewModel::class.java
    }

    override fun getResourceLayout(): Int = R.layout.fragment_my_account

    override fun initView() {

    }

    override fun bindViewModel() {

    }
}