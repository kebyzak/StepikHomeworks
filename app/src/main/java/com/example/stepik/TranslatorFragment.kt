package com.example.stepik

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

private const val ARG_PRM1 = "param1"
private const val ARG_PRM2 = "param2"

class TranslatorFragment : Fragment() {
    private var prm1: String? = null
    private var prm2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            prm1 = it.getString(ARG_PRM1)
            prm2 = it.getString(ARG_PRM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_translator, container, false)

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) = TranslatorFragment().apply {
            arguments = Bundle().apply {
                putString(ARG_PRM1, param1)
                putString(ARG_PRM2, param2)
            }
        }
    }
}