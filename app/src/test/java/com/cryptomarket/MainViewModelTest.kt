package com.cryptomarket

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.cryptomarket.model.CryptoAsset
import com.cryptomarket.model.CryptoAssetDetails
import com.cryptomarket.viewmodel.MainViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule


@ExperimentalCoroutinesApi
class MainViewModelTest {

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var mainViewModel: MainViewModel


    @Before
    fun setUp() {
        mainViewModel = MainViewModel()
    }

    @Test
    fun testGetAssetInfo(){
        // Assign

        // Act
        mainViewModel.getCryptoAsset()

        // Assert
        mainViewModel.cryptoAssetsData.observeForTesting {
            val cryptoAsset: CryptoAsset? = mainViewModel.cryptoAssetsData.value
            assertNotNull(cryptoAsset)
        }
    }

    @After
    fun tearDown() {

    }
}

const val getCryptoMarketDataJson = """
{
  "data": [
    {
      "id": "bitcoin",
      "rank": "1",
      "symbol": "BTC",
      "name": "Bitcoin",
      "supply": "17193925.0000000000000000",
      "maxSupply": "21000000.0000000000000000",
      "marketCapUsd": "119150835874.4699281625807300",
      "volumeUsd24Hr": "2927959461.1750323310959460",
      "priceUsd": "6929.8217756835584756",
      "changePercent24Hr": "-0.8101417214350335",
      "vwap24Hr": "7175.0663247679233209"
    },
    {
      "id": "ethereum",
      "rank": "2",
      "symbol": "ETH",
      "name": "Ethereum",
      "supply": "101160540.0000000000000000",
      "maxSupply": null,
      "marketCapUsd": "40967739219.6612727047843840",
      "volumeUsd24Hr": "1026669440.6451482672850841",
      "priceUsd": "404.9774667045200896",
      "changePercent24Hr": "-0.0999626159535347",
      "vwap24Hr": "415.3288028454417241"
    }
  ]
}
"""