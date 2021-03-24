package com.tashi.shisheo_developer_proficiency_assignment.ui.data

import com.tashi.shisheo_developer_proficiency_assignment.R

object Restaurants {

    val names: ArrayList<String>
        get() {
            val nameList = ArrayList<String>()
            nameList.add("Doors Freestyle Grill")
            nameList.add("Al Dawaar Revolving Restaurant")
            nameList.add("The Boardwalk")
            nameList.add("Indego by Vineet")
            nameList.add("Hakkasan Dubai")
            // birthday card from photo on cake 2020
            return nameList
        }

    val images: ArrayList<Int>
        get() {
            val imageList = ArrayList<Int>()
            imageList.add(R.drawable.rest1)
            imageList.add(R.drawable.rest2)
            imageList.add(R.drawable.rest3)
            imageList.add(R.drawable.rest4)
            imageList.add(R.drawable.rest5)
            // birthday card from photo on cake 2020
            return imageList
        }
}