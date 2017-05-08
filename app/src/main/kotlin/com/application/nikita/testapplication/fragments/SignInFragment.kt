package com.application.nikita.testapplication.fragments

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.application.nikita.testapplication.R
import com.application.nikita.testapplication.activities.MainActivity
import com.application.nikita.testapplication.helper.SessionManager
import khttp.post
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class SignInFragment : Fragment() {

    private var mLoginTextView: EditText? = null
    private var mPasswordTextView: EditText? = null
    private var mLoginButton: Button? = null
    private var mSession: SessionManager? =null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mLoginTextView = getView()!!.findViewById(R.id.login_editTxtView) as EditText
        mPasswordTextView = getView()!!.findViewById(R.id.password_editTxtView) as EditText
        mLoginButton = getView()!!.findViewById(R.id.login_button) as Button

        mLoginButton?.setOnClickListener {
            val login = mLoginTextView?.text.toString()
            val passwrod = mPasswordTextView?.text.toString()

            if (areFieldsCorrect(login, passwrod))
                    makeLogInRequest(login.trim(),
                            passwrod.trim())

        }
    }

    private fun makeLogInRequest(login: String, password: String) {
        doAsync {
            val postParams = mapOf("login" to login, "password" to password)
            val request = post("http://213.184.248.43:9099/api/account/signin", json = postParams)
            val status = request.statusCode
            uiThread {
                when(status) {
                    200 -> {
                        Toast.makeText(context, R.string.signin_ok_text, Toast.LENGTH_SHORT).show()
                        setUserLoggedIn()
                        val intent = Intent(activity, MainActivity::class.java)
                        startActivity(intent)
                        activity.finish()
                    }
                    400 -> Toast.makeText(context, R.string.signin_denied_text, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun areFieldsCorrect(login: String, password: String): Boolean =
            !((login.length < 4 || login.length > 32) && !login.contains("[a-z0-9_-]+")
                    || (password.length < 8 || password.length > 500))

    private fun setUserLoggedIn() {
        mSession = SessionManager(context)
        mSession?.setLogin(true)
    }
}
