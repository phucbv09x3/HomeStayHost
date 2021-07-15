package open.hosthomestay.ui.login

import open.hosthomestay.R
import open.hosthomestay.databinding.LoginFragmentBinding
import open.hosthomestay.ui.base.BaseFragment

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
    }

    override fun bindViewModel() {

    }
}