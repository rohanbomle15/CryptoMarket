package com.cryptomarket.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import com.cryptomarket.R
import com.cryptomarket.adapter.CryptoAssetsAdapter
import com.cryptomarket.databinding.MainActivityBinding
import com.cryptomarket.model.Market
import com.cryptomarket.network.CryptoAssetRetrofitBuilder
import com.cryptomarket.network.CryptoAssetServiceHelper
import com.cryptomarket.repository.CryptoAssetRepository
import com.cryptomarket.utils.Status
import com.cryptomarket.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: CryptoAssetsAdapter
    private var binding: MainActivityBinding? = null

    private fun requireBinding() = requireNotNull(binding)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MainActivityBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        setSupportActionBar(binding?.mainToolbar)
        setupViewModel()
        setupUI()
        setupObservers()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.action_refresh) {
            setupObservers()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

    private fun setupViewModel() {
//        viewModel = ViewModelProvider(this,
//            CustomViewModelFactory(CryptoAssetServiceHelper(CryptoAssetRetrofitBuilder.apiService)
//            ))[MainViewModel::class.java]

        val cryptoAssetRepository = CryptoAssetRepository(CryptoAssetServiceHelper(
            CryptoAssetRetrofitBuilder.apiService))
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        viewModel.init(cryptoAssetRepository)
    }

    private fun setupUI() {
        val binding = requireBinding()
        binding.recyclerView.layoutManager = GridLayoutManager(this, 2)
        adapter = CryptoAssetsAdapter(arrayListOf())
        binding.recyclerView.addItemDecoration(
            DividerItemDecoration(
                binding.recyclerView.context,
                (binding.recyclerView.layoutManager as GridLayoutManager).orientation
            )
        )
        binding.recyclerView.adapter = adapter
    }

    private fun setupObservers() {
        val binding = requireBinding()
        viewModel.getCryptoAssetsDetails().observe(this, Observer {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        binding.recyclerView.visibility = View.VISIBLE
                        binding.progressBar.visibility = View.GONE
                        binding.errorText.visibility = View.GONE
                        resource.data?.let { market ->
                            retrieveList(market.markets)
                        }
                    }
                    Status.ERROR -> {
                        binding.recyclerView.visibility = View.VISIBLE
                        binding.progressBar.visibility = View.GONE
                        binding.errorText.visibility = View.VISIBLE
                        Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                    }
                    Status.LOADING -> {
                        binding.progressBar.visibility = View.VISIBLE
                        binding.recyclerView.visibility = View.GONE
                        binding.errorText.visibility = View.GONE
                    }
                }
            }
        })
    }

    private fun retrieveList(markets: List<Market>) {
        adapter.apply {
            addCryptoAssets(markets)
            notifyDataSetChanged()
        }
    }
}