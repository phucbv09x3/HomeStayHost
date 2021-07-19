package com.kujira.hosthomestay.ui.login

import android.view.View
import android.widget.Toast
import com.kujira.hosthomestay.R
import com.kujira.hosthomestay.databinding.LoginFragmentBinding
import com.kujira.hosthomestay.ui.base.BaseFragment
import kotlinx.android.synthetic.main.activity_main.*

/**
 * Created by OpenYourEyes on 10/24/2020
 */
class LoginFragment : BaseFragment<LoginViewModel, LoginFragmentBinding>() {
    override fun createViewModel(): Class<LoginViewModel> {
        return LoginViewModel::class.java
    }

    override fun getResourceLayout(): Int {
        return R.layout.login_fragment
    }


    override fun initView() {
        activity.linear_on_main.visibility = View.GONE
        viewModel.checkCurrentUser()
        val arg = arguments
        val email = arg?.getString("email") ?: ""
        val passWord = arg?.getString("pass") ?: ""
        viewModel.email.set(email)
        viewModel.password.set(passWord)
        viewModel.login(requireView())
    }

    override fun bindViewModel() {
        viewModel.authFail.observe(this,{
            when(it){
                R.string.error_auth -> {
                    Toast.makeText(context,getString(it),Toast.LENGTH_LONG).show()
                }
                1 -> {
                    Toast.makeText(context,"isEmpty",Toast.LENGTH_LONG).show()
                }
            }
        })
    }
}