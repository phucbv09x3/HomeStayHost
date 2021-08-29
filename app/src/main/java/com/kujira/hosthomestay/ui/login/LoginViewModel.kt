package com.kujira.hosthomestay.ui.login

import android.util.Log
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

/**
 * Created by OpenYourEyes on 10/22/2020
 */
class LoginViewModel : BaseViewModel() {
    var email = ObservableField<String>()
    var password = ObservableField<String>()
    private var auth = FirebaseAuth.getInstance()
    val listenerData = MutableLiveData<Int>()

    companion object {
        const val VERIFY = 1
    }

    fun click(view: View) {
        when (view.id) {
            R.id.btn_login -> {
                checkSignIn()
            }

            R.id.tv_register -> {
                navigation.navigate(R.id.registerFragment)
            }
        }
    }

    private var listMail = mutableListOf<String>()
    fun getListAcc(): MutableList<String> {

        val dataRef =
            FirebaseDatabase.getInstance().getReference(Constants.HOST).child(Constants.ACCOUNT)
        dataRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                listMail.clear()
                for (snap in snapshot.children) {

                    listMail.add(snap.child(Constants.MAIL).value.toString())
                }
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })
        return listMail

    }

    private fun checkSignIn() {
        val email = email.get() ?: ""
        val password = password.get() ?: ""
        val isCheck = listMail.contains(email)
        if (email.isNotEmpty() && password.isNotEmpty()) {
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        val user = auth.currentUser
                        if (user!!.isEmailVerified) {
                            if (isCheck) {
                                listenerData.value = VERIFY
                            } else {
                                listenerData.value = R.string.not_exit_account
                            }
                        } else {
                            listenerData.value = R.string.error_auth
                        }
                    }
                }
                .addOnFailureListener {
                    listenerData.value = R.string.error
                }
        } else {
            listenerData.value = 1
        }
    }

    fun checkCurrentUser() {
        Log.d("currentUSer", "${auth.currentUser?.isEmailVerified}")
        if (auth.currentUser?.isEmailVerified == false || auth.currentUser == null) {

        } else {
            navigation.navigate(R.id.managerRoomFragment)
        }
    }
}