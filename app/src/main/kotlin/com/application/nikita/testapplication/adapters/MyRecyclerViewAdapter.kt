package com.application.nikita.testapplication.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.application.nikita.testapplication.R
import com.application.nikita.testapplication.models.Photo


class MyRecyclerViewAdapter(photoList: List<Photo>, context: Context) : RecyclerView.Adapter<MyRecyclerViewAdapter.MyViewHolder>() {

    private var mPhotoList: List<Photo>
    private var mContext: Context

    init {
        this.mPhotoList = photoList
        this.mContext = context
    }

    override fun getItemCount(): Int = mPhotoList.size

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent?.context)
                .inflate(R.layout.item_layout, parent, false)

        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder?, position: Int) {
        val mPhoto: Photo = mPhotoList[position]
        holder?.photo?.setImageDrawable(mPhoto.getDrawableFromUrl())
        holder?.date?.setText(mPhoto.date)
    }

    class MyViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {
        var photo: ImageView?
            get() = this.photo
            set(value) {
                this.photo = value
            }
        var date: TextView?
            get() = this.date
            set(value) {
                this.date = value
            }

        init {
            photo = itemView?.findViewById(R.id.item_photo) as ImageView
            date = itemView?.findViewById(R.id.item_date) as TextView
        }
    }
}