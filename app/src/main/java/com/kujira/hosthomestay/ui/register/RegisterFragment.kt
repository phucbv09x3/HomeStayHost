package com.kujira.hosthomestay.ui.register

import android.annotation.SuppressLint
import android.provider.Settings
import android.view.View
import android.widget.Toast
import com.kujira.hosthomestay.R
import com.kujira.hosthomestay.databinding.RegisterFragmentBinding
import com.kujira.hosthomestay.ui.base.BaseFragment
import kotlinx.android.synthetic.main.activity_host_main.*

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

    override fun initView() {
        activity.linear_on_main.visibility = View.GONE
        viewModel.getListAcc()
    }

    @SuppressLint("HardwareIds")
    override fun bindViewModel() {
        viewModel.onClick.observe(this, {
            when (it) {
                2 -> {
                    val id =
                        Settings.Secure.getString(
                            activity.contentResolver,
                            Settings.Secure.ANDROID_ID
                        )
                    if (viewModel.checkDeviceId(id)) {
                        Toast.makeText(context, getString(R.string.register_out), Toast.LENGTH_LONG)
                            .show()
                    } else {
                        viewModel.registerAcc()
                        viewModel.notifyRegister.observe(this, { value ->
                            when (value) {
                                R.string.error_register -> {
                                    Toast.makeText(context, getString(value), Toast.LENGTH_LONG)
                                        .show()
                                }
                                RegisterViewModel.NOTIFY_AUTH_FAIL -> {

                                }
                            }

                        })
                    }
                }

            }
        })
    }

}