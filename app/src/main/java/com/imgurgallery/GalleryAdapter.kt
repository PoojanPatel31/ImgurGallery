package com.imgurgallery

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.imgurgallery.models.GalleryItem
import com.imgurgallery.util.DateTimeUtil

class GalleryAdapter : RecyclerView.Adapter<GalleryAdapter.RowViewHolder>() {

    companion object{
        const val gridSize = 3
        const val listSize = 1
    }

    private val galleryItems: MutableList<GalleryItem> = mutableListOf()
    private val glideReqOption = RequestOptions().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)

    fun loadImages(images: List<GalleryItem>) {
        galleryItems.clear()
        galleryItems.addAll(images)
        notifyItemRangeChanged(0, galleryItems.size)
    }

    override fun getItemCount(): Int {
        return galleryItems.count()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RowViewHolder =
        LayoutInflater.from(parent.context).inflate(R.layout.gallery_row, parent, false).let {
            RowViewHolder(it)
        }

    override fun onBindViewHolder(holder: RowViewHolder, position: Int) {
        holder.onBind(galleryItems[position])
    }

    inner class RowViewHolder(view: View) : ViewHolder(view) {

        private val image: ImageView = view.findViewById(R.id.image)
        private val title: TextView = view.findViewById(R.id.title)
        private val postDate: TextView = view.findViewById(R.id.post_date)
        private val imageCount: TextView = view.findViewById(R.id.image_count)

        fun onBind(item: GalleryItem) {
            Glide.with(image)
                .applyDefaultRequestOptions(glideReqOption)
                .load(item.images.first().link)
                .into(image)
            title.text = item.title
            postDate.text = DateTimeUtil.format(item.datetime)
            if (item.images.count() > 1) {
                imageCount.visibility = View.VISIBLE
                imageCount.text = item.images.count().toString()
            } else {
                imageCount.visibility = View.GONE
            }
        }
    }
}