package open.your.eyes.miichibaseapp.utils.widget.view

import android.view.View
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe

/**
 * Created by OpenYourEyes on 11/2/20
 */
fun View.click(): Observable<Unit> {
    return Observable.create {
        it.onNext(Unit)
    }
}