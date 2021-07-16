package com.kujira.hosthomestay.data.api

import com.kujira.hosthomestay.data.model.response.ProvinceItem
import retrofit2.Call
import retrofit2.http.GET

/**
 * Created by OpenYourEyes on 4/4/21
 */
interface ApiCoroutines {
//    @GET("search/repositories")
//    suspend fun getRepoGit(
//        @Query("q") q: String = "trending",
//        @Query("sort") stars: String = "stars"
//    ): GitResponse
//
//    @POST("api/login")
//    fun login(): BaseResponse<LoginViewModel?>

    @GET("/api/?depth=3")
     fun getListProvince() :  Call<MutableList<ProvinceItem>>
}