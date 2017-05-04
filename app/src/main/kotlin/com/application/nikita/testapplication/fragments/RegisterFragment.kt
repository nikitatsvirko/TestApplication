package com.application.nikita.testapplication.fragments

import android.support.v4.app.Fragment

class RegisterFragment : Fragment() {

    override fun onCreate(savedInstanceState: android.os.Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: android.view.LayoutInflater?, container: android.view.ViewGroup?,
                              savedInstanceState: android.os.Bundle?): android.view.View? {
        return inflater!!.inflate(com.application.nikita.testapplication.R.layout.fragment_register, container, false)
    }

    private fun toRegisterUser(login: String, password: String) {

    }
}