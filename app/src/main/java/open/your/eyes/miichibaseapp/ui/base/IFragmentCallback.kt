package open.your.eyes.miichibaseapp.ui.base

import android.content.Context

interface IFragmentCallback {
    fun getContext(): Context
    fun showLoading()
    fun hideLoading()
}