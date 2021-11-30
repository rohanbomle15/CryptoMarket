package com.cryptomarket

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.cryptomarket.model.Markets
import com.cryptomarket.viewmodel.MainViewModel
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.ArgumentCaptor
import org.mockito.Mock
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import java.net.HttpURLConnection


@RunWith(MockitoJUnitRunner::class)
class MainViewModelTest {

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    private lateinit var mainViewModel: MainViewModel
    private lateinit var mockWebServer: MockWebServer

    @Mock
    private lateinit var cryptoAssetsDataObserver: Observer<Markets>

    @Mock
    private lateinit var cryptoAssetsErrorObserver: Observer<String>

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        mainViewModel = MainViewModel()
        mainViewModel.cryptoAssetsData.observeForever(cryptoAssetsDataObserver)
        mainViewModel.cryptoAssetsDataError.observeForever(cryptoAssetsErrorObserver)
        mockWebServer = MockWebServer()
        mockWebServer.start()
    }

    @Test
    fun testGetAssetInfo(){
        // Assign
        val response = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody(getCryptoMarketDataJson)
        mockWebServer.enqueue(response)

        // Act
        mainViewModel.getCryptoAsset()
        mockWebServer.takeRequest()

        // Assert
        val captor = ArgumentCaptor.forClass(Markets::class.java)
        captor.run {
            verify(cryptoAssetsDataObserver, times(1)).onChanged(capture())
        }
    }

    @Test
    fun testGetAssetInfoError(){
        // Assign
        val response = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody("")
        mockWebServer.enqueue(response)

        // Act
        mainViewModel.getCryptoAsset()
        mockWebServer.takeRequest()

        // Assert
        val captor = ArgumentCaptor.forClass(Markets::class.java)
        captor.run {
            verify(cryptoAssetsErrorObserver, times(1)).onChanged(capture().toString())
        }
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
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