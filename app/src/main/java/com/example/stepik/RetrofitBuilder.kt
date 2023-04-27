package com.example.stepik

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitBuilder {
    val apiService: APIService =
        Retrofit.Builder().baseUrl("https://api.apilayer.com/currency_data/")
            .addConverterFactory(GsonConverterFactory.create()).build()
            .create(APIService::class.java)
}
