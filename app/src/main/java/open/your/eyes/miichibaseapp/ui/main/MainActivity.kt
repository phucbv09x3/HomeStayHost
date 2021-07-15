package open.your.eyes.miichibaseapp.ui.main

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavOptions
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import kotlinx.android.synthetic.main.activity_main.*
import open.your.eyes.miichibaseapp.R
import open.your.eyes.miichibaseapp.databinding.ActivityMainBinding
import open.your.eyes.miichibaseapp.ui.base.BaseActivity
import open.your.eyes.miichibaseapp.ui.base.BaseFragment
import open.your.eyes.miichibaseapp.utils.printLog


open class MainActivity : BaseActivity<MainViewModel, ActivityMainBinding>(),
    NavController.OnDestinationChangedListener {
    private lateinit var navController: NavController

    private lateinit var currentFragment: BaseFragment<*, *>
    private var currentFragmentId: Int = R.id.loginFragment
    private val navHostFragment: NavHostFragment by lazy {
        supportFragmentManager.findFragmentById(R.id.main_nav_fragment) as NavHostFragment
    }

    override fun onSupportNavigateUp(): Boolean {
        currentFragment.onNavigationUp()
        return false
    }

    override fun createViewModel(): Class<MainViewModel> {
        return MainViewModel::class.java
    }

    override fun getContentView(): Int {
        return R.layout.activity_main
    }

    override fun initAction() {
    }

    override fun initData() {
        navController = navHostFragment.navController
        setSupportActionBar(toolbar)
        navController.addOnDestinationChangedListener(this)
        NavigationUI.setupActionBarWithNavController(this, navController)
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