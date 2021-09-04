package com.kujira.hosthomestay.ui.all_login.register_new

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
import com.kujira.hosthomestay.utils.printLog

class RegisterAccViewModel : BaseViewModel() {
    val emailRegister = ObservableField<String>()
    val passwordRegister = ObservableField<String>()
    val questionIntroductory = ObservableField<String>()
    val numberPhoneRegister = ObservableField<String>()
    private val dataRef = FirebaseDatabase.getInstance().getReference(Constants.ADMIN)
    private var fireBaseAuth = FirebaseAuth.getInstance()
    private var listEmailAdmin = mutableListOf<String>()
    val listenerShowToast = MutableLiveData<Int>()
    private var adminIntroductory = ""

    fun click(view: View) {
        when (view.id) {
            R.id.btn_register_admin -> {
               if (adminIntroductory.isNotEmpty() && questionIntroductory.get().toString() == adminIntroductory){
                   registerAcc()
               }else{
                   listenerShowToast.value = R.string.introductory
               }
            }
        }
    }


    fun getIntroductory() {
        dataRef.child(Constants.IntroductorySentence)
            .addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                adminIntroductory = snapshot.child("admin").value.toString()
                printLog("adminIntroductory : $adminIntroductory")
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })
    }

    private fun registerAcc() {
        val adminIntroductory = questionIntroductory.get()
        val mail = emailRegister.get()
        val passWord = passwordRegister.get()
        val phone = numberPhoneRegister.get()
        if (adminIntroductory?.isEmpty() == false && mail?.isEmpty() == false
            && passWord?.isEmpty() == false && phone?.isEmpty() == false
        ) {
            showLoading.onNext(true)
            val isCheckMail = listEmailAdmin.contains(mail)
            if (isCheckMail) {
                 listenerShowToast.value = R.string.email_exist
            } else {
                fireBaseAuth.createUserWithEmailAndPassword(mail, passWord)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            showLoading.onNext(false)
                            val user = fireBaseAuth.currentUser
                            val mail = user?.email
                            val userUID = user?.uid
                            val hashMap = HashMap<String, String>()
                            hashMap[Constants.MAIL] = mail!!
                            hashMap[Constants.UID] = userUID!!
                            hashMap[Constants.PHONE] = phone
                            dataRef.child(Constants.ACCOUNT).child(userUID).setValue(hashMap)

                            user.sendEmailVerification()
                                .addOnCompleteListener {
                                    listenerShowToast.value = R.string.email_verify
                                }
                        } else {
                             listenerShowToast.value = R.string.register_fail
                        }
                    }
            }
        } else {
             listenerShowToast.value = R.string.error_isEmpty
        }
    }


    fun getListAcc(): MutableList<String> {
        val dataRef =
            FirebaseDatabase.getInstance().getReference(Constants.ADMIN).child(Constants.ACCOUNT)
        dataRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                listEmailAdmin.clear()
                for (snap in snapshot.children) {
                    listEmailAdmin.add(snap.child(Constants.MAIL).value.toString())
                }
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })
        return listEmailAdmin

    }
}