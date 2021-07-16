package open.hosthomestay.ui.base

import android.content.Intent
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import dagger.android.support.DaggerAppCompatActivity
import open.hosthomestay.BR
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import open.hosthomestay.data.DataManager
import open.hosthomestay.data.scheduler.ISchedulerProvider
import open.hosthomestay.utils.printLog
import javax.inject.Inject

/**
 * Created by OpenYourEyes on 9/18/2020
 */
abstract class BaseActivity<VM : BaseViewModel, DB : ViewDataBinding> :
    DaggerAppCompatActivity(), Navigators {

    @Inject
    lateinit var factory: ViewModelFactory

    @Inject
    lateinit var dataManager: DataManager

    @Inject
    lateinit var schedulerProvider: ISchedulerProvider

    private val mDisposable: CompositeDisposable by lazy {
        CompositeDisposable()
    }
    private val mProgressDialog: BaseFragment.ProgressDialog by lazy {
        BaseFragment.ProgressDialog(this)
    }

    abstract fun createViewModel(): Class<VM>

    abstract fun getContentView(): Int

    abstract fun initAction()

    abstract fun initData()

    lateinit var mViewModel: VM
    lateinit var mDataBinding: DB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mDataBinding = DataBindingUtil.setContentView(this, getContentView());
        mViewModel = ViewModelProvider(this, factory).get(createViewModel())
        mDataBinding.setVariable(BR.viewModel, mViewModel)
        trackingProgress()
        initAction()
        initData()

    }

    private fun trackingProgress() {
        mViewModel.showLoading
            .subscribeOn(schedulerProvider.io)
            .observeOn(schedulerProvider.ui)
            .subscribe {
                if (it) {
                    showLoading()
                } else {
                    hideLoading()
                }
            }.addDisposable()
    }

    override fun showActivity(activity: Class<*>, bundle: Bundle?) {
        val intent = Intent(this, activity)
        intent.putExtras(bundle ?: Bundle())
        startActivity(intent)
    }

    private fun showLoading() {
        if (!mProgressDialog.isShowing && !isFinishing) {
            printLog("showLoading")
            mProgressDialog.show()
        }
    }

    private fun hideLoading() {
        if (mProgressDialog.isShowing && !isFinishing) {
            printLog("hideProgress")
            mProgressDialog.dismiss()
        }
    }

    fun Disposable.addDisposable() {
        mDisposable.add(this)
    }

}