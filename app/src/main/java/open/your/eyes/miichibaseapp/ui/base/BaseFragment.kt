package open.your.eyes.miichibaseapp.ui.base

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.IdRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.FragmentNavigator
import dagger.android.support.DaggerFragment
import open.your.eyes.miichibaseapp.data.scheduler.ISchedulerProvider
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import open.your.eyes.miichibaseapp.BR
import open.your.eyes.miichibaseapp.R
import open.your.eyes.miichibaseapp.data.DataManager
import open.your.eyes.miichibaseapp.utils.printLog
import javax.inject.Inject


/**
 * Created by OpenYourEyes on 11/26/2019
 */
abstract class BaseFragment<VM : BaseViewModel, DB : ViewDataBinding> :
    DaggerFragment(), IFragmentCallback {
    var TAG = ""

    @Inject
    lateinit var dataManager: DataManager

    @Inject
    lateinit var factory: ViewModelFactory

    @Inject
    lateinit var schedule: ISchedulerProvider
    private val disposable = CompositeDisposable()

    lateinit var activity: Activity
    lateinit var navigators: Navigators
    lateinit var viewModel: VM
    lateinit var dataBinding: DB


    private val mProgressDialog: ProgressDialog by lazy {
        ProgressDialog(activity)
    }
    private var mIsAttached: Boolean = false

    override fun getContext(): Context {
        return activity
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        TAG = javaClass.simpleName
        mIsAttached = true
        if (context is Navigators) {
            activity = context as Activity
            navigators = context
            viewModel = ViewModelProvider(this, factory).get(createViewModel())
            viewModel.navigation = navigators
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return onCreateViewInternal(inflater, container)
    }

    var isFirst = false
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI(view)
        navigators.onFragmentResumed(this)
        trackingViewModel()
        viewModel.transform()
        initView()
        bindViewModel()
    }

    private fun setupUI(view: View) {
        if (view !is EditText) {
            view.setOnTouchListener { _, _ ->
                hideSoftKeyboard()
                false
            }
        }
        if (view is ViewGroup) {
            for (i in 0 until view.childCount) {
                val innerView = view.getChildAt(i)
                setupUI(innerView)
            }
        }
    }

    private fun hideSoftKeyboard() {
        activity.currentFocus?.let {
            val inputMethodManager =
                activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(it.windowToken, 0)
        }
    }

    private fun trackingViewModel() {
        viewModel.showLoading
            .observeOn(schedule.ui)
            .subscribe {
                if (it) {
                    showLoading()
                } else {
                    hideLoading()
                }
            }.addDisposable()

        viewModel.trackingError
            .observeOn(schedule.ui)
            .subscribe {
                Toast.makeText(requireContext(), it.errorMessage, Toast.LENGTH_LONG).show()
            }.addDisposable()
    }

    private fun onCreateViewInternal(inflater: LayoutInflater, container: ViewGroup?): View? {
        dataBinding = DataBindingUtil.inflate(
            inflater,
            getResourceLayout(),
            container,
            false
        )
        dataBinding.apply {
            setVariable(BR.viewModel, viewModel)
            lifecycleOwner = viewLifecycleOwner
        }
        return dataBinding.root
    }

    abstract fun createViewModel(): Class<VM>

    abstract fun getResourceLayout(): Int
    abstract fun initView()
    abstract fun bindViewModel()

    open fun isShowToolbar(): Boolean {
        return true
    }

    open fun isShowBack(): Boolean {
        return true
    }

    override fun showLoading() {
        if (!mProgressDialog.isShowing && userVisibleHint) {
            mProgressDialog.show()
        }

    }

    override fun hideLoading() {
        if (mProgressDialog.isShowing && !isDetached) {
            mProgressDialog.dismiss()
        }
    }

    open fun showActionBar(): Boolean {
        return true
    }

    open fun onNavigationUp() {
        navigators.navigateUp()
    }

    fun replaceFragment(
        @IdRes fragmentId: Int,
        bundle: Bundle? = null,
        addToBackStack: Boolean = true
    ) {
        navigators.navigate(fragmentId, bundle, addToBackStack)
    }

    fun replaceWithSharedElements(
        @IdRes fragmentId: Int,
        bundle: Bundle? = null,
        sharedElements: FragmentNavigator.Extras? = null,
        addToBackStack: Boolean = true
    ) {
        navigators.navigateWithSharedElement(fragmentId, bundle, sharedElements, addToBackStack)
    }


    class ProgressDialog constructor(context: Context) : Dialog(context) {
        init {
            setCancelable(false)
            requestWindowFeature(Window.FEATURE_NO_TITLE)
            setContentView(R.layout.progress_circle)
            val window = window
            if (window != null) {
                getWindow()!!.setBackgroundDrawableResource(android.R.color.transparent)
            }
        }
    }

    fun Disposable.addDisposable() {
        disposable.add(this)
    }

    override fun onDestroyView() {
        super.onDestroyView()
//        dataBinding = null
        viewModel.onDestroyView()
        disposable.clear()

    }

    override fun onDetach() {
        super.onDetach()
        mIsAttached = false
        disposable.dispose()
    }
}