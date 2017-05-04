package com.application.nikita.testapplication.activities

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import com.application.nikita.testapplication.R
import com.application.nikita.testapplication.adapters.ViewPagerAdapter
import com.application.nikita.testapplication.fragments.LoginFragment
import com.application.nikita.testapplication.fragments.RegisterFragment

class StartActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)

        val viewPager = findViewById(R.id.viewpager) as ViewPager
        setupViewPager(viewPager)

        val tabLayout = findViewById(R.id.tabs) as TabLayout
        tabLayout.setupWithViewPager(viewPager)
    }

    private fun setupViewPager(viewPager: ViewPager) {
        val adapter: ViewPagerAdapter = ViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(LoginFragment(), "Login")
        adapter.addFragment(RegisterFragment(), "Register")
        viewPager.setAdapter(adapter)
    }
}
