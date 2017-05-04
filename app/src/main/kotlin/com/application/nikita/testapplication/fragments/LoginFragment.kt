package com.application.nikita.testapplication.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import com.application.nikita.testapplication.R
import khttp.post

class LoginFragment : Fragment() {

    private var loginTextView: EditText? = null
    private var passwordTextView: EditText? = null
    private var loginButton: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loginTextView = getView()!!.findViewById(R.id.login_editTxtView) as EditText
        passwordTextView = getView()!!.findViewById(R.id.password_editTxtView) as EditText
        loginButton = getView()!!.findViewById(R.id.login_button) as Button

        loginButton?.setOnClickListener {
            if(areFieldsCorrect()) {
                setUserLoggedIn(loginTextView?.text.toString().trim(),
                        passwordTextView?.text.toString().trim())
            }
        }
    }

    private fun setUserLoggedIn(login: String, password: String) {
        val postParams = mapOf("login" to login, "password" to password)
        val request = post("http://213.184.248.43:9099/api/account/signin", json = postParams)
        val status = request.statusCode
    }

    private fun  areFieldsCorrect(): Boolean {

        return true
    }
}
