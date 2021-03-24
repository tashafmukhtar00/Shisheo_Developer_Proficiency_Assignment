package com.tashi.shisheo_developer_proficiency_assignment.ui.activities.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tashi.shisheo_developer_proficiency_assignment.R
import java.util.*

import kotlin.collections.ArrayList


class RestaurantsRecyclerViewAdapter(
    private var restaurantList: ArrayList<String>,
    private var restaurantPhotoList: ArrayList<Int>
) :
    RecyclerView.Adapter<RestaurantsRecyclerViewAdapter.RestaurantViewHolder>(), Filterable {

    var restaurantFilterList = ArrayList<String>()
    private lateinit var mContext: Context


    init {
        restaurantFilterList = restaurantList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RestaurantViewHolder {
        val countryListView =
            LayoutInflater.from(parent.context).inflate(R.layout.list_item_layout, parent, false)
        val sch = RestaurantViewHolder(countryListView)
        mContext = parent.context
        return sch


    }

    override fun getItemCount(): Int {
        return restaurantFilterList.size
    }


    override fun onBindViewHolder(holder: RestaurantViewHolder, position: Int) {
        holder.restaurantName.text = restaurantFilterList[position]
        Glide.with(mContext).load(restaurantPhotoList[position]).into(holder.restaurantImage)
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                if (charSearch.isEmpty()) {
                    restaurantFilterList = restaurantList
                } else {
                    val resultList = ArrayList<String>()
                    for (row in restaurantList) {
                        if (row.toLowerCase(Locale.ROOT)
                                .contains(charSearch.toLowerCase(Locale.ROOT))
                        ) {
                            resultList.add(row)
                        }
                    }
                    restaurantFilterList = resultList
                }
                val filterResults = FilterResults()
                filterResults.values = restaurantFilterList
                return filterResults
            }

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                restaurantFilterList = results?.values as ArrayList<String>
                notifyDataSetChanged()
            }

        }
    }

    class RestaurantViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val restaurantName = itemView.findViewById(R.id.selectRestaurant) as TextView
        val restaurantImage = itemView.findViewById(R.id.restaurantImage) as ImageView


    }


}