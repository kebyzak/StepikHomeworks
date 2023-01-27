package com.example.stepik

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder

private const val ITEM_VIEW_TYPE_DEFAULT = 0
private const val ITEM_VIEW_TYPE_ADD = 1

class CurrencyAdapter(private val layoutInflater: LayoutInflater) : RecyclerView.Adapter<ViewHolder>() {
    private val currencies: MutableList<Currency> = mutableListOf()

    override fun getItemViewType(position: Int): Int {
        return if (position + 1 == currencies.size){
            ITEM_VIEW_TYPE_ADD
        } else {
            ITEM_VIEW_TYPE_DEFAULT
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutId = if(viewType == ITEM_VIEW_TYPE_ADD){
            R.layout.item_add_currency
        } else {
            R.layout.item_currency
        }
        val view = layoutInflater.inflate(layoutId, parent, false)
        val viewHolder = if(viewType == ITEM_VIEW_TYPE_ADD){
            ButtonAddViewHolder(view)
        } else {
            CurrencyViewHolder(view)
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currency = currencies[position]
        if(holder is CurrencyViewHolder){
            holder.bind(currency)
        }
    }

    override fun getItemCount(): Int {
        return currencies.size
    }

    public fun updateDataSet(newDataSet:List<Currency>){
        currencies.clear()
        currencies.addAll(newDataSet)
        notifyDataSetChanged()
    }

}