package com.kujira.hosthomestay.ui.main

import android.content.pm.PackageManager
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavOptions
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.NavHostFragment
import com.kujira.hosthomestay.R
import com.kujira.hosthomestay.databinding.ActivityHostMainBinding

import com.kujira.hosthomestay.ui.add.AddRoomFragment
import com.kujira.hosthomestay.ui.base.BaseActivity
import com.kujira.hosthomestay.ui.base.BaseFragment
import com.kujira.hosthomestay.ui.manager.ManagerAccFragment
import com.kujira.hosthomestay.ui.myacc.MyAccountFragment
import com.kujira.hosthomestay.utils.printLog

open class MainHostActivity : BaseActivity<MainHostViewModel, ActivityHostMainBinding>(),
    NavController.OnDestinationChangedListener {
    private lateinit var navController: NavController

    private lateinit var currentFragment: BaseFragment<*, *>
    private var currentFragmentId: Int = R.id.managerRoomFragment
    private val navHostFragment: NavHostFragment by lazy {
        supportFragmentManager.findFragmentById(R.id.main_nav_fragment) as NavHostFragment
    }

    override fun onSupportNavigateUp(): Boolean {
        currentFragment.onNavigationUp()
        return false
    }

    override fun createViewModel(): Class<MainHostViewModel> {
        return MainHostViewModel::class.java
    }

    override fun getContentView(): Int {
        return R.layout.activity_host_main
    }

    override fun initAction() {
    }


    override fun initData() {
        navController = navHostFragment.navController
        //setSupportActionBar(toolbar)
        window.statusBarColor = ContextCompat.getColor(this, R.color.colorWhite)
        navController.addOnDestinationChangedListener(this)
//        NavigationUI.setupActionBarWithNavController(this, navController)
        listenerAction()
        requestPermissionCamera()

    }

    private fun requestPermissionCamera() = if (
        ContextCompat.checkSelfPermission(
            this, android.Manifest.permission.CAMERA
        ) != PackageManager.PERMISSION_GRANTED
        && ContextCompat.checkSelfPermission(
            this,
            android.Manifest.permission.RECORD_AUDIO
        )
        != PackageManager.PERMISSION_GRANTED
    ) {

        ActivityCompat.requestPermissions(
            this,
            arrayOf(
                android.Manifest.permission.CAMERA,
                android.Manifest.permission.RECORD_AUDIO,
            ), 1
        )
    } else {
        // startLocalStream()
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == 1) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //startLocalStream()
            } else {
                requestPermissionCamera()
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    private fun listenerAction() {
        mViewModel.onClickMain.observe(this, {
            when (it) {
//                MainViewModel.BTN_MESSAGE -> {
//                    if (currentFragment is MessageFragment) {
//
//                    } else {
//                        navigate(R.id.messageFragment)
//                    }
//
//                }
                MainHostViewModel.BTN_MANAGER_ROOM -> {
                    if (currentFragment is ManagerAccFragment) {

                    } else {
                        navigate(R.id.managerRoomFragment,null, false)
                    }

                }

                MainHostViewModel.BTN_ADD_ROOM -> {
                    if (currentFragment is AddRoomFragment) {

                    } else {
                        navigate(R.id.addRoomFragment,null, false)
                    }

                }
                MainHostViewModel.BTN_MY_ACC -> {
                    if (currentFragment is MyAccountFragment) {

                    } else {
                        navigate(R.id.myAccountFragment,null, false)
                    }

                }

            }
        })
    }

    override fun navigateUp() {
        val isFinish = !navController.popBackStack()
        if (isFinish) {
            finish()
        }
    }

    override fun onFragmentResumed(fragment: BaseFragment<*, *>) {
        currentFragment = fragment
        printLog("currentFragment: ${fragment::class.simpleName}")
    }

    private fun getNavOptionsNavigate(): NavOptions.Builder? {
        return NavOptions.Builder()
            .setEnterAnim(R.anim.slide_in)
            .setExitAnim(R.anim.fade_out)
            .setPopEnterAnim(R.anim.fade_in)
            .setPopExitAnim(R.anim.slide_out)
    }

    override fun navigate(
        fragmentId: Int,
        bundle: Bundle?,
        addToBackStack: Boolean
    ) {
        val navOptions = getNavOptionsNavigate()
        if (!addToBackStack) {
            navOptions?.setPopUpTo(currentFragmentId, true)
        }
        navController.navigate(fragmentId, bundle, navOptions?.build())
        currentFragmentId = fragmentId
    }


    override fun navigateWithSharedElement(
        fragmentId: Int,
        bundle: Bundle?,
        sharedElements: FragmentNavigator.Extras?,
        addToBackStack: Boolean
    ) {

        val navOptions = getNavOptionsNavigate()
        if (!addToBackStack) {
            navOptions?.setPopUpTo(currentFragmentId, true)
        }
        navController.navigate(fragmentId, bundle, null, sharedElements)
    }

    override fun present(fragmentId: Int, bundle: Bundle?) {
        currentFragmentId = fragmentId
    }

    override fun onDestinationChanged(
        controller: NavController,
        destination: NavDestination,
        arguments: Bundle?
    ) {
        val currentFragment = navHostFragment.childFragmentManager.fragments.firstOrNull()


    }
}