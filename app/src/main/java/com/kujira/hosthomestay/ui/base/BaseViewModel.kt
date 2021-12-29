package com.kujira.hosthomestay.ui.base

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken
import com.kujira.hosthomestay.data.DataManager
import com.kujira.hosthomestay.data.api.ApiCoroutines
import com.kujira.hosthomestay.data.api.IApiService
import com.kujira.hosthomestay.data.model.response.BaseResponse
import com.kujira.hosthomestay.data.model.response.ErrorResponse
import com.kujira.hosthomestay.data.scheduler.ISchedulerProvider
import com.kujira.hosthomestay.utils.printLog
import com.kujira.hosthomestay.utils.trackingError
import com.kujira.hosthomestay.utils.trackingProgress
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.PublishSubject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import java.io.IOException

/**
 * Created by PhucBv on 5/2021
 */
open abstract class BaseViewModel : ViewModel() {
    lateinit var navigation: Navigators

    lateinit var dataManager: DataManager
    lateinit var apiService: IApiService
    lateinit var scheduler: ISchedulerProvider

    private val disposable: CompositeDisposable by lazy {
        CompositeDisposable()
    }
    val showLoading = PublishSubject.create<Boolean>()
    val apiWithAuthenticator: ApiCoroutines by lazy {
        apiService.apiWithAuthenticator()
    }
    val apiWithoutAuthenticator: ApiCoroutines by lazy {
        apiService.apiWithoutAuthenticator()
    }
    val trackingError: PublishSubject<ErrorResponse> by lazy {
        PublishSubject.create()
    }
    val gson: Gson by lazy {
        Gson()
    }

    fun initData(dataManager: DataManager, apiService: IApiService, scheduler: ISchedulerProvider) {
        this.dataManager = dataManager
        this.apiService = apiService
        this.scheduler = scheduler
    }

    open fun transform() {}

    /**
     * this is sample test
     */
    fun <T> executeRequestTest(
        request: suspend (ApiCoroutines.() -> T),
        isLoading: Boolean = true
    ): Flow<T> {
        val loading = if (isLoading) showLoading else null
        return flow {
            val result = request(apiWithoutAuthenticator)
            emit(result)
        }.flowOn(Dispatchers.IO)
            .trackingProgress(loading)
            .trackingError(trackingError)
    }

    /**
     * Execute api inside scope Coroutine
     * data is not allowed to be null, if null should be used
     * @see executeRequestFlow
     */
    fun <T> executeRequest(
        request: suspend (ApiCoroutines.() -> BaseResponse<T>),
        response: (T) -> Unit
    ): Job {
        return viewModelScope.launch {
            flow {
                val result = request(apiWithAuthenticator)
                if (!result.isSuccess) {
                    throw result.error ?: ErrorResponse("Unknow")
                }
                val data = result.data
                    ?: throw NullPointerException("Data must be not null, please using executeRequestFlow")
                showLoading.onNext(true)
                emit(data)
            }
                .trackingProgress(progressBar = showLoading)
                .trackingError(trackingError = trackingError)
                .flowOn(Dispatchers.IO)
                .collect {
                    response(it)
                }
        }
    }

    inline fun <reified T> executeRequestObject(
        crossinline request: (ApiCoroutines.() -> Call<JsonObject>),
        crossinline response: (T) -> Unit,
        isLoading: Boolean = true
    ): Job {
        return viewModelScope.launch(Dispatchers.IO) {

            if (isLoading) {
                withContext(Dispatchers.Main) {
                    showLoading.onNext(true)
                }
            }

            try {
                val jsonObject = request(apiWithoutAuthenticator).execute()

                val code = jsonObject.code()
                if (isLoading) {
                    withContext(Dispatchers.Main) {
                        showLoading.onNext(false)
                    }
                }
                withContext(Dispatchers.Main) {
                    Log.d("isSuccNot1","NotOk")
                    if (code == 200) {
                        Log.d("isSucc","ok")
                        val itemType = object : TypeToken<T>() {}.type
                        val responseModel =
                            gson.fromJson(jsonObject.body().toString(), itemType) as T
                        response(responseModel)
                    }else{
                        Log.d("isSuccNot","NotOk")
                    }
                }
            } catch (io: IOException) {
                printLog("error:$io")
            }
        }

    }

    /**
     * Using when data can be null or transform data before update ui
     */
    suspend fun <T> executeRequestFlow(
        request: suspend (ApiCoroutines.() -> BaseResponse<T>),
        isLoading: Boolean = true
    ): Flow<T?> {
        val loading = if (isLoading) showLoading else null
        return flow {
            val result = request(apiWithAuthenticator)
            if (!result.isSuccess) {
                throw result.error ?: ErrorResponse("UnKnow")
            }
            val data = result.data
            emit(data)
        }.flowOn(Dispatchers.IO)
            .trackingProgress(loading)
            .trackingError(trackingError)
    }


    fun Disposable.addDisposable() {
        disposable.add(this)
    }

    override fun onCleared() {
        disposable.dispose()
        super.onCleared()
    }

    fun onDestroyView() {
        disposable.clear()
    }
}