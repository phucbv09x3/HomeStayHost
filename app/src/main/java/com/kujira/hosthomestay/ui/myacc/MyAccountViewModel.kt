package com.kujira.hosthomestay.ui.myacc

import androidx.databinding.ObservableField
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.kujira.hosthomestay.ui.base.BaseViewModel

class MyAccountViewModel : BaseViewModel() {
    val myName = ObservableField<String>()
    val myMail = ObservableField<String>()
    val myPhone = ObservableField<String>()
    private var auth = FirebaseAuth.getInstance()
    private var dataReference = FirebaseDatabase.getInstance().getReference("Host")
    fun updateUI() {

        dataReference.child("Account").child(auth.currentUser!!.uid)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val name = snapshot.child("userName").value.toString()
                    val email = snapshot.child("mail").value.toString()
                    val phone = snapshot.child("phone").value.toString()
                    myName.set(name)
                    myMail.set(email)
                    myPhone.set("SĐT : $phone")
                }

                override fun onCancelled(error: DatabaseError) {

                }
            })
    }

    fun changeAcc(name: String, phone: String) {
        dataReference.child("Account").child(auth.currentUser!!.uid)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val hash = HashMap<String, Any>()
                    hash["userName"] = name
                    hash["phone"] = phone
                    dataReference.updateChildren(hash)
                        .addOnSuccessListener {
                        }
                }

                override fun onCancelled(error: DatabaseError) {

                }
            })
    }
}