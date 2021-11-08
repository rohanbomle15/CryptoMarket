package com.cryptomarket.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.cryptomarket.R
import com.cryptomarket.model.CryptoAssetDetails

class CryptoAssetsAdapter(private val context: Context, private val assets: List<CryptoAssetDetails>):
    RecyclerView.Adapter<CryptoAssetsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.crypto_asset_item, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = assets[position]
        holder.nameTextView.text = item.name
        holder.priceInUSD.text =  "$" + String.format("%.3f", item.priceUsd)
        holder.changePercent24hr.text = String.format("%.3f", item.changePercent24Hr)
        val textColor: Int = if(item.changePercent24Hr < 0) {
            ContextCompat.getColor(context, R.color.design_default_color_error)
        } else {
            ContextCompat.getColor(context, R.color.design_default_color_secondary_variant)
        }
        holder.changePercent24hr.setTextColor(textColor)
    }

    override fun getItemCount(): Int = assets.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var nameTextView: TextView = view.findViewById(R.id.name)
        var priceInUSD: TextView = view.findViewById(R.id.priceInUSD)
        var changePercent24hr: TextView = view.findViewById(R.id.changePercent24hr)
    }
}