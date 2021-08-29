package com.kujira.hosthomestay.ui.register

import android.os.Bundle
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
 * Created by OpenYourEyes on 10/24/2020
 */
class RegisterViewModel : BaseViewModel() {
    var userName = ObservableField<String>()
    var email = ObservableField<String>()
    var password = ObservableField<String>()
    var numberPhone = ObservableField<String>()
    val notifyRegister = MutableLiveData<Int>()
    val onClick = MutableLiveData<Int>()
    private var deviceId = ""

    companion object {
        const val NOTIFY_AUTH_FAIL = 1
    }

    private var mAuth = FirebaseAuth.getInstance()
    private var dataReference =
        FirebaseDatabase.getInstance().getReference("Host").child("Account")

    fun register(view: View) {
        when (view.id) {
            R.id.btn_register -> {
                onClick.value = 2
            }
        }
    }

    fun registerAcc() {
        val userName = userName.get()
        val mail = email.get()
        val passWord = password.get()
        val phone = numberPhone.get()
        if (userName?.isEmpty() == false && mail?.isEmpty() == false
            && passWord?.isEmpty() == false && phone?.isEmpty() == false
        ) {
            mAuth.createUserWithEmailAndPassword(mail, passWord)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val user = mAuth.currentUser
                        val mail = user?.email
                        val userUID = user?.uid
                        val hashMap = HashMap<String, String>()
                        hashMap["userName"] = userName
                        hashMap["mail"] = mail!!
                        hashMap["uid"] = userUID!!
                        hashMap["password"] = passWord
                        hashMap["phone"] = phone!!
                        hashMap["device_id"] = deviceId
                        dataReference?.child(userUID).setValue(hashMap)

                        user.sendEmailVerification()
                            .addOnCompleteListener {
                                val bundle = Bundle()
                                bundle.putString("email", mail)
                                bundle.putString("pass", passWord)
                                navigation.navigate(R.id.loginFragment, bundle)
                            }
                    } else {
                        notifyRegister.value = NOTIFY_AUTH_FAIL
                    }
                }
        } else {
            notifyRegister.value = R.string.error_register
        }
    }

    fun checkDeviceId(id: String): Boolean {
        deviceId = id
        return listDeviceId.contains(deviceId)
    }

    private var listDeviceId = mutableListOf<String>()
    fun getListAcc(): MutableList<String> {

        val dataRef =
            FirebaseDatabase.getInstance().getReference(Constants.HOST).child(Constants.ACCOUNT)
        dataRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                listDeviceId.clear()
                for (snap in snapshot.children) {
                    listDeviceId.add(snap.child(Constants.DEVICE_ID).value.toString())
                }
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })
        return listDeviceId

    }
}