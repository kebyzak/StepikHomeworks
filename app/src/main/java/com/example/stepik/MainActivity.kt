package com.example.stepik

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    lateinit var adapter: CurrencyAdapter
    lateinit var rvCurrency: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initRecycler()
        fillListWithData()
        initAddButton()
    }

    private fun initRecycler() {
        adapter = CurrencyAdapter(layoutInflater)
        val layoutManager = LinearLayoutManager(this)
        rvCurrency = findViewById(R.id.rv_currencies)

        rvCurrency.adapter = adapter
        rvCurrency.layoutManager = layoutManager
    }

    private fun fillListWithData() {
        val currencies = mutableListOf<Currency>()
        currencies.add(Currency("20000", R.drawable.img_flag_kz, "Тенге, Казахстан"))
        currencies.add(Currency("", R.drawable.img_flag_usa, "Доллары, США"))
        currencies.add(Currency("100", R.drawable.img_flag_tr, "Лира, Турция"))
        currencies.add(Currency("100", R.drawable.img_flag_eu, "Евро, ЕС"))
        currencies.add(Currency("", 0, ""))
        adapter.updateDataSet(currencies)
    }

    private fun initAddButton() {
        val btnAdd: Button = findViewById(R.id.btn_add)
        btnAdd.setOnClickListener {
            val currency = Currency("100", R.drawable.img_flag_kz, "Тенге, Казахстан")
            val position = 0
            adapter.addItemToPosition(currency = currency, position = position)

            val smoothScroller = object : LinearSmoothScroller(this) {
                override fun getVerticalSnapPreference(): Int = LinearSmoothScroller.SNAP_TO_START
            }
            smoothScroller.targetPosition = position
            rvCurrency.layoutManager?.startSmoothScroll(smoothScroller)
        }
    }
}