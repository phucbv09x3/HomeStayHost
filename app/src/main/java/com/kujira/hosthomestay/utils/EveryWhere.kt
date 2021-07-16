package open.hosthomestay.utils

import android.util.Log
import open.hosthomestay.BuildConfig

/**
 * Created by OpenYourEyes on 10/22/2020
 */

fun printLog(message: Any?, prefix: String = "") {
    if (message == null || !BuildConfig.DEBUG) return
    val stackTraceElement = Thread.currentThread().stackTrace[4]
    Log.d("[MiiLog${prefix} - ${stackTraceElement.fileName}]", "#$message")

}