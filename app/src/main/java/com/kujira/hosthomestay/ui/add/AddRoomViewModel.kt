package com.kujira.hosthomestay.ui.add

import android.net.Uri
import android.view.View
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.kujira.hosthomestay.R
import com.kujira.hosthomestay.data.model.response.DistrictFB
import com.kujira.hosthomestay.data.model.response.ProvinceFB
import com.kujira.hosthomestay.ui.base.BaseViewModel
import com.kujira.hosthomestay.utils.Constants

class AddRoomViewModel : BaseViewModel() {

    private var listProvinceFB = mutableListOf<ProvinceFB>()
    private var listDistrictsFB = mutableListOf<DistrictFB>()

    var listDistrictFBLiveData = MutableLiveData<MutableList<DistrictFB>>()
    var listProvincesFB = MutableLiveData<MutableList<ProvinceFB>>()
    private var dataReference =
        FirebaseDatabase.getInstance().getReference("Host")
    var auth = FirebaseAuth.getInstance()
    private var dataStoreRef = FirebaseStorage.getInstance().getReference("HostStorage")
    var introduce = ObservableField<String>()
    var listenerBtnAddHome = MutableLiveData<Int>()

    var listenerSuccess = MutableLiveData<Int>()
    var notifyPut = MutableLiveData<Int>()
    var linkImg1 = ""

    companion object {
        const val BTN_IMG_1 = 0
        const val BTN_IMG_ACCESS = 2
        const val BTN_IMG_ACCESS_ALL = 3
    }

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

    fun getListDistrictFB(name: String) {
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

    fun onClick(view: View) {
        when (view.id) {
            R.id.btn_add_img_1 -> {
                listenerBtnAddHome.value = BTN_IMG_1
            }
            R.id.btn_access_add -> {
                listenerBtnAddHome.value = BTN_IMG_ACCESS
            }
            R.id.btn_access_all -> {
                listenerBtnAddHome.value = BTN_IMG_ACCESS_ALL
            }
        }
    }

    fun putTravel(img1: Uri) {
        val imgRef = dataStoreRef.child(auth.currentUser!!.uid)
            .child("image")
        val imgName1 = imgRef.child(img1.toString())
        imgName1.putFile(img1)
            .addOnSuccessListener {
                imgName1.downloadUrl.addOnCompleteListener { p0 ->
                    if (p0.isSuccessful) {
                        linkImg1 = p0.result.toString()
                        listenerSuccess.value = 1

                    }
                }.addOnFailureListener {

                }
            }
    }

    val dataRefer =
        FirebaseDatabase.getInstance().getReference(Constants.CLIENT)

    fun addTravel(address: String) {
        showLoading.onNext(true)
        val hashMap = HashMap<String, String>()
        hashMap["address"] = address
        hashMap["detail"] = introduce.get().toString()
        hashMap["id"] = "key"
        hashMap["img"] = linkImg1

        dataRefer.child("TravelList").child(System.currentTimeMillis().toString()).setValue(hashMap)
            .addOnSuccessListener {
                showLoading.onNext(false)
            }.addOnFailureListener {
                showLoading.onNext(false)
            }
    }
}

