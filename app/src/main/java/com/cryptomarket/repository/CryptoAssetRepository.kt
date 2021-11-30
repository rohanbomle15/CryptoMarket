package com.cryptomarket.repository

import com.cryptomarket.network.CryptoAssetServiceHelper

class CryptoAssetRepository(private val cryptoAssetServiceHelper: CryptoAssetServiceHelper) {
    suspend fun getCryptoAsserts() = cryptoAssetServiceHelper.getCryptoAssets()
}