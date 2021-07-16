package com.kujira.hosthomestay.ui.base

import android.os.Bundle
import androidx.annotation.IdRes
import androidx.navigation.fragment.FragmentNavigator

interface Navigators {

    fun onFragmentResumed(fragment: BaseFragment<*, *>)

    /**
     * using
     */
    fun navigate(
        @IdRes fragmentId: Int,
        bundle: Bundle? = null,
        addToBackStack: Boolean = true
    )

    /**
     * using
     */
    fun navigateWithSharedElement(
        @IdRes fragmentId: Int,
        bundle: Bundle? = null,
        sharedElements: FragmentNavigator.Extras? = null,
        addToBackStack: Boolean = true
    )


    //on button back toolbar
    fun navigateUp()

    fun present(@IdRes fragmentId: Int, bundle: Bundle? = null)

    fun showActivity(activity: Class<*>, bundle: Bundle? = null)
}