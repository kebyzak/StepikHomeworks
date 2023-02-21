package com.example.stepik

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class CurrencyAdapter(
    private val layoutInflater: LayoutInflater,
    private val onLongClickListener: OnLongClickListener
) : RecyclerView.Adapter<CurrencyViewHolder>() {

    interface OnLongClickListener {
        fun onLongClick(itemView: View, currency: Currency)
    }


    private val currencies: MutableList<Currency> = mutableListOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyViewHolder {
        val view = layoutInflater.inflate(R.layout.item_currency, parent, false)
        return CurrencyViewHolder(view)
    }


    override fun getItemCount(): Int {
        return currencies.size
    }

    fun updateDataSet(newDataSet: List<Currency>) {
        currencies.clear()
        currencies.addAll(newDataSet)
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
        val fromPosition = currencies[from]
        currencies.removeAt(from)
        if (to < from) {
            currencies.add(to, fromPosition)
        } else {
            currencies.add(to - 1, fromPosition)
        }
    }

    fun deleteOnSwipe(position: Int) {
        currencies.removeAt(position)
        notifyItemRemoved(position)
    }

    fun deleteOnLongClick(currency: Currency) {
        currencies.remove(currency)
        notifyItemRemoved(currencies.indexOf(currency))
    }

    fun sortByAlphabet() {
        currencies.sortBy { currency ->
            currency.info
        }
        notifyDataSetChanged()
    }

    fun sortByPrice() {
        currencies.sortBy { currency ->
            currency.amount
        }
        notifyDataSetChanged()
    }

    fun resetSort() {
        currencies.clear()
        notifyDataSetChanged()
    }
}