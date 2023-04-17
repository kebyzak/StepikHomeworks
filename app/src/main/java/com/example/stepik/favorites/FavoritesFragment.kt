package com.example.stepik.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.stepik.R

class FavoritesFragment : Fragment() {
    private lateinit var rvFavs: RecyclerView
    private lateinit var adapter: FavoritesAdapter

    private lateinit var maxFragment: Fragment
    private lateinit var minFragment: Fragment
    private var fragment: Fragment? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_favorites, container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (savedInstanceState == null) {
            fragment = MaxFragment.newInstance().also {
                childFragmentManager.beginTransaction().replace(R.id.fragment_container, it)
                    .commitNow()
            }
        }

        maxFragment = MaxFragment.newInstance()
        minFragment = MinFragment.newInstance()

        val icon = view.findViewById<View>(R.id.btn_close)
        icon.setOnClickListener {
            val fragmentManager = requireActivity().supportFragmentManager
            val transaction = fragmentManager.beginTransaction()

            if (fragmentManager.findFragmentById(R.id.container) is MaxFragment) {
                transaction.replace(R.id.container, minFragment)
                transaction.addToBackStack(null)
            } else {
                transaction.replace(R.id.container, maxFragment)
            }
            transaction.commit()

            view.findViewById<FrameLayout>(R.id.fragment_container).visibility = View.GONE
        }
    }

    private fun initRecycler(view: View) {
        rvFavs = view.findViewById(R.id.rv_favs)
        adapter = FavoritesAdapter(LayoutInflater.from(requireContext()))
        rvFavs.adapter = adapter
        rvFavs.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun initData() {
        val favs = listOf(
            Favorite(R.drawable.movie1, "Forsazh 2009"),
            Favorite(R.drawable.movie2, "Komediia 'Akim' 2020"),
            Favorite(R.drawable.movie1, "Forsazh 2009"),
            Favorite(R.drawable.movie2, "Komediia 'Akim' 2020")
        )
        adapter.setData(favs)
    }
}
