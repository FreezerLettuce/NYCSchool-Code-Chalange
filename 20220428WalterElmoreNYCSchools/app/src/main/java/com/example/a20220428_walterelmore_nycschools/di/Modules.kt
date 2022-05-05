package com.example.a20220428_walterelmore_nycschools.di

import com.example.a20220428_walterelmore_nycschools.data.api.ApiRepoImpl
import com.example.a20220428_walterelmore_nycschools.data.api.ApiService
import com.example.a20220428_walterelmore_nycschools.util.BASE_URL
import com.example.a20220428_walterelmore_nycschools.vm.SchoolViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


val viewModelModule = module{
    viewModel {
        SchoolViewModel(get())
    }
}

val repositoryModule = module{
    single{
        ApiRepoImpl(get())
    }
}

val apiModule = module {
    single {
        provideApiService(get())
    }
}

val retrofitModule = module {
    single {
        provideRetrofit()
    }
}

fun provideApiService(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)
fun provideRetrofit(): Retrofit =
    Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(
            OkHttpClient.Builder()
                .addInterceptor(HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                })
                .build()
        )
        .build()
