package com.example.stepik

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

private const val ARG_PRM1 = "param1"
private const val ARG_PRM2 = "param2"
const val API_KEY = "zsZ83n4OD4LAKbB8gdOoAF8DypPFVsa8"

class TranslatorFragment : Fragment() {
    private var prm1: String? = null
    private var prm2: String? = null
    private lateinit var main: TextView

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        main = view.findViewById(R.id.tv_main)

        MainScope().launch {
            val result: String
            withContext(Dispatchers.Default) {
                RetrofitBuilder.apiService.convertCurrency(API_KEY, "KZT", "USD", 150000.0).body()
                    .apply {
                        result = this?.result.toString()
                    }
            }
            main.text = result
        }
    }

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