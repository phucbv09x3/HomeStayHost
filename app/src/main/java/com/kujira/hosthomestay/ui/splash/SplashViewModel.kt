package com.kujira.hosthomestay.ui.splash

import android.os.Looper
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.kujira.hosthomestay.ui.base.BaseViewModel
import com.kujira.hosthomestay.utils.Constants

class SplashViewModel : BaseViewModel() {
    val auth = FirebaseAuth.getInstance()
    val autoLogin = MutableLiveData<Int>()

    fun checkCurrentUser() {
        showLoading.onNext(true)
        if (auth.currentUser?.isEmailVerified == false || auth.currentUser == null) {
            showLoading.onNext(false)
            autoLogin.value = 0
        } else {
            showLoading.onNext(false)
            android.os.Handler(Looper.getMainLooper()).postDelayed({
                autoLogin.value = 1
            }, 300)
        }
    }
}