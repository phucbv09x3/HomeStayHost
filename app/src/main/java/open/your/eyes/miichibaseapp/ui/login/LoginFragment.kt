package open.your.eyes.miichibaseapp.ui.login

import open.your.eyes.miichibaseapp.R
import open.your.eyes.miichibaseapp.databinding.LoginFragmentBinding
import open.your.eyes.miichibaseapp.ui.base.BaseFragment

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