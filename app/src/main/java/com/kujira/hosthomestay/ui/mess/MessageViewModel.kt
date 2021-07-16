package com.kujira.hosthomestay.ui.mess

import androidx.lifecycle.MutableLiveData
import com.kujira.hosthomestay.data.api.ApiCoroutines
import com.kujira.hosthomestay.data.model.response.ProvinceItem
import com.kujira.hosthomestay.ui.base.BaseViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MessageViewModel : BaseViewModel() {
    var listProvince = MutableLiveData<MutableList<ProvinceItem>>()


    private val retrofit = Retrofit.Builder()
        .baseUrl("https://provinces.open-api.vn")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(ApiCoroutines::class.java)

    fun getPost() {
        val call =retrofit.getListProvince()
        call.enqueue(object : Callback<MutableList<ProvinceItem>> {
            override fun onResponse(
                call: Call<MutableList<ProvinceItem>>,
                response: Response<MutableList<ProvinceItem>>
            ) {
                listProvince.value = response.body()
            }

            override fun onFailure(call: Call<MutableList<ProvinceItem>>, t: Throwable) {

            }
        })
    }
}