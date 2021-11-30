package com.cryptomarket.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.cryptomarket.R
import com.cryptomarket.adapter.CryptoAssetsAdapter.DataViewHolder
import com.cryptomarket.model.Market
import kotlinx.android.synthetic.main.crypto_asset_item.view.name
import kotlinx.android.synthetic.main.crypto_asset_item.view.priceInUSD
import kotlinx.android.synthetic.main.crypto_asset_item.view.changePercent24hr

class CryptoAssetsAdapter(private val assets: ArrayList<Market>):
    RecyclerView.Adapter<DataViewHolder>() {

    class DataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(item: Market) {
            itemView.apply {
                name.text = item.base_asset
                priceInUSD.text =  "$" + String.format("%.3f", item.price)
                changePercent24hr.text = String.format("%.3f", item.change_24h)
                val textColor: Int = if(item.change_24h < 0) {
                    ContextCompat.getColor(context, R.color.design_default_color_error)
                } else {
                    ContextCompat.getColor(context, R.color.design_default_color_secondary_variant)
                }
                changePercent24hr.setTextColor(textColor)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder =
        DataViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.crypto_asset_item, parent, false))

    override fun getItemCount(): Int = assets.size

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        holder.bind(assets[position])
    }

    fun addCryptoAssets(assets: List<Market>) {
        this.assets.apply {
            clear()
            addAll(assets)
        }
    }
}