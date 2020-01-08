package com.example.tablayout

import android.os.Bundle
import android.view.MenuItem
import com.google.android.material.tabs.TabLayout
import androidx.viewpager.widget.ViewPager
import androidx.appcompat.app.AppCompatActivity
import com.example.tablayout.R.drawable
import com.example.tablayout.ui.main.SectionsPagerAdapter

class MainActivity : AppCompatActivity() {

    private var signnOut: MenuItem? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)
        val viewPager: ViewPager = findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabs)
        tabs.setupWithViewPager(viewPager)
        tabs.getTabAt(0)!!.setIcon(drawable.ic_workout)
        tabs.getTabAt(1)!!.setIcon(drawable.ic_standings)
        tabs.getTabAt(2)!!.setIcon(drawable.ic_profile)



    }



}