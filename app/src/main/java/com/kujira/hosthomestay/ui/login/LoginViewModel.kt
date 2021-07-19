package com.kujira.hosthomestay.ui.login

import android.util.Log
import android.view.View
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.kujira.hosthomestay.R
import com.kujira.hosthomestay.ui.base.BaseViewModel

/**
 * Created by OpenYourEyes on 10/22/2020
 */
class LoginViewModel : BaseViewModel() {
    var email = ObservableField<String>()
    var password = ObservableField<String>()
    private var auth = FirebaseAuth.getInstance()
    val authFail = MutableLiveData<Int>()

    fun login(view: View) {
        when (view.id) {
            R.id.btn_login -> {
                checkSignIn()
            }

            R.id.tv_register -> {
                navigation.navigate(R.id.registerFragment)
            }
        }
    }

    private fun checkSignIn() {
        val email = email.get() ?: ""
        val password = password.get() ?: ""
        if (email.isNotEmpty() && password.isNotEmpty()) {
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        val user = auth.currentUser
                        if (user!!.isEmailVerified) {
                            navigation.navigate(R.id.messageFragment)
                        } else {
                            authFail.value = R.string.error_auth
                        }
                    }
                }
        } else {
            authFail.value = 1
        }
    }
    fun checkCurrentUser(){
        Log.d("currentUSer","${auth.currentUser?.isEmailVerified}")
        if (auth.currentUser?.isEmailVerified == false || auth.currentUser == null){

        }else{
            navigation.navigate(R.id.messageFragment)
        }
    }
}