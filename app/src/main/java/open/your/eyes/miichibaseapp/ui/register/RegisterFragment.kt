package open.your.eyes.miichibaseapp.ui.register

import kotlinx.android.synthetic.main.register_fragment.*
import open.your.eyes.miichibaseapp.R
import open.your.eyes.miichibaseapp.databinding.RegisterFragmentBinding
import open.your.eyes.miichibaseapp.ui.base.BaseFragment

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
        buttonRegister.setOnClickListener {
            navigators.navigate(R.id.aboutFragment)
        }
    }

}