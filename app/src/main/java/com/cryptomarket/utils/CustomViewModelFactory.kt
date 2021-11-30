package com.cryptomarket.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.cryptomarket.network.CryptoAssetServiceHelper
import com.cryptomarket.repository.CryptoAssetRepository
import com.cryptomarket.viewmodel.MainViewModel

class CustomViewModelFactory(private val apiHelper: CryptoAssetServiceHelper) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(CryptoAssetRepository(apiHelper)) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }

}