package com.example.stepik

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Button
import android.widget.Toast
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ItemTouchHelper.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    lateinit var adapter: CurrencyAdapter
    lateinit var rvCurrency: RecyclerView

    private val itemTouchHelper by lazy {
        ItemTouchHelper(object : SimpleCallback(UP or DOWN, LEFT or RIGHT) {

            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                val from = viewHolder.adapterPosition
                val to = target.adapterPosition
                adapter.moveItem(from, to)
                adapter.notifyItemMoved(from, to)
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val myPosition = viewHolder.adapterPosition
                adapter.deleteOnSwipe(myPosition)
                adapter.notifyItemRemoved(myPosition)
            }
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initRecycler()
        fillListWithData()
        initAddButton()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.sort_by_alphabet -> {
                sortByAlphabet()
                item.isChecked = true
                true
            }
            R.id.sort_by_price -> {
                sortByPrice()
                item.isChecked = true
                true
            }
            R.id.reset_sort -> {
                resetSort()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun sortByAlphabet() {
        adapter.sortByAlphabet()
    }

    private fun sortByPrice() {
        adapter.sortByPrice()
    }

    private fun resetSort() {
        adapter.resetSort()
        fillListWithData()
    }

    private fun initRecycler() {
        adapter = CurrencyAdapter(layoutInflater)
        val layoutManager = LinearLayoutManager(this)
        rvCurrency = findViewById(R.id.rv_currencies)

        rvCurrency.adapter = adapter
        rvCurrency.layoutManager = layoutManager
        itemTouchHelper.attachToRecyclerView(rvCurrency)
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