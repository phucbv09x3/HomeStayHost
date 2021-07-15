package open.your.eyes.miichibaseapp.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.PublishSubject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import open.your.eyes.miichibaseapp.data.DataManager
import open.your.eyes.miichibaseapp.data.api.ApiCoroutines
import open.your.eyes.miichibaseapp.data.api.IApiService
import open.your.eyes.miichibaseapp.data.model.response.BaseResponse
import open.your.eyes.miichibaseapp.data.model.response.ErrorResponse
import open.your.eyes.miichibaseapp.data.scheduler.ISchedulerProvider
import open.your.eyes.miichibaseapp.utils.printLog
import open.your.eyes.miichibaseapp.utils.trackingError
import open.your.eyes.miichibaseapp.utils.trackingProgress

/**
 * Created by OpenYourEyes on 11/26/2019
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