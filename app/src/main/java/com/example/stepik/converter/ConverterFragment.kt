package com.example.stepik.converter

import android.icu.lang.UCharacter.IndicPositionalCategory.LEFT
import android.icu.lang.UCharacter.IndicPositionalCategory.RIGHT
import android.os.Bundle
import android.view.*
import android.widget.Button
import androidx.core.app.ActivityCompat.invalidateOptionsMenu
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ItemTouchHelper.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.RecyclerView
import com.example.stepik.API_KEY
import com.example.stepik.DeleteDialogFragment
import com.example.stepik.R
import com.example.stepik.RetrofitBuilder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import com.google.android.material.textfield.TextInputEditText

class ConverterFragment : Fragment(), CurrBottomSheet.BottomSheetListener {

    private lateinit var showBottomSheetBtn: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_converter, container, false)
        showBottomSheetBtn = view.findViewById(R.id.btn_open_bs)
        return view
    }

    private lateinit var adapter: CurrencyAdapter
    private lateinit var rvCurrency: RecyclerView
    private lateinit var toolbar: androidx.appcompat.widget.Toolbar
    private var isDeleteCalled: Boolean = false
    private lateinit var itDelete: Currency
    private var currentProp: MutableList<Double> = mutableListOf()
    private var isDataAvailable = false

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
                val currPosition = viewHolder.adapterPosition
                adapter.deleteOnSwipe(currPosition)
            }
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView(view)
        apiRequest()
        fillListWithData()
        initAddButton(view)
        initToolbar(view)
        pinEditText(view)

        showBottomSheetBtn.setOnClickListener {
            val bottomSheet = CurrBottomSheet()
            bottomSheet.bottomSheetListener = this@ConverterFragment
            bottomSheet.show(parentFragmentManager, "CurrBottomSheet")
        }
    }

    private fun initToolbar(view: View) {
        toolbar = requireActivity().findViewById(R.id.toolbar)
    }

    private fun initRecyclerView(view: View) {
        adapter = CurrencyAdapter(layoutInflater, object : CurrencyAdapter.OnLongClickListener {
            override fun onLongClick(itemView: View, currency: Currency) {
                isDeleteCalled = true
                itDelete = currency
                invalidateOptionsMenu(requireActivity())
            }

        })
        val layoutManager = LinearLayoutManager(requireContext())
        rvCurrency = view.findViewById(R.id.rv_main)

        rvCurrency.adapter = adapter
        rvCurrency.layoutManager = layoutManager
        itemTouchHelper.attachToRecyclerView(rvCurrency)
    }

    private fun updateCurrencies(amount: Double) {
        val currencies = mutableListOf<Currency>()

        currencies.add(Currency(amount.toString(), R.drawable.img_flag_kz, "Тенге, Казахстан"))
        currencies.add(
            Currency(
                (amount * currentProp[1]).toString(), R.drawable.img_flag_usa, "Доллары, США"
            )
        )
        currencies.add(
            Currency(
                (amount * currentProp[2]).toString(), R.drawable.img_flag_tr, "Лира, Турция"
            )
        )
        currencies.add(
            Currency(
                (amount * currentProp[3]).toString(), R.drawable.img_flag_eu, "Евро, EC"
            )
        )
        adapter.updateDataSet(currencies)
    }

    private fun apiRequest() {
        currentProp.add(1.0)

        MainScope().launch {
            withContext(Dispatchers.Default) {
                RetrofitBuilder.apiService.convertCurrency(API_KEY, "KZT", "USD", 1.0).body()
                    .apply {
                        currentProp.add(1, this!!.result)
                    }
                RetrofitBuilder.apiService.convertCurrency(API_KEY, "KZT", "TRY", 1.0).body()
                    .apply {
                        currentProp.add(2, this!!.result)
                    }

                RetrofitBuilder.apiService.convertCurrency(API_KEY, "KZT", "EUR", 1.0).body()
                    .apply {
                        currentProp.add(3, this!!.result)
                    }
                isDataAvailable = true
            }
        }
    }

    private fun fillListWithData() {
        val currencies = mutableListOf<Currency>()
        currencies.add(Currency("1 500 000", R.drawable.img_flag_kz, "Тенге, Казахстан"))
        currencies.add(Currency("2 3450", R.drawable.img_flag_usa, "Доллары, США"))
        currencies.add(Currency("2 3450", R.drawable.img_flag_tr, "Лира, Турция"))
        currencies.add(Currency("2 3450", R.drawable.img_flag_eu, "Евро, EC"))

        adapter.updateDataSet(currencies)
    }

    private fun initAddButton(view: View) {
        val btnAdd: Button = view.findViewById(R.id.btn_open_bs)
        btnAdd.setOnClickListener {
            val addBottomSheet = CurrBottomSheet()
            addBottomSheet.show(childFragmentManager, null)
        }
    }

    private fun pinEditText(view: View) {
        val pinnedEt: TextInputEditText = view.findViewById(R.id.et_amount)
        pinnedEt.doOnTextChanged { text, start, before, count ->
            if (text.isNullOrEmpty()) {
                fillListWithData()
                return@doOnTextChanged
            }
            updateCurrencies(amount = text.toString().toDouble())
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        val inflater: MenuInflater = requireActivity().menuInflater
        if (isDeleteCalled) {
            inflater.inflate(R.menu.menu_delete, menu)
            toolbar.title = "Item selected"
        } else {
            inflater.inflate(R.menu.menu_convert, menu)
            toolbar.title = "Converter"
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.sort_by_alphabet -> {
                sortByAlphabet()
                item.isChecked = true
                true
            }
            R.id.sort_by_price -> {
                sortByAmount()
                item.isChecked = true
                true
            }
            R.id.menu_sort -> {
                resetSort()
                true
            }
            R.id.menu_delete -> {
                showDeleteDialog()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun sortByAlphabet() {
        adapter.sortByAlphabet()
    }

    private fun sortByAmount() {
        adapter.sortByPrice()
    }

    private fun resetSort() {
        adapter.resetSort()
        fillListWithData()
    }

    private fun showDeleteDialog() {
        DeleteDialogFragment(object : DeleteDialogFragment.OnDeleteListener {
            override fun onDelete() {
                adapter.deleteOnLongClick(itDelete)
            }
        }).show(parentFragmentManager, null)
        isDeleteCalled = false
        invalidateOptionsMenu(activity)
    }

    override fun onAddClicked(name: String, amount: String) {
        val newCurrency = Currency(amount = amount, flag = R.drawable.img_flag_kz, info = name)
        val position = 0
        adapter.addItemToPosition(currency = newCurrency, position = position)

        val smoothScroller = object : LinearSmoothScroller(requireContext()) {
            override fun getVerticalSnapPreference(): Int = LinearSmoothScroller.SNAP_TO_START

        }
        smoothScroller.targetPosition = position

        rvCurrency.layoutManager?.startSmoothScroll(smoothScroller)
    }
}