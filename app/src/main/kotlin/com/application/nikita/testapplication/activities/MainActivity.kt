package com.application.nikita.testapplication.activities

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.support.design.widget.NavigationView
import android.support.v4.app.FragmentTransaction
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.util.Base64
import android.util.Log
import android.view.MenuItem
import com.application.nikita.testapplication.R
import com.application.nikita.testapplication.fragments.PhotosFragment
import com.application.nikita.testapplication.helper.GPSTracker
import com.application.nikita.testapplication.helper.SessionManager
import com.application.nikita.testapplication.models.User
import com.application.nikita.testapplication.models.UserDao
import khttp.get
import khttp.post
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.toast
import org.jetbrains.anko.uiThread
import java.io.ByteArrayOutputStream

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private var session: SessionManager? = null
    private var fragmentPhotos: PhotosFragment? = null
    private var REQUEST_IMAGE_CAPTURE = 1
    private var latitude: Int = 0
    private var longitude: Int = 0
    private var date: Int = 0
    lateinit private var mUserDao: UserDao
    lateinit private var mUser: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        session = SessionManager(applicationContext)
        mUserDao = UserDao()

        fab.setOnClickListener {
            takePictureIntent()
        }

        val drawer = findViewById(R.id.drawer_layout) as DrawerLayout
        val toggle = ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)
        fragmentPhotos = PhotosFragment()
        val fragmentTransaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.container, fragmentPhotos)
        fragmentTransaction.commit()
        nav_view.menu.getItem(0).isChecked = true
    }

    private fun takePictureIntent() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (takePictureIntent.resolveActivity(packageManager) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        setLatAndLng()
        setDate()
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
            val extras = data?.extras
            val imageBitmap = extras?.get("data") as Bitmap
            val encodedImage = encodeImageToBase(imageBitmap)
            sendImage(encodedImage, date, latitude, longitude)
        }
    }

    private fun setDate() {
        date = (System.currentTimeMillis() / 1000).toInt()
    }

    private fun setLatAndLng() {
        val gps = GPSTracker(this)
        if (gps.canGetLocation()) {
            latitude = gps.getLatitude().toInt()
            longitude = gps.getLongitude().toInt()
        } else {
            gps.showSettingsAlert()
        }
        gps.stopUsingGPS()
    }

    private fun  encodeImageToBase(imageBitmap: Bitmap): String {
        val byteArrayOutputStream = ByteArrayOutputStream()
        imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
        val byteArray = byteArrayOutputStream.toByteArray()
        val encodedImage = Base64.encodeToString(byteArray, Base64.DEFAULT)

        return encodedImage
    }

    override fun onBackPressed() {
        val drawer = findViewById(R.id.drawer_layout) as DrawerLayout
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        val fragTrans: FragmentTransaction = supportFragmentManager.beginTransaction()

        when (id ) {
            R.id.nav_photos -> {
                fragTrans.replace(R.id.container, fragmentPhotos)
            }
            R.id.nav_map -> {
                testFun()
            }
            R.id.nav_exit -> {
                session?.setLogin(false)
                deleteUserFromDataBase()
                Log.d("Main Activity", "User logged in: ${session?.isLoggedIn()}")
                val intent = Intent(applicationContext, StartActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
        fragTrans.commit()

        val drawer = findViewById(R.id.drawer_layout) as DrawerLayout
        drawer.closeDrawer(GravityCompat.START)
        return true
    }

    private fun deleteUserFromDataBase() {
        mUser = mUserDao.loadUser()[0]
        Log.d("USER IN DB BEF DEL", mUser.getInfo())
        mUserDao.deleteUser(mUser)
        mUserDao.deleteAllUsers()
        Log.d("USER IN DB AFT DEL", mUser.getInfo())
    }

    private fun sendImage(encodedImage: String, date: Int ,latitude: Int, longitude: Int) {
        mUser = mUserDao.loadUser()[0]
        val header = mapOf("Access-Token" to mUser.token!!)
        val postParams = mapOf("base64Image" to encodedImage, "date" to date,
                "lat" to latitude, "lng" to longitude)
        Log.d("Sending Image Request", "Image: $encodedImage\n" +
                "date: $date\n" +
                "lat: $latitude\n" +
                "lng: $longitude\n")

        doAsync {
            val request = post("http://213.184.248.43:9099/api/image", json = postParams, headers = header)
            val status = request.statusCode
            uiThread {
                when (status) {
                    200 -> toast("Ok!")
                    400 -> toast("Error!")
                    500 -> toast("File upload error!")
                }
            }
        }
    }

    private fun testFun() {
        mUser = mUserDao.loadUser()[0]
        val header = mapOf("Access-Token" to mUser.token!!)
        doAsync {
            val request = get("http://213.184.248.43:9099/api/image?page=0", headers = header)
            uiThread {
                toast("${request.jsonObject}")
            }
        }
    }
}