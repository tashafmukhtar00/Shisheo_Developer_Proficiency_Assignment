package com.tashi.shisheo_developer_proficiency_assignment.ui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.tashi.shisheo_developer_proficiency_assignment.R
import com.tashi.shisheo_developer_proficiency_assignment.ui.fragments.HomeFragment
import com.tashi.shisheo_developer_proficiency_assignment.ui.fragments.RestaurantLocationFragment

class MainActivity : AppCompatActivity() {

    private lateinit var navigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        navigationView = findViewById(R.id.navigationView)


        val homeFragment = HomeFragment()
        val restLocationsFragment = RestaurantLocationFragment()



        makeCurrentFragment(homeFragment)
        navigationView.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.ic_home -> makeCurrentFragment(homeFragment)
                R.id.ic_locations -> makeCurrentFragment(restLocationsFragment)
            }

            true
        }


    }

    private fun makeCurrentFragment(fragment: Fragment) =
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragmentContainer, fragment)
            commit()
        }


}