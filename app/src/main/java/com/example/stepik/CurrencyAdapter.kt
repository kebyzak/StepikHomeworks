package com.example.stepik

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
class CurrencyAdapter(private val layoutInflater: LayoutInflater) : RecyclerView.Adapter<CurrencyViewHolder>() {
    private val currencies: MutableList<Currency> = mutableListOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyViewHolder {
        val view = layoutInflater.inflate(R.layout.item_currency, parent, false)
        return CurrencyViewHolder(view)
    }
    override fun getItemCount(): Int {
        return currencies.size
    }
    fun updateDataSet(newDataSet:List<Currency>){
        currencies.clear()
        currencies.addAll(newDataSet)
        notifyDataSetChanged()
    }
    fun addItemToPosition(currency: Currency, position : Int){
        currencies.add(position, currency)
        notifyItemInserted(position)
    }

    fun moveItem(from : Int, to : Int){
        val fromPosition = currencies[from]
        currencies.removeAt(from)
        if(to < from) {
            currencies.add(to, fromPosition)
        } else {
            currencies.add(to-1, fromPosition)
        }
    }
    fun deleteOnSwipe(position: Int){
        currencies.removeAt(position)
    }
    override fun onBindViewHolder(holder: CurrencyViewHolder, position: Int) {
        holder.bind(currencies[position])
    }
}