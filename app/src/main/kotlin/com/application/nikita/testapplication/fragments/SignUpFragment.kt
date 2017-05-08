package com.application.nikita.testapplication.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.application.nikita.testapplication.R
import khttp.post
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class SignUpFragment : Fragment() {

    private var mLoginTextView: EditText? = null
    private var mPasswordTextView: EditText? = null
    private var mRepeatPasswordTextView: EditText? = null
    private var mRegisterButton: Button? = null

    override fun onCreate(savedInstanceState: android.os.Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? =
            inflater!!.inflate(R.layout.fragment_signup, container, false)


    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mLoginTextView = getView()!!.findViewById(R.id.register_login_editTxtView) as EditText
        mPasswordTextView = getView()!!.findViewById(R.id.register_password_editTxtView) as EditText
        mRepeatPasswordTextView = getView()!!.findViewById(R.id.repeat_password_editTxtView) as EditText
        mRegisterButton = getView()!!.findViewById(R.id.signup_button) as Button

        mRegisterButton?.setOnClickListener {
            if (checkAllFields()) {
               registerUser(mLoginTextView?.text.toString().trim(),
                       mRepeatPasswordTextView?.text.toString().trim())
            } else {
                Toast.makeText(context, R.string.signup_warning_message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun  registerUser(login: String, password: String) {
        doAsync {
            val postParams = mapOf("login" to login, "password" to password)
            val request = post("http://213.184.248.43:9099/api/account/signup", json = postParams)
            val status = request.statusCode
            uiThread {
                Log.d("FRAGMENT!!!!!", "Status $status")
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

    private fun checkAllFields(): Boolean =
        areFieldsCorrect(mLoginTextView?.text.toString(), mRepeatPasswordTextView?.text.toString())
                && comparePasswords(mPasswordTextView?.text.toString(), mRepeatPasswordTextView?.text.toString())

}