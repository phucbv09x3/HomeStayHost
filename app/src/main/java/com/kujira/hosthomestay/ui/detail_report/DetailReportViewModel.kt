package com.kujira.hosthomestay.ui.detail_report

import android.view.View
import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.*
import com.kujira.hosthomestay.R
import com.kujira.hosthomestay.ui.base.BaseViewModel
import com.kujira.hosthomestay.utils.Constants
import com.kujira.hosthomestay.utils.printLog

class DetailReportViewModel : BaseViewModel() {
    private val dataRef = FirebaseDatabase.getInstance().getReference("Client")
    private var listAcc = mutableListOf<ReportModel>()
    var listAccLiveData = MutableLiveData<MutableList<ReportModel>>()
    var uidClient =""
    fun getListReport(uid : String) {
        showLoading.onNext(true)
        dataRef.child("Report").child("ReportClient").child(uid)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    listAcc.clear()
                    for (snap in snapshot.children) {
                        val idHost = snap.child("idHost").value.toString()
                        val content = snap.child("contentReport").value.toString()
                        listAcc.add(ReportModel(idHost, content))
                    }
                    listAccLiveData.value = listAcc
                    showLoading.onNext(false)
                }

                override fun onCancelled(error: DatabaseError) {
                    showLoading.onNext(false)
                }
            })
    }

    fun resetReportAccClient(uid: String) {
        showLoading.onNext(true)
        val dataRefer = FirebaseDatabase.getInstance().getReference(Constants.CLIENT)
        dataRefer.child("Report").child("ReportClient").child(uid).removeValue()
            .addOnSuccessListener {
                showLoading.onNext(false)
                navigation.navigateUp()
            }
    }
    fun resetReportAccHost(uid: String) {
        showLoading.onNext(true)
        val dataRefer = FirebaseDatabase.getInstance().getReference(Constants.CLIENT)
        dataRefer.child("Report").child("ReportHost").child(uid).removeValue()
            .addOnSuccessListener {
                showLoading.onNext(false)
                navigation.navigateUp()
            }
    }
}