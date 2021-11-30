package com.cryptomarket.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object CryptoAssetRetrofitBuilder {

    private const val BASE_URL = "https://www.cryptingup.com/api/"

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
    }

    val apiService: CryptoAssetsService = getRetrofit().create(CryptoAssetsService::class.java)
}