package com.kujira.hosthomestay.utils.widget.view

import android.view.View
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe

/**
 * Created by PhucBv on 5/2021
 */
fun View.click(): Observable<Unit> {
    return Observable.create {
        it.onNext(Unit)
    }
}