package com.example.stepik

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    lateinit var adapter: CurrencyAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initRecycler()
        fillListWithData()
    }

    private fun initRecycler(){
        adapter = CurrencyAdapter(layoutInflater)
        val layoutManager = LinearLayoutManager(this)
        val rvCurrencies: RecyclerView = findViewById(R.id.rv_currencies)

        rvCurrencies.layoutManager = layoutManager
        rvCurrencies.adapter = adapter
    }

    private fun fillListWithData() {
        val currencies = mutableListOf<Currency>()
        currencies.add(Currency("20000", R.drawable.img_flag_kz, "Тенге, Казахстан"))
        currencies.add(Currency("", R.drawable.img_flag_usa, "Доллары, США"))
        currencies.add(Currency("20000", R.drawable.img_flag_tr, "Лира, Турция"))
        currencies.add(Currency("", 0, ""))
        adapter.updateDataSet(currencies)
    }
}