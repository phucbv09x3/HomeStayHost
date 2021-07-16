package com.kujira.hosthomestay.ui.add

import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.kujira.hosthomestay.data.model.response.ProvinceFB
import com.kujira.hosthomestay.ui.base.BaseViewModel

class AddRoomViewModel : BaseViewModel() {
    var listProvincesFB = MutableLiveData<MutableList<ProvinceFB>>()
    private var listProvinceFB = mutableListOf<ProvinceFB>()
    fun getListProvincesFB() {

        val dataReference =
            FirebaseDatabase.getInstance().getReference("Host").child("ListProvinces")
        dataReference.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                listProvinceFB.clear()
                for (pos in snapshot.children) {
//                    var status = pos.getValue(ProvinceFB::class.java)!!
                    val obProvinceFB = ProvinceFB(
                        pos.child("code").value.toString().toInt(),
                        pos.child("name").value.toString(),
                        pos.child("phone_code").value.toString().toInt(),
                    )
                    listProvinceFB.add(obProvinceFB)
                }
                listProvincesFB.value = listProvinceFB
            }

        })
    }
}