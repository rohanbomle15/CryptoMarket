package com.cryptomarket.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cryptomarket.model.CryptoAsset
import com.cryptomarket.network.CryptoApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {

    private val TAG = "MainViewModel"
    var cryptoAssetsData = MutableLiveData<CryptoAsset>()
    var cryptoAssetsDataError = MutableLiveData<String>()

    fun getCryptoAsset() {
        val api = CryptoApi.retrofitService.getAssetInfo()

        api.enqueue(object: Callback<CryptoAsset> {
            override fun onResponse(call: Call<CryptoAsset>, response: Response<CryptoAsset>) {
                if(response.body() == null) {
                    cryptoAssetsDataError.value = "Something went wrong! \nPlease try again"
                } else {
                    cryptoAssetsData.value = response.body()
                }
            }

            override fun onFailure(call: Call<CryptoAsset>, t: Throwable) {
                cryptoAssetsDataError.value = "Unable to fetch data! \nPlease try again"
                Log.d(TAG, "Api Failure :" + t.message)
            }
        })
    }
}