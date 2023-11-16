package com.example.petcare.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.FragmentActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.petcare.R
import com.example.petcare.databinding.ActivityMainBinding

class MainActivity : FragmentActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navigation: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        navigation = (supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment).navController

        setNaviBarListener()
    }

    fun setNaviBarToVisible() {
        binding.bottomNavigation.visibility = View.VISIBLE
    }

    private fun setNaviBarListener() {
        binding.bottomNavigation.setOnItemSelectedListener {
            when(it.itemId) {
                R.id.menu_home -> {
                    navigation.navigate(R.id.feedFragment)
                    true
                }
                R.id.menu_new_post -> {
                    navigation.navigate(R.id.createPostFragment)
                    true
                }
                R.id.menu_donation -> {
                    navigation.navigate(R.id.donationListFragment)
                    true
                }
                R.id.menu_my_account -> {
                    navigation.navigate(R.id.myAccountFragment)
                    true
                }

                else -> false
            }
        }
    }
}