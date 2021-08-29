package com.kujira.hosthomestay.ui.login

import android.content.DialogInterface
import android.view.View
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth
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
        viewModel.click(requireView())
        dataBinding.tvForgotPassword.setOnClickListener {
            forgotPassWord()
        }
        viewModel.getListAcc()
    }

    private fun forgotPassWord() {
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Đặt lại mật khẩu")
        val linearLayout = LinearLayout(context)

        val edtMail = EditText(context)
        linearLayout.addView(edtMail)
        builder.setView(linearLayout)
        edtMail.hint = "Nhập email bạn cần khôi phục mật khẩu"
        builder.setPositiveButton("Gửi") { _: DialogInterface?, _: Int ->
            FirebaseAuth.getInstance().sendPasswordResetEmail(edtMail.text.toString().trim())
                .addOnCompleteListener { p0 ->
                    if (p0.isSuccessful) {
                        Toast.makeText(
                            context,
                            "Thành công ! Vui lòng kiểm tra mail và đặt lại mật khẩu",
                            Toast.LENGTH_LONG
                        ).show()
                    } else {
                        Toast.makeText(
                            context,
                            "Send failed....",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
        }
        builder.setNegativeButton("Không") { dialog: DialogInterface?, _: Int ->
            dialog!!.dismiss()
        }
        builder.show()

    }

    override fun bindViewModel() {
        viewModel.listenerData.observe(this, {
            when (it) {
                LoginViewModel.VERIFY -> {
                    replaceFragment(R.id.managerRoomFragment)
                }
                R.string.error_auth -> {
                    Toast.makeText(context, getString(R.string.error_auth), Toast.LENGTH_LONG)
                        .show()
                }
                R.string.error -> {
                    Toast.makeText(context, getString(R.string.error), Toast.LENGTH_LONG).show()
                }
                1 -> {
                    Toast.makeText(context, getString(R.string.error_register), Toast.LENGTH_LONG)
                        .show()
                }
            }
        })
    }
}