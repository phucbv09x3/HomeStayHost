package com.kujira.hosthomestay.ui.list

import android.os.Handler
import androidx.lifecycle.viewModelScope
import io.reactivex.subjects.BehaviorSubject
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import com.kujira.hosthomestay.data.model.GitResponse
import com.kujira.hosthomestay.data.model.RepoModel
import com.kujira.hosthomestay.ui.base.BaseViewModel
import com.kujira.hosthomestay.utils.*
import com.kujira.hosthomestay.utils.widget.baseadapter.PagingDelegate
import com.kujira.hosthomestay.utils.widget.baseadapter.PagingType
import java.sql.Time
import java.util.concurrent.TimeUnit

/**
 * Created by OpenYourEyes on 11/2/20
 */
class ListViewModel : BaseViewModel(), PagingDelegate {
    override var triggerPaging = BehaviorSubject.createDefault(PagingType.None)
    override var isEndOf: Boolean = false
    override var limit: Int = 50
    override var page: Int = 0
    override fun initPaging() {
        triggerPaging.filter {
            it != PagingType.None
        }.subscribe {
            //getListRepo()
        }.addDisposable()
    }


    val listRepo = ListLiveData<RepoModel>()
    private val _listRepo = listRepo


    var job: Job? = null
    fun listRepoHasValue(): Boolean {
        return _listRepo.hasValue()
    }

//    private fun getListRepo() {
//        val isRefresh = triggerPaging.value == PagingType.Refresh
//        printLog("getListRepo ${triggerPaging.value}")
//        job = viewModelScope.launch {
//            executeRequestTest(request = { getRepoGit() }, isLoading = isRefresh)
//                .onEach { delay(1000) }
//                .map { it.items }
//                .collect { newItems ->
//                    page++
//                    if (isRefresh) {
//                        _listRepo.postValue(newItems)
//                    } else {
//                        val listItems = _listRepo.value ?: mutableListOf()
//                        listItems.addAll(newItems)
//                        _listRepo.postValue(listItems)
//                    }
//                }
//
//        }
//    }

    fun onRefresh() {
        page = 0
        isEndOf = false
        triggerPaging.onNext(PagingType.Refresh)
    }


}