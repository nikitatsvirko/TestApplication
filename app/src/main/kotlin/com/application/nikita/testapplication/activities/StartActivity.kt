package com.application.nikita.testapplication.activities

import android.content.Intent
import android.os.Bundle
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import com.application.nikita.testapplication.R
import com.application.nikita.testapplication.adapters.ViewPagerAdapter
import com.application.nikita.testapplication.fragments.SignInFragment
import com.application.nikita.testapplication.fragments.SignUpFragment
import com.application.nikita.testapplication.helper.SessionManager
import kotlinx.android.synthetic.main.activity_start.*

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

        setupViewPager(viewpager)
        tabs.setupWithViewPager(viewpager)
    }

    private fun setupViewPager(viewPager: ViewPager) {
        val adapter: ViewPagerAdapter = ViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(SignInFragment(), "Sign In")
        adapter.addFragment(SignUpFragment(), "Sign Up")
        viewPager.adapter = adapter
    }
}
