package com.application.nikita.testapplication.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.application.nikita.testapplication.R
import com.application.nikita.testapplication.models.PhotoData
import com.squareup.picasso.Picasso
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.imageBitmap


class PhotoAdapter(var mPhotoList: List<PhotoData> ) : RecyclerView.Adapter<PhotoAdapter.ViewHolder>() {

    override fun getItemCount(): Int = mPhotoList.size

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent?.context).inflate(R.layout.item_layout, parent, false)
        return PhotoAdapter.ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        val photo = mPhotoList[position]
        doAsync {
            holder?.mPhotoImage?.imageBitmap = Picasso.with(holder?.itemView?.context).load(photo.url).get()
        }
        holder?.mPhotoDate?.text = java.text.SimpleDateFormat("dd.MM.yyyy").format(java.util.Date((photo.date * 1000).toLong()))
    }

    class ViewHolder : RecyclerView.ViewHolder {

        var mPhotoImage: ImageView
        var mPhotoDate: TextView

        constructor(itemView: View): super(itemView) {
            this.mPhotoImage = itemView.findViewById(R.id.item_photo) as ImageView
            this.mPhotoDate = itemView.findViewById(R.id.item_date) as TextView
        }
    }
}