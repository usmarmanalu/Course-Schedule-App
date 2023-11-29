package com.dicoding.courseschedule.ui.home

import androidx.lifecycle.*
import com.dicoding.courseschedule.data.*
import com.dicoding.courseschedule.util.*

class HomeViewModel(private val repository: DataRepository) : ViewModel() {

    private val _queryType = MutableLiveData<QueryType>()

    init {
        _queryType.value = QueryType.CURRENT_DAY
    }

    fun setQueryType(queryType: QueryType) {
        _queryType.value = queryType
    }

    fun getNearestSchedule() =
        repository.getNearestSchedule(_queryType.value ?: QueryType.CURRENT_DAY)
}
