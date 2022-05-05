package com.example.a20220428_walterelmore_nycschools.data.api

import com.example.a20220428_walterelmore_nycschools.data.model.NYCSchool
import com.example.a20220428_walterelmore_nycschools.data.model.SatScore
import com.example.a20220428_walterelmore_nycschools.util.RESOURCE
import com.example.a20220428_walterelmore_nycschools.util.SAT_SCORES
import com.example.a20220428_walterelmore_nycschools.util.SCHOOLS
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    @GET("$RESOURCE$SCHOOLS")
    suspend fun  fetchSchools(): Response<List<NYCSchool>>

    @GET("$RESOURCE$SAT_SCORES")
    suspend fun fetchSatScores(): Response<List<SatScore>>
}