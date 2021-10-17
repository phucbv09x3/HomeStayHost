package com.kujira.hosthomestay.ui.all_login.login_new

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.FragmentNavigator
import com.google.firebase.auth.FirebaseAuth
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

    override fun onStart() {
        super.onStart()
        mViewModel.autoLogin()
        mViewModel.autoLogin.observe(this,{
            if (it==1){
                startActivity(Intent(this,MainHostActivity::class.java))
            }else{

            }
        })
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
                    forgot()
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
    private fun forgot() {
        val buider = AlertDialog.Builder(this)
        buider.setTitle("Đặt lại mật khẩu")
        val linea = LinearLayout(this)

        val edt_mail = EditText(this)
        linea.addView(edt_mail)
        buider.setView(linea)
        edt_mail.hint = "Nhập email bạn cần khôi phục mật khẩu"
        buider.setPositiveButton("Gửi") { dialog: DialogInterface?, which: Int ->
            val mAuth =
                FirebaseAuth.getInstance().sendPasswordResetEmail(edt_mail.text.toString().trim())
                    .addOnCompleteListener { p0 ->
                        if (p0.isSuccessful) {
                            Toast.makeText(
                                this,
                                "Send success....",
                                Toast.LENGTH_LONG
                            ).show()
                        } else {
                            Toast.makeText(
                                this,
                                "Send failed....",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
        }
        buider.setNegativeButton("Không") { dialog: DialogInterface?, which: Int ->
            dialog!!.dismiss()
        }
        buider.show()
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