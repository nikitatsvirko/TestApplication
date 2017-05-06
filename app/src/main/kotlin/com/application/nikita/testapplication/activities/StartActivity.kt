package com.application.nikita.testapplication.activities

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import com.application.nikita.testapplication.R
import com.application.nikita.testapplication.adapters.ViewPagerAdapter
import com.application.nikita.testapplication.fragments.SignInFragment
import com.application.nikita.testapplication.fragments.SignUpFragment
import com.application.nikita.testapplication.helper.SessionManager

class StartActivity : AppCompatActivity() {

    private var session: SessionManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)

        session = SessionManager(applicationContext)
        if (session?.isLoggedIn()!!) {
            val intent = Intent(applicationContext, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        val viewPager = findViewById(R.id.viewpager) as ViewPager
        setupViewPager(viewPager)

        val tabLayout = findViewById(R.id.tabs) as TabLayout
        tabLayout.setupWithViewPager(viewPager)
    }

    private fun setupViewPager(viewPager: ViewPager) {
        val adapter: ViewPagerAdapter = ViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(SignInFragment(), "Sign In")
        adapter.addFragment(SignUpFragment(), "Sign Up")
        viewPager.setAdapter(adapter)
    }
}
