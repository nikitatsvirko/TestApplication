package com.application.nikita.testapplication.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.application.nikita.testapplication.R
import com.application.nikita.testapplication.adapters.PhotoAdapter
import com.application.nikita.testapplication.models.Photo
import com.application.nikita.testapplication.models.PhotoDao
import com.application.nikita.testapplication.models.User
import com.application.nikita.testapplication.models.UserDao
import khttp.get
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import org.json.JSONArray

class PhotosFragment: Fragment() {

    private val TAG = this.tag
    private var mPhotosList: MutableList<Photo>? = ArrayList()
    private var mAdapter: PhotoAdapter? = null
    private var mPage: Int = 0
    private var recyclerView: RecyclerView? = null
    lateinit private var mUserDao: UserDao
    lateinit private var mUser: User
    lateinit private var mPhotoDao: PhotoDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.fragment_photos, container, false)

        mUserDao = UserDao()
        mUser = mUserDao.loadUser()[0]
        mPhotoDao = PhotoDao()

        if (mPhotoDao.loadPhotos().size != 0) {
            Log.d("PhotosFragment", "LoadingFrom DB")
            mPhotosList = mPhotoDao.loadPhotos()
            mPage = mPhotosList?.size!! / 20
        }
        mAdapter = PhotoAdapter(mPhotosList!!)
        val mLayoutManager  = GridLayoutManager(context, 3)
        recyclerView = view.findViewById(R.id.recycler_view) as RecyclerView
        recyclerView?.setHasFixedSize(false)
        recyclerView?.layoutManager = mLayoutManager
        recyclerView?.adapter = mAdapter
        recyclerView?.addOnScrollListener(object: RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy > 0) {
                    if (mLayoutManager.findLastVisibleItemPosition() == ((mPage + 1) * 20 - (5 + (mPage + 4) % 3))) {
                        mPage++
                        downloadImagesRequest(mPage)
                        Log.d("Changing page", "$mPage and index is: ${3 + (mPage + 4) % 3}")
                    }
                    Log.d("Listener", "Scrolling Up, Last visible is: ${mLayoutManager.findLastCompletelyVisibleItemPosition()}")
                } else {
                    if (mLayoutManager.findFirstCompletelyVisibleItemPosition() == 0) {
                        updateScreen()
                    }
                    Log.d("Listener", "Scrolling Down")
                }
            }
        })
        Log.d("Count of Photos", "${mPhotoDao.loadPhotos().size}")

        if(mPhotosList?.size == 0) {
            Log.d("DATABASE", "Table photo isn't created")
            downloadImagesRequest(0)
        }

        return view
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    private fun downloadImagesRequest(page: Int) {
        mUser = mUserDao.loadUser()[0]
        Log.d(TAG, "Downloading $page page")
        val header = mapOf("Access-Token" to mUser.token!!)
        doAsync {
            val request = get("http://213.184.248.43:9099/api/image?page=$page", headers = header)
            updateAdapter(request.jsonObject.getJSONArray("data"))
            uiThread {

            }
        }
    }

    private fun updateAdapter(jsonArray: JSONArray) {
        for (i in 0..(jsonArray.length()) - 1) {
            val jsonObject = jsonArray.getJSONObject(i)
            val id = jsonObject.getInt("id")
            val url = jsonObject.getString("url")
            val date = jsonObject.getLong("date")
            val lat = jsonObject.getString("lat")
            val lng = jsonObject.getString("lng")
            val photo = mPhotoDao.createPhoto()

            Log.d(TAG, "id: $id\n" +
                    "url: $url\n" +
                    "date: $date\n" +
                    "lat: $lat\n" +
                    "lng: $lng\n")

            photo.date = date
            photo.id = id
            photo.lat = lat
            photo.lng = lng
            photo.url = url

            mPhotosList!!.add(photo)
            mPhotoDao.savePhoto(photo)
        }
        recyclerView?.recycledViewPool?.clear()
        mAdapter?.notifyDataSetChanged()
    }

    private fun updateScreen() {
        mPhotoDao.deleteAllPhotos()
        if (mPhotosList?.size != 0) {
            recyclerView?.recycledViewPool?.clear()
            mPhotosList?.clear()
        } else {
            downloadImagesRequest(0)
        }

        for(i in 0..mPage) {
            downloadImagesRequest(i)
        }
    }
}