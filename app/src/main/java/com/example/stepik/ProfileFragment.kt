package com.example.stepik

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class ProfileFragment : Fragment() {

    private lateinit var tabLayout: TabLayout
    private lateinit var viewPagerTwo: ViewPager2

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(view) {
            tabLayout = findViewById(R.id.tab_layout)
            viewPagerTwo = findViewById(R.id.viewpager_two)
        }

        val adapter = ProfileTabAdapter(requireActivity())
        viewPagerTwo.adapter = adapter

        TabLayoutMediator(tabLayout, viewPagerTwo) { tab, position ->
            val tabNames = listOf("Основные", "Статистика")
            tab.text = tabNames[position]
        }.attach()
    }

}