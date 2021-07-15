package open.your.eyes.miichibaseapp.utils

import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import open.your.eyes.miichibaseapp.data.model.response.BaseResponse
import open.your.eyes.miichibaseapp.data.model.response.ErrorResponse

/**
 * Created by OpenYourEyes on 10/24/2020
 */
fun <T : BaseResponse<*>> Observable<T>.mapToResult(): Observable<Result<T>> {
    return this.map {
        if (it.isSuccess) {
            Result.success(it)
        } else {
            Result.failure(it.error ?: ErrorResponse.defaultError())
        }
    }.onErrorReturn { Result.failure(it) }
}

//fun <T : BaseResponse<*>> Observable<T>.mapToResult(trackingError: PublishSubject<ErrorResponse>): Observable<T> {
//    return this.map {
//        if (it.isSuccess()) {
//            Result.success(it)
//        } else {
//            val throws = it.error ?: ErrorResponse.defaultError()
////            val error: ErrorResponse = HandlerErrorFromServer.handleErrorAPI(throws)
//            trackingError.onNext(throws)
//            Result.failure(throws)
//        }
//    }.onErrorReturn {
////        val error: ErrorResponse = HandlerErrorFromServer.handleErrorAPI(it)
//        trackingError.onNext(ErrorResponse.defaultError())
//        Result.failure(it)
//    }.filterNull()
//
//}


fun <D, T : Result<D>> Observable<T>.filterNull(): Observable<D> {
    return this.filter { result ->
        result.isSuccess
    }.map {
        it.getOrNull()!!
    }
}

class ResultResponse<T : Any> private constructor(val value: Any) {

    companion object {
        fun <T : Any> success(value: T): ResultResponse<T> = ResultResponse(value)

        fun <T : Any> failure(error: Throwable): ResultResponse<T> = ResultResponse(Failure(error))
    }

    val isSuccess: Boolean = this.value !is Failure

    val isFailure: Boolean = this.value is Failure

    @Suppress("UNCHECKED_CAST")
    fun materialize(onFailure: (error: Throwable) -> T): T = when (this.value) {
        is Failure -> onFailure.invoke(this.value.error)
        else -> this.value as T
    }

    @Suppress("UNCHECKED_CAST")
    fun <U : Any> map(f: (T) -> U): ResultResponse<U> = when (this.value) {
        is Failure -> failure(this.value.error)
        else -> success(f.invoke(this.value as T))
    }

    class Failure(val error: Throwable)
}

fun <T> Observable<T>.mapToResult(trackingError: PublishSubject<ErrorResponse>): Observable<T> {
    return this.map { response ->
        if (response is BaseResponse<*> && !response.isSuccess) {
            return@map ResultResponse.failure(ErrorResponse.defaultError())
        }
        ResultResponse.success(response!!)
    }.onErrorReturn { error ->
        ResultResponse.failure(error)
    }.filter { result ->
        val value = result.value
        if (value is ResultResponse.Failure) {
            val error = value.error as? ErrorResponse ?: ErrorResponse.defaultError()
            trackingError.onNext(error)
        }
        result.isSuccess
    }.map { result ->
        result.value as T
    }
}

//fun <T> Observable<T>.mapToResult(trackingError: PublishSubject<ErrorResponse>): Observable<T> {
//    return this.map { response ->
//        if (response is BaseResponse<*> && !response.isSuccess()) {
//            return@map Result.failure(ErrorResponse.defaultError())
//        }
//        val result = Result.success(response)
//        return@map result
//    }.onErrorReturn { error ->
//        Result.failure(error)
//    }.filter { result ->
//        if (result.isFailure) {
//            val error = result.exceptionOrNull() as? ErrorResponse ?: ErrorResponse.defaultError()
//            trackingError.onNext(error)
//        }
//        result.isSuccess
//    }.map { result ->
//        result.fold({ repo ->
//            printLog("fold")
//        }, {
//            printLog("fold")
//        })
//        result.getOrNull()!!
//    }
//}

