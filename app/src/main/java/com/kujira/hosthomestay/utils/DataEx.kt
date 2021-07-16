package com.kujira.hosthomestay.utils

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.subjects.PublishSubject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import com.kujira.hosthomestay.data.model.response.ErrorResponse

/**
 * Created by OpenYourEyes on 4/4/21
 */
class ListLiveData<T> : MutableLiveData<MutableList<T>>()

fun <T> LiveData<T>.hasValue(): Boolean {
    return value?.let {
        true
    } ?: kotlin.run {
        false
    }
}


fun <T> Flow<T>.trackingProgress(progressBar: PublishSubject<Boolean>?): Flow<T> {
    val indicator = progressBar ?: return this
    return this.onStart {
        indicator.onNext(true)
    }.onCompletion {
        indicator.onNext(false)
    }
}

fun <T> Flow<T>.trackingError(trackingError: PublishSubject<ErrorResponse>): Flow<T> {
    return this.catch { error ->
        val message = error.message ?: "Error"
        trackingError.onNext(ErrorResponse(message))
    }
}