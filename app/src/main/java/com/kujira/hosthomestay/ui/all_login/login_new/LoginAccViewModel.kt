package com.kujira.hosthomestay.ui.all_login.login_new

import android.view.View
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.kujira.hosthomestay.R
import com.kujira.hosthomestay.ui.base.BaseViewModel
import com.kujira.hosthomestay.utils.Constants
import java.nio.charset.StandardCharsets
import java.security.MessageDigest
import java.util.*

class LoginAccViewModel : BaseViewModel() {
    val emailLogin = ObservableField<String>()
    val passwordLogin = ObservableField<String>()
    val listenerClick = MutableLiveData<Int>()
    private var auth = FirebaseAuth.getInstance()
    val listener = MutableLiveData<Int>()
    val autoLogin = MutableLiveData<Int>()
    private val dataRef =
        FirebaseDatabase.getInstance().getReference(Constants.ADMIN)

    companion object {
        const val LOGIN = 1
        const val FORGOT_PASSWORD = 2
        const val REGISTER_ACC = 3
        const val SUCCESS = 4
        const val PERMISSION_2 = 5
    }

    fun click(view: View) {
        when (view.id) {
            R.id.btn_login_new -> {
                checkSignIn()
            }
            R.id.tv_forgot_password_new -> {
                listenerClick.value = FORGOT_PASSWORD
            }
            R.id.tv_register_new -> {
                listenerClick.value = REGISTER_ACC
            }
        }
    }

    private var listMailAdmin = mutableListOf<String>()
    fun getListAcc() {
        dataRef.child(Constants.ACCOUNT)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    listMailAdmin.clear()
                    for (snap in snapshot.children) {
                        val mail = snap.child(Constants.MAIL).value.toString()
                        listMailAdmin.add(mail)
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                }
            })

    }

    private fun checkSignIn() {
        val email = emailLogin.get()?.trim() ?: ""
        val password = passwordLogin.get()?.trim() ?: ""

        if (email.isNotEmpty() && password.isNotEmpty()) {
            if (listMailAdmin.isEmpty()) {
                listener.value = R.string.repair
            }

            val acc = listMailAdmin.firstOrNull {
                it.lowercase(Locale.getDefault()) == email.lowercase(Locale.getDefault())
            }
            if (acc != null) {
                showLoading.onNext(true)
                auth.signInWithEmailAndPassword(email, hashFunc(password))
                    .addOnCompleteListener {
                        if (it.isSuccessful) {
                            val user = auth.currentUser
                            if (user!!.isEmailVerified) {
                                showLoading.onNext(false)
                                listener.value = SUCCESS
                            } else {
                                showLoading.onNext(false)
                                listener.value = R.string.error_auth
                            }
                        }
                    }
                    .addOnCanceledListener {
                        showLoading.onNext(false)
                    }
                    .addOnFailureListener {
                        showLoading.onNext(false)
                    }
            } else {
                listener.value = R.string.not_exit_account
            }

        } else {
            listener.value = R.string.error_isEmpty
        }
    }

    private fun hashFunc(textEncrypt: String): String {
        val md5 = MessageDigest.getInstance("MD5")//SHA-256
        var sbb = ""
        val byteArray: ByteArray = md5.digest(textEncrypt.toByteArray(StandardCharsets.UTF_8))
        for (item in byteArray) {
            sbb += String.format("%02x", item)
        }
        return sbb
    }

}
