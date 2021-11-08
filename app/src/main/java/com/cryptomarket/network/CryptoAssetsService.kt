package com.cryptomarket.network

import com.cryptomarket.model.CryptoAsset
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

private const val BASE_URL = "https://api.coincap.io/v2/"

private val retrofit = Retrofit.Builder()
    .addConverterFactory(GsonConverterFactory.create())
    .baseUrl(BASE_URL)
    .build()

interface CryptoAssetsService {
    @GET("assets")
    fun getAssetInfo(): Call<CryptoAsset>
}

object CryptoApi {
    val retrofitService: CryptoAssetsService by lazy {
        retrofit.create(CryptoAssetsService::class.java)
    }
}