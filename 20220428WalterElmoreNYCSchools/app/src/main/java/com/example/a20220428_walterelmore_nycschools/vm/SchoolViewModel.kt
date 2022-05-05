package com.example.a20220428_walterelmore_nycschools.vm

import androidx.lifecycle.ViewModel
import com.example.a20220428_walterelmore_nycschools.data.api.ApiRepoImpl
import com.example.a20220428_walterelmore_nycschools.data.model.UIState
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectIndexed
import kotlinx.coroutines.launch


class SchoolViewModel(
    private  val repository: ApiRepoImpl,
    private val coroutineDispatcher: CoroutineDispatcher = Dispatchers.IO
    ): ViewModel() {

    private val _schoolData = MutableLiveData<UIState<*>>()
    val schoolData : LiveData<UIState<*>>
        get() = _schoolData

    private val _satData = MutableLiveData<UIState<*>>()
    val satData: LiveData<UIState<*>>
        get() = _satData

    init {
        getSchools()
    }

    fun getSchools() {
        CoroutineScope(coroutineDispatcher).launch {
            _schoolData.postValue(UIState.Loading)
            repository.fetchSchools()
                .catch {
                    _schoolData.postValue(UIState.Error("Failed to retrive schools"))
                }
                .collect {
                    _schoolData.postValue(it)
                }
        }
    }

    fun getScores() {
        CoroutineScope(Dispatchers.IO).launch {
            _satData.postValue(UIState.Loading)
            repository.fetchSatScores()
                .catch {
                    _satData.postValue(UIState.Error("Faild to retrive SAT scores"))
                }
                .collect{
                    _satData.postValue(it)
                }
        }
    }
}