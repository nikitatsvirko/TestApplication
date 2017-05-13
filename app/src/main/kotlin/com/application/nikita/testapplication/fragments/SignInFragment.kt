package com.application.nikita.testapplication.fragments

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.application.nikita.testapplication.R
import com.application.nikita.testapplication.activities.MainActivity
import com.application.nikita.testapplication.helper.SessionManager
import com.application.nikita.testapplication.models.User
import com.application.nikita.testapplication.models.UserDao
import khttp.post
import kotlinx.android.synthetic.main.fragment_login.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import org.json.JSONObject

class SignInFragment : Fragment() {

    private val TAG = this.tag
    private var mSession: SessionManager? =null
    lateinit private var mUserDao: UserDao
    lateinit private var mUser: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? =
        inflater!!.inflate(R.layout.fragment_login, container, false)

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mUserDao = UserDao()

        login_button.setOnClickListener { view ->
            val login = login_editTxtView.text.toString()
            val password = password_editTxtView.text.toString()

            if (areFieldsCorrect(login, password)) {
                makeLogInRequest(login.trim(),
                        password.trim())
            } else {
                Snackbar.make(view, R.string.signup_warning_message, Snackbar.LENGTH_SHORT)
                        .setAction("Action", null).show()
            }

        }
    }

    private fun makeLogInRequest(login: String, password: String) {
        val postParams = mapOf("login" to login, "password" to password)
        doAsync {
            val request = post("http://213.184.248.43:9099/api/account/signin", json = postParams)
            val status = request.statusCode
            uiThread {
                when(status) {
                    200 -> {
                        Toast.makeText(context, R.string.signin_ok_text, Toast.LENGTH_SHORT).show()
                        setUserLoggedIn()
                        saveUserToDataBase(request.jsonObject.getJSONObject("data"))
                        val intent = Intent(activity, MainActivity::class.java)
                        startActivity(intent)
                        activity.finish()
                    }
                    400 -> Toast.makeText(context, R.string.signin_denied_text, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun  saveUserToDataBase(jsonObject: JSONObject) {
        val login = jsonObject.getString("login")
        val token = jsonObject.getString("token")
        val userId = jsonObject.getInt("userId")

        mUser = mUserDao.createUser()
        mUser.login = login
        mUser.token = token
        mUser.userId = userId
        mUserDao.saveUser(mUser)
        Log.d("USER IN DATABASE", mUser.getInfo())
    }

    private fun areFieldsCorrect(login: String, password: String): Boolean =
            !((login.length < 4 || login.length > 32) && !login.contains("[a-z0-9_-]+")
                    || (password.length < 8 || password.length > 500))

    private fun setUserLoggedIn() {
        mSession = SessionManager(context)
        mSession?.setLogin(true)
    }
}
