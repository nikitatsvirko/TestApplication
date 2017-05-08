package com.application.nikita.testapplication.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.application.nikita.testapplication.R
import com.application.nikita.testapplication.adapters.MyRecyclerViewAdapter
import com.application.nikita.testapplication.models.Photo

class PhotosFragment: Fragment() {
    private var mPhotosList: MutableList<Photo>? = null
    private var mRecyclerView: RecyclerView? = null
    private var mAdapter: MyRecyclerViewAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_photos, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mRecyclerView = getView()!!.findViewById(R.id.recycler_view) as RecyclerView?

        mAdapter = MyRecyclerViewAdapter(mPhotosList!!, context)
        val mLayoutManager: RecyclerView.LayoutManager = LinearLayoutManager(context)
        mRecyclerView?.setLayoutManager(mLayoutManager)
        mRecyclerView?.setItemAnimator(DefaultItemAnimator())
        mRecyclerView?.setAdapter(mAdapter)

        prepareData()
    }

    private fun prepareData() {

        var i: Int = 0
        while (i in 0..10) {
            mPhotosList!!.add(i, Photo(24101996, 45, "52.025541", ",29.235373", "https://static.panoramio.com.storage.googleapis.com/photos/large/76983180.jpg"))
        }

        mAdapter?.notifyDataSetChanged()
    }
}