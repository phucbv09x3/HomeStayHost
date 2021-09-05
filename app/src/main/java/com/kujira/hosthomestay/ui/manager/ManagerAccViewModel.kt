package com.kujira.hosthomestay.ui.manager

import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.kujira.hosthomestay.data.model.response.AccUser
import com.kujira.hosthomestay.data.model.response.AddRoomModel
import com.kujira.hosthomestay.ui.base.BaseViewModel
import com.kujira.hosthomestay.utils.Constants

class ManagerAccViewModel : BaseViewModel() {

    private var auth = FirebaseAuth.getInstance()
    private var dataReferences = FirebaseDatabase.getInstance().getReference(Constants.CLIENT)

    private var listRoom = mutableListOf<AccUser>()
    var listRoomLiveData = MutableLiveData<MutableList<AccUser>>()

    fun managerListAcc() {
        val query = dataReferences.child(Constants.ACCOUNT)
        query.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                listRoom.clear()
                for (snap in snapshot.children) {
                    val obStatus = AccUser(
                        snap.child("uid").value.toString(),
                        snap.child("mail").value.toString(),
                        snap.child("phone").value.toString(),
                        snap.child("userName").value.toString(),
                        snap.child("permission").value.toString(),

                    )
                    listRoom.add(obStatus)
                }
                listRoomLiveData.value = listRoom

            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }

    fun blockAcc(uid : String){
        showLoading.onNext(true)
        dataReferences.child("Report")
        val hashMap = HashMap<String, String>()
        hashMap["idHost"] = auth.currentUser?.uid.toString()
        hashMap["idClient"] = uid
        hashMap["contentReport"] = "AdminBlock"
        dataReferences.child("Report").child("ReportHost")
            .child(uid).setValue(hashMap)
            .addOnSuccessListener {
                listener.value = 1
                showLoading.onNext(false)
            }
            .addOnFailureListener {
                listener.value = 2
            }
    }
    fun blockAccClient(uid : String){
        showLoading.onNext(true)
        dataReferences.child("Report")
        val hashMap = HashMap<String, String>()
        hashMap["idHost"] = auth.currentUser?.uid.toString()
        hashMap["idClient"] = uid
        hashMap["contentReport"] = "AdminBlock"
        for (i in 0..10){
            dataReferences.child("Report").child("ReportClient")
                .child(uid).child(System.currentTimeMillis().toString()).setValue(hashMap)
                .addOnSuccessListener {
                    listener.value = 1
                    showLoading.onNext(false)
                }
                .addOnFailureListener {
                    listener.value = 2
                }
        }

    }

//    fun removeRoom(addRoomModel: AddRoomModel) {
//        dataReferences.child(addRoomModel.id).removeValue()
//    }

    var listener = MutableLiveData<Int>()
    fun editRoom(addRoomModel: AddRoomModel) {
        dataReferences.orderByChild("id").equalTo(addRoomModel.id).limitToFirst(1)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val hash = HashMap<String, Any>()
                    hash["nameRoom"] = addRoomModel.nameRoom
                    hash["s_room"] = addRoomModel.s_room
                    hash["numberSleepRoom"] = addRoomModel.numberSleepRoom
                    hash["convenient"] = addRoomModel.convenient
                    hash["introduce"] = addRoomModel.introduce
                    hash["price"] = addRoomModel.price
                    dataReferences.child(addRoomModel.id).updateChildren(hash)
                        .addOnSuccessListener {
                            listener.value = 1
                        }
                }

                override fun onCancelled(error: DatabaseError) {

                }
            })
    }
    fun cancelRoom(id: String) {
        val dataRef = FirebaseDatabase.getInstance().getReference("Host")
        dataRef.child("ListRoom").child(id).child("idClient").removeValue()
        val query= dataRef.child("ListRoom")
        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val hash = HashMap<String, Any>()
                hash["status"] = "Còn Trống"
                query.child(id).updateChildren(hash)
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }
}

