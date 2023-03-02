package com.cryptomarket.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.cryptomarket.R
import com.cryptomarket.adapter.CryptoAssetsAdapter.DataViewHolder
import com.cryptomarket.databinding.CryptoAssetItemBinding
import com.cryptomarket.model.Market

class CryptoAssetsAdapter(private val assets: ArrayList<Market>):
    RecyclerView.Adapter<DataViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        val binding = CryptoAssetItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DataViewHolder(binding)
    }

    override fun getItemCount(): Int = assets.size

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        holder.bind(assets[position])
    }

    class DataViewHolder(private val binding: CryptoAssetItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Market) {
            with(itemView) {
                binding.name.text = item.base_asset
                binding.priceInUSD.text =  "$" + String.format("%.3f", item.price)
                binding.changePercent24hr.text = String.format("%.3f", item.change_24h)
                val textColor: Int = if(item.change_24h < 0) {
                    ContextCompat.getColor(context, R.color.design_default_color_error)
                } else {
                    ContextCompat.getColor(context, R.color.design_default_color_secondary_variant)
                }
                binding.changePercent24hr.setTextColor(textColor)
            }
        }
    }

    fun addCryptoAssets(assets: List<Market>) {
        this.assets.apply {
            clear()
            addAll(assets)
        }
    }
}