package com.example.stepik

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.DialogFragment

class DeleteDialogFragment(private val onDeleteListener: OnDeleteListener) : DialogFragment() {
    interface OnDeleteListener {
        fun onDelete()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.delete_dialog, container, false)

        with(view) {
            findViewById<TextView>(R.id.cancel_btn).setOnClickListener {
                dismiss()
            }
            findViewById<TextView>(R.id.delete_btn).setOnClickListener {
                onDeleteListener.onDelete()
                dismiss()
            }

        }
        return view
    }
}