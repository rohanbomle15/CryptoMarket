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
import androidx.recyclerview.widget.LinearLayoutManager
import com.cryptomarket.R
import com.cryptomarket.adapter.CryptoAssetsAdapter
import com.cryptomarket.model.Market
import com.cryptomarket.network.CryptoAssetRetrofitBuilder
import com.cryptomarket.network.CryptoAssetServiceHelper
import com.cryptomarket.utils.CustomViewModelFactory
import com.cryptomarket.utils.Status
import com.cryptomarket.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.main_activity.progressBar
import kotlinx.android.synthetic.main.main_activity.recyclerView
import kotlinx.android.synthetic.main.main_activity.errorText
import kotlinx.android.synthetic.main.main_activity.main_toolbar

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: CryptoAssetsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        setSupportActionBar(main_toolbar)
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

    private fun setupViewModel() {
        viewModel = ViewModelProvider(this,
            CustomViewModelFactory(CryptoAssetServiceHelper(CryptoAssetRetrofitBuilder.apiService)
            ))[MainViewModel::class.java]
    }

    private fun setupUI() {
        recyclerView.layoutManager = GridLayoutManager(this, 2)
        adapter = CryptoAssetsAdapter(arrayListOf())
        recyclerView.addItemDecoration(
            DividerItemDecoration(
                recyclerView.context,
                (recyclerView.layoutManager as GridLayoutManager).orientation
            )
        )
        recyclerView.adapter = adapter
    }

    private fun setupObservers() {
        viewModel.getCryptoAssetsDetails().observe(this, Observer {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        recyclerView.visibility = View.VISIBLE
                        progressBar.visibility = View.GONE
                        errorText.visibility = View.GONE
                        resource.data?.let { market ->
                            retrieveList(market.markets)
                        }
                    }
                    Status.ERROR -> {
                        recyclerView.visibility = View.VISIBLE
                        progressBar.visibility = View.GONE
                        errorText.visibility = View.VISIBLE
                        Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                    }
                    Status.LOADING -> {
                        progressBar.visibility = View.VISIBLE
                        recyclerView.visibility = View.GONE
                        errorText.visibility = View.GONE
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