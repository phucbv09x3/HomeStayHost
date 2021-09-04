package com.kujira.hosthomestay.ui.all_login.login_new

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.FragmentNavigator
import com.kujira.hosthomestay.R
import com.kujira.hosthomestay.databinding.ActivityLoginBinding
import com.kujira.hosthomestay.ui.all_login.register_new.RegisterActivity
import com.kujira.hosthomestay.ui.base.BaseActivity
import com.kujira.hosthomestay.ui.base.BaseFragment
import com.kujira.hosthomestay.ui.main.MainHostActivity
import com.kujira.hosthomestay.utils.printLog

class LoginActivity : BaseActivity<LoginAccViewModel, ActivityLoginBinding>() {
    override fun createViewModel(): Class<LoginAccViewModel> {
        return LoginAccViewModel::class.java
    }

    override fun getContentView(): Int = R.layout.activity_login

    override fun initAction() {

    }

    override fun initData() {
        window.statusBarColor = ContextCompat.getColor(this, R.color.bg_login_new)
        listener()
        mViewModel.getListAcc()
    }

    private val startForResult = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result: ActivityResult ->
        if (result.resultCode == RESULT_OK) {
            val data = result.data
            val mail = data?.getStringExtra("1").toString()
            mDataBinding.edtEmailLogin.setText(mail)
        }


    }

    private fun listener() {
        mViewModel.listenerClick.observe(this, {
            when (it) {
                LoginAccViewModel.REGISTER_ACC -> {
                    val intent = Intent(this, RegisterActivity::class.java)
                    startForResult.launch(intent)
                }
                LoginAccViewModel.FORGOT_PASSWORD -> {

                }
            }
        })
        mViewModel.listener.observe(this, {
            printLog("messsss: $it")
            if (it == LoginAccViewModel.SUCCESS) {
                val intent = Intent(this, MainHostActivity::class.java)
                startActivity(intent)
            } else {
                Toast.makeText(this, getString(it), Toast.LENGTH_LONG).show()
            }
        })
    }

    override fun onFragmentResumed(fragment: BaseFragment<*, *>) {

    }

    override fun navigate(fragmentId: Int, bundle: Bundle?, addToBackStack: Boolean) {

    }

    override fun navigateWithSharedElement(
        fragmentId: Int,
        bundle: Bundle?,
        sharedElements: FragmentNavigator.Extras?,
        addToBackStack: Boolean
    ) {

    }

    override fun navigateUp() {

    }

    override fun present(fragmentId: Int, bundle: Bundle?) {

    }
}