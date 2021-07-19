package com.kujira.hosthomestay.ui.register

import android.view.View
import android.widget.Toast
import com.kujira.hosthomestay.R
import com.kujira.hosthomestay.databinding.RegisterFragmentBinding
import com.kujira.hosthomestay.ui.base.BaseFragment
import kotlinx.android.synthetic.main.activity_main.*

/**
 * Created by OpenYourEyes on 10/24/2020
 */
class RegisterFragment : BaseFragment<RegisterViewModel, RegisterFragmentBinding>() {
    override fun createViewModel(): Class<RegisterViewModel> {
        return RegisterViewModel::class.java
    }

    override fun getResourceLayout(): Int {
        return R.layout.register_fragment
    }

    override fun bindViewModel() {
    }

    override fun initView() {
        activity.linear_on_main.visibility = View.GONE
        viewModel.notifyRegister.observe(this, {
            when (it) {
                R.string.error_register -> {
                    Toast.makeText(context, getString(it), Toast.LENGTH_LONG).show()
                }
                RegisterViewModel.NOTIFY_AUTH_FAIL -> {

                }
            }
        })
    }

}