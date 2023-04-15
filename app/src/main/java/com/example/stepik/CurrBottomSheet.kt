package com.example.stepik

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.textfield.TextInputEditText

class CurrBottomSheet : BottomSheetDialogFragment() {

    private lateinit var addBtn: TextView
    private lateinit var currency: TextInputEditText
    private lateinit var inKzt: TextInputEditText
    var bottomSheetListener: BottomSheetListener? = null

    interface BottomSheetListener {
        fun onAddClicked(name: String, amount: String)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.bottom_sheet, container, false)
        addBtn = view.findViewById(R.id.btn_add)
        currency = view.findViewById(R.id.et_currency_name)
        inKzt = view.findViewById(R.id.et_amount)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addBtn.setOnClickListener {
            bottomSheetListener?.onAddClicked(
                name = currency.text.toString(), amount = inKzt.text.toString()
            )
        }
    }
}
