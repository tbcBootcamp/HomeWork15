package com.example.homework15.network

import com.example.homework15.data.ItemModel
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

object RetrofitClient {
    val moshi = Moshi.Builder()
        .addLast(KotlinJsonAdapterFactory())
        .build()

    val retrofit = Retrofit.Builder()
        .baseUrl("https://mocki.io/")
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()

    val apiService = retrofit.create(ApiService::class.java)
}

interface ApiService {

    @GET("v1/663e7ca2-a7f1-4cf4-963a-4981a1b9db5f")
    suspend fun getItemModels(): List<ItemModel>
}