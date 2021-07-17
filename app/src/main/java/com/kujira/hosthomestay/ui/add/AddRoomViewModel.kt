package com.kujira.hosthomestay.ui.add

import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.kujira.hosthomestay.data.model.response.DistrictFB
import com.kujira.hosthomestay.data.model.response.ProvinceFB
import com.kujira.hosthomestay.ui.base.BaseViewModel

class AddRoomViewModel : BaseViewModel() {
    var listProvincesFB = MutableLiveData<MutableList<ProvinceFB>>()
    private var listProvinceFB = mutableListOf<ProvinceFB>()

    var listDistrictFBLiveData = MutableLiveData<MutableList<DistrictFB>>()
    private var listDistrictsFB = mutableListOf<DistrictFB>()
    var dataReference =
        FirebaseDatabase.getInstance().getReference("Host")

    fun getListProvincesFB() {
        dataReference.child("ListProvinces")
            .addValueEventListener(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                listProvinceFB.clear()
                for (pos in snapshot.children) {
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
    fun getListDistrictFB(name:String) {
        listDistrictsFB.clear()
        dataReference.child("DetailProvince").child(name).child("Districts")
            .addValueEventListener(object : ValueEventListener {
                override fun onCancelled(error: DatabaseError) {
                }

                override fun onDataChange(snapshot: DataSnapshot) {
                    for (pos in snapshot.children) {
                        val obDistrictFB = DistrictFB(
                            pos.child("code").value.toString(),
                            pos.child("name").value.toString(),

                        )
                        listDistrictsFB.add(obDistrictFB)
                    }
                    listDistrictFBLiveData.value = listDistrictsFB
                }

            })
    }

}

