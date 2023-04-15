package com.example.stepik

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment


class FavoritesFragment : Fragment() {
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
    ): View? {
        return inflater.inflate(R.layout.fragment_favorites, container, false)
    }

    companion object {
        private const val ARG_PRM1 = "param1"
        private const val ARG_PRM2 = "param2"

        @JvmStatic
        fun newInstance(param1: String, param2: String) = FavoritesFragment().apply {
            arguments = Bundle().apply {
                putString(ARG_PRM1, param1)
                putString(ARG_PRM2, param2)
            }
        }
    }
}