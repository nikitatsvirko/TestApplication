package com.application.nikita.testapplication.fragments

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.application.nikita.testapplication.R
import khttp.post
import kotlinx.android.synthetic.main.fragment_signup.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class SignUpFragment : Fragment() {

    private val TAG = this.tag

    override fun onCreate(savedInstanceState: android.os.Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? =
            inflater!!.inflate(R.layout.fragment_signup, container, false)


    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        signup_button.setOnClickListener { view ->
            val login = register_login_editTxtView.text.toString()
            val repeatPassword = repeat_password_editTxtView.text.toString()
            if (checkAllFields()) {
               registerUser(login.trim(), repeatPassword.trim())
            } else {
                Snackbar.make(view, R.string.signup_warning_message, Snackbar.LENGTH_SHORT)
                        .setAction("Action", null).show()
            }
        }
    }

    private fun  registerUser(login: String, password: String) {
        doAsync {
            val postParams = mapOf("login" to login, "password" to password)
            val request = post("http://213.184.248.43:9099/api/account/signup", json = postParams)
            val status = request.statusCode
            uiThread {
                Log.d(TAG, "Status $status")
                when(status) {
                    200 -> Toast.makeText(context, R.string.confirm_text, Toast.LENGTH_SHORT).show()
                    400 -> Toast.makeText(context, R.string.denied_text, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun areFieldsCorrect(login: String, password: String): Boolean =
            !((login.length < 4 || login.length > 32) && !login.contains("[a-z0-9_-]+")
                || (password.length < 8 || password.length > 500))

    private fun comparePasswords(firstPassword: String, secondPassword: String): Boolean =
            secondPassword == firstPassword

    private fun checkAllFields(): Boolean {
        val login = register_login_editTxtView.text.toString()
        val password = register_password_editTxtView.text.toString()
        val repeatPassword = repeat_password_editTxtView.text.toString()

        return areFieldsCorrect(login, repeatPassword) && comparePasswords(password, repeatPassword)
    }
}