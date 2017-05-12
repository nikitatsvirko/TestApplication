package com.application.nikita.testapplication.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.application.nikita.testapplication.R
import com.application.nikita.testapplication.adapters.PhotoAdapter
import com.application.nikita.testapplication.models.Photo
import kotlinx.android.synthetic.main.fragment_photos.*

class PhotosFragment: Fragment() {

    private val TAG = this.tag
    private var mPhotosList: MutableList<Photo>? = ArrayList()
    private var mAdapter: PhotoAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_photos, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mPhotosList!!.add(0, Photo(24101996, 45, "52.025541", ",29.235373", "http://i.imgur.com/DvpvklR.png"))
        mPhotosList!!.add(1, Photo(25101996, 46, "52.025541", ",29.235373", "http://i.imgur.com/DvpvklR.png"))
        mPhotosList!!.add(2, Photo(26101996, 47, "52.025541", ",29.235373", "http://i.imgur.com/DvpvklR.png"))
        mPhotosList!!.add(3, Photo(27101996, 48, "52.025541", ",29.235373", "http://i.imgur.com/DvpvklR.png"))
        mPhotosList!!.add(4, Photo(28101996, 49, "52.025541", ",29.235373", "http://i.imgur.com/DvpvklR.png"))
        mPhotosList!!.add(5, Photo(29101996, 50, "52.025541", ",29.235373", "http://i.imgur.com/DvpvklR.png"))

        mAdapter = PhotoAdapter(mPhotosList!!)
        val mLayoutManager: RecyclerView.LayoutManager = GridLayoutManager(activity, 3)
        recycler_view.setHasFixedSize(false)
        recycler_view.layoutManager = mLayoutManager
        recycler_view.adapter = mAdapter
    }
}