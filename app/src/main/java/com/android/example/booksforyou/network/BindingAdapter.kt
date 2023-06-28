package com.android.example.booksforyou.network

import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load


@BindingAdapter("listData")
fun bindRecyclerView(recyclerView: RecyclerView, data: List<ApiBook>?) {
    val adapter = recyclerView.adapter as BookGridAdapter
    adapter.submitList(data)
}

@BindingAdapter("imageUrl")
fun bindImage(imgView: ImageView, imgUrl: String?) {
    imgUrl?.let {
        val imgUri = imgUrl.toUri().buildUpon().scheme("https").build()
        imgView.load(imgUri)
    }
}
