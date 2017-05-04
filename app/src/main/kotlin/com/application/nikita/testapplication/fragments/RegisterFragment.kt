package com.application.nikita.testapplication.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.application.nikita.testapplication.R

class RegisterFragment : Fragment() {

    override fun onCreate(savedInstanceState: android.os.Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.fragment_register, container, false)
        val loginTextView = getView()!!.findViewById(R.id.login_editTxtView)
        val passwordTextView = getView()!!.findViewById(R.id.password_editTxtView)
        val repeatPasswordTextView = getView()!!.findViewById(R.id.repeat_password_editTxtView)

        if (repeatPasswordTextView.toString() == passwordTextView.toString()) {
            passwordTextView.setBackgroundColor(R.color.identicalPasswords)
            repeatPasswordTextView.setBackgroundColor(R.color.identicalPasswords)
        } else {
            passwordTextView.setBackgroundColor(R.color.unIdenticalPasswords)
            repeatPasswordTextView.setBackgroundColor(R.color.unIdenticalPasswords)
        }

        val registerButton = getView()!!.findViewById(R.id.register_button)

        return view
    }
}