package com.example.stepik

import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CurrencyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val etAmount: EditText = itemView.findViewById(R.id.et_amount)
    private val ivFlag: ImageView = itemView.findViewById(R.id.iv_flag)
    private val tvInfo: TextView = itemView.findViewById(R.id.tv_info)

    fun bind(currency: Currency, listener: CurrencyAdapter.OnLongClickListener) {
        etAmount.setText(currency.amount)
        ivFlag.setImageResource(currency.flag)
        tvInfo.text = currency.info

        ivFlag.setOnLongClickListener { flag ->
            listener.onLongClick(flag, currency)
            return@setOnLongClickListener true
        }

        tvInfo.setOnLongClickListener { info ->
            listener.onLongClick(info, currency)
            return@setOnLongClickListener true

        }
    }
}