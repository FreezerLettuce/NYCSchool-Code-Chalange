package com.example.a20220428_walterelmore_nycschools.data.api

import com.example.a20220428_walterelmore_nycschools.data.model.UIState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*

interface ApiRepo {
    suspend fun fetchSchools(): Flow<UIState<*>>
    suspend fun fetchSatScores(): Flow<UIState<*>>
}

class ApiRepoImpl(private val service: ApiService): ApiRepo{

    override suspend fun fetchSchools() = flow {
        emit(UIState.Loading)

        try {
            val data = service.fetchSchools()
            if (data.isSuccessful) {
                data.body()?.let {
                    emit(UIState.Success(it))
                } ?: emit(UIState.Error("Empty school response"))
            } else {
               emit(UIState.Error("Failure school response"))
            }
        } catch (e: Exception) {
            emit(UIState.Error(e.localizedMessage))
        }
    }.conflate()

    override suspend fun fetchSatScores() = flow{
        emit(UIState.Loading)

        try {
            val data = service.fetchSatScores()
            if (data.isSuccessful){
                data.body()?.let{
                    emit(UIState.Success(it))
                } ?: emit(UIState.Error("Empty score response"))
            } else{
                emit(UIState.Error("Failure score response"))
            }
        } catch (e: Exception){
            emit(UIState.Error(e.localizedMessage))
        }
    }.conflate()
}