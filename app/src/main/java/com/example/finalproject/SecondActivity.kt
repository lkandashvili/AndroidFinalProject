package com.example.finalproject

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI.setupWithNavController
import androidx.viewpager2.widget.ViewPager2
import me.relex.circleindicator.CircleIndicator
import me.relex.circleindicator.CircleIndicator3


class SecondActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private lateinit var bottomNavigationView: BottomNavigationView

    private var titlesList = mutableListOf<String>()
    private var namesList = mutableListOf<String>()
    private var imagesList = mutableListOf<Int>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.contentLayout) as NavHostFragment
        navController = navHostFragment.navController

        bottomNavigationView = findViewById(R.id.bottomNavigationView)
        setupWithNavController(bottomNavigationView, navController)


        postToList()

//        view_pager2.adapter = ViewPagerAdapter(titlesList, namesList, imagesList)
//        view_pager2.orientation = ViewPager2.ORIENTATION_HORIZONTAL
//
//        val indicator = findViewById<CircleIndicator3>(R.id.indicator)
//        indicator.setViewPager(view_Pager2)


    }

    private fun addToList(title: String, name: String, image: Int) {
        titlesList.add(title)
        namesList.add(name)
        imagesList.add(image)
    }

    private fun postToList() {
        for (i in 1..5) {
            addToList("title $i", "name $i", R.mipmap.ic_launcher)
        }
    }
}