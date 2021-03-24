package com.tashi.shisheo_developer_proficiency_assignment.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tashi.shisheo_developer_proficiency_assignment.R
import com.tashi.shisheo_developer_proficiency_assignment.ui.activities.adapters.RestaurantsRecyclerViewAdapter
import com.tashi.shisheo_developer_proficiency_assignment.ui.data.Restaurants


class HomeFragment : Fragment() {

    private lateinit var adapter: RestaurantsRecyclerViewAdapter
    private lateinit var restaurantSearchView: SearchView
    private lateinit var restaurantRecyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val view: View = inflater.inflate(R.layout.fragment_home, container, false)
        restaurantRecyclerView = view.findViewById(R.id.restaurant_rv)
        restaurantSearchView = view.findViewById(R.id.restaurant_search)
        restaurantRecyclerView.layoutManager = LinearLayoutManager(context)
        restaurantRecyclerView.setHasFixedSize(true)

        restaurantSearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapter.filter.filter(newText)
                return false
            }

        })

        getListOfCountries()

        return view
    }

    private fun getListOfCountries() {

        adapter = RestaurantsRecyclerViewAdapter(Restaurants.names, Restaurants.images)
        restaurantRecyclerView.adapter = adapter
    }
}