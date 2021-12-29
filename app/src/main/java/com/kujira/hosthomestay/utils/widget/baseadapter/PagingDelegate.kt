package com.kujira.hosthomestay.utils.widget.baseadapter

import io.reactivex.subjects.BehaviorSubject

/**
 * Created by PhucBv on 5/2021
 */
interface PagingDelegate {
    val triggerPaging: BehaviorSubject<PagingType>
    var isEndOf: Boolean
    var limit: Int
    var page: Int
    fun initPaging()
}

enum class PagingType {
    Refresh, LoadMore, None
}
