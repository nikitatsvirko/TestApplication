package com.application.nikita.testapplication.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.application.nikita.testapplication.R
import khttp.post
import kotlin.concurrent.thread

class RegisterFragment : Fragment(), TextWatcher {

    private var loginTextView: EditText? = null
    private var passwordTextView: EditText? = null
    private var repeatPasswordTextView: EditText? = null
    private var registerButton: Button? = null
    private var isIdentical = false
    private var _ignore = false

    override fun onCreate(savedInstanceState: android.os.Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? =
            inflater!!.inflate(R.layout.fragment_register, container, false)


    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loginTextView = getView()!!.findViewById(R.id.register_login_editTxtView) as EditText
        passwordTextView = getView()!!.findViewById(R.id.register_password_editTxtView) as EditText
        repeatPasswordTextView = getView()!!.findViewById(R.id.repeat_password_editTxtView) as EditText
        registerButton = getView()!!.findViewById(R.id.register_button) as Button

        loginTextView?.addTextChangedListener(this)
        passwordTextView?.addTextChangedListener(this)
        repeatPasswordTextView?.addTextChangedListener(this)

        registerButton?.setOnClickListener {
            if (isIdentical) {
               registerUser(loginTextView?.text.toString().trim(),
                       repeatPasswordTextView?.text.toString().trim())
            }
        }
    }

    private fun  registerUser(login: String, password: String) {
        Log.d("Register Fragment", "Login is $login and password is $password")
        val postParams = mapOf("login" to login, "password" to password)
        thread {
            val request = post("http://213.184.248.43:9099/api/account/signup", json = postParams)
            Log.d("Register Fragment", "Request is ${request.text}")
        }
    }

    private fun checkFields(login: String, password: String) {
        if ((login.length < 4 || login.length > 32) && !login.contains("[a-z0-9_-]+")
                || (password.length < 8 || password.length > 500))
            Toast.makeText(context, getString(R.string.registration_warning_message), Toast.LENGTH_LONG).show()
    }

    private fun comparePasswords(firstPassword: String, secondPassword: String): Boolean {
        when(secondPassword == firstPassword) {
            true -> {
                return true
            }
            false -> {
                return false
            }
        }
    }

    override fun afterTextChanged(s: Editable?) {
        if(_ignore)
            return

        _ignore = true
        val loginText = loginTextView?.text.toString()
        val passwordText = passwordTextView?.text.toString()
        val repPasswordText = repeatPasswordTextView?.text.toString()

        checkFields(loginText, passwordText)

        isIdentical = comparePasswords(passwordText, repPasswordText)
        _ignore = false
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

    }
}