package com.cryptomarket

import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cryptomarket.adapter.CryptoAssetsAdapter
import com.cryptomarket.model.CryptoAsset
import com.cryptomarket.model.CryptoAssetDetails
import com.cryptomarket.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: CryptoAssetsAdapter
    private val cryptoAssets = ArrayList<CryptoAssetDetails>()
    private lateinit var toolbar: Toolbar
    private lateinit var recyclerView: RecyclerView
    private lateinit var errorText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        toolbar = findViewById(R.id.main_toolbar)
        setSupportActionBar(toolbar)

        errorText = findViewById(R.id.errorText)
        recyclerView = findViewById(R.id.cryptoAssets)
        adapter = CryptoAssetsAdapter(applicationContext, cryptoAssets)
        val layoutManager = GridLayoutManager(applicationContext, 2)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        viewModel.getCryptoAsset()
        val dataObserver = Observer<CryptoAsset> { assets ->
            cryptoAssets.addAll(assets.cryptoData)
            adapter.notifyDataSetChanged()
            errorText.visibility = View.GONE
        }
        val errorObserver = Observer<String> {  error ->
            errorText.text = error
            errorText.visibility = View.VISIBLE
        }

        viewModel.cryptoAssetsData.observe(this, dataObserver)
        viewModel.cryptoAssetsDataError.observe(this, errorObserver)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.action_refresh) {
            viewModel.getCryptoAsset()
        }
        return super.onOptionsItemSelected(item)
    }
}