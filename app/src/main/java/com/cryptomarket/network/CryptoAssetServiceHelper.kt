package com.cryptomarket.network

class CryptoAssetServiceHelper(private val cryptoAssetsService: CryptoAssetsService) {

    suspend fun getCryptoAssets() = cryptoAssetsService.getCryptoAssets()
}