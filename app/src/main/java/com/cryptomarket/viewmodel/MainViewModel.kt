package com.cryptomarket.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.cryptomarket.repository.CryptoAssetRepository
import com.cryptomarket.utils.Resource
import kotlinx.coroutines.Dispatchers

class MainViewModel(private val cryptoAssetRepository: CryptoAssetRepository) : ViewModel() {

    fun getCryptoAssetsDetails() = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(cryptoAssetRepository.getCryptoAsserts()))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, exception.message ?: "Something went wrong!"))
        }
    }
}