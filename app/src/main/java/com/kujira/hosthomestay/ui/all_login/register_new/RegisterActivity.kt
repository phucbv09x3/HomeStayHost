package com.kujira.hosthomestay.ui.all_login.register_new

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.FragmentNavigator
import com.kujira.hosthomestay.R
import com.kujira.hosthomestay.databinding.ActivityRegisterBinding
import com.kujira.hosthomestay.ui.all_login.login_new.LoginActivity
import com.kujira.hosthomestay.ui.base.BaseActivity
import com.kujira.hosthomestay.ui.base.BaseFragment
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : BaseActivity<RegisterAccViewModel, ActivityRegisterBinding>() {
    override fun createViewModel(): Class<RegisterAccViewModel> {
        return RegisterAccViewModel::class.java
    }

    override fun getContentView(): Int = R.layout.activity_register

    override fun initAction() {

    }

    override fun initData() {
        window.statusBarColor = ContextCompat.getColor(this, R.color.bg_login_new)
        mViewModel.getIntroductory()
        mViewModel.getListAcc()
        mViewModel.listenerShowToast.observe(this, {
            Toast.makeText(this, getString(it), Toast.LENGTH_LONG).show()
            if (it == R.string.email_verify) {
                val intent = Intent(this, LoginActivity::class.java)
                val email = edt_email_register.text.toString()
                val password = edt_password_register.text.toString()
                intent.putExtra("1", email)
                intent.putExtra("2", password)
                setResult(RESULT_OK, intent)
                finish()
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