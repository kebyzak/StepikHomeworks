package com.example.stepik.converter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.stepik.R

class CurrencyAdapter(
    private val layoutInflater: LayoutInflater,
    private val onLongClickListener: (View, Currency) -> Unit
) : RecyclerView.Adapter<CurrencyViewHolder>() {

    private val currencies: MutableList<Currency> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyViewHolder =
        CurrencyViewHolder(layoutInflater.inflate(R.layout.item_currency, parent, false))

    override fun getItemCount() = currencies.size

    fun updateDataSet(newDataSet: List<Currency>) {
        currencies.apply {
            clear()
            addAll(newDataSet)
        }
        notifyDataSetChanged()
    }

    fun addItemToPosition(currency: Currency, position: Int) {
        currencies.add(position, currency)
        notifyItemInserted(position)
    }

    override fun onBindViewHolder(holder: CurrencyViewHolder, position: Int) {
        holder.bind(currency = currencies[position], onLongClickListener)
    }

    fun moveItem(from: Int, to: Int) {
        currencies.apply {
            add(if (to < from) to else to - 1, removeAt(from))
        }
    }

    fun deleteOnSwipe(position: Int) {
        currencies.removeAt(position)
        notifyItemRemoved(position)
    }

    fun deleteOnLongClick(currency: Currency) {
        val position = currencies.indexOf(currency)
        currencies.remove(currency)
        notifyItemRemoved(position)
    }

    fun sortByAlphabet() {
        currencies.sortBy { it.info }
        notifyDataSetChanged()
    }

    fun sortByPrice() {
        currencies.sortBy { it.amount }
        notifyDataSetChanged()
    }

    fun resetSort() {
        currencies.clear()
        notifyDataSetChanged()
    }
}