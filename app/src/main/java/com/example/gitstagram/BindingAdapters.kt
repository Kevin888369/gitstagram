package com.example.gitstagram

import android.util.Log
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.gitstagram.adapter.MainAdapter
import com.example.gitstagram.network.GitUser

@BindingAdapter("imageUrl")
fun setImage(imageView: ImageView, imageUrl: String?) {
    imageUrl.let {
        Glide.with(imageView.context)
            .load(imageUrl)
            .apply(RequestOptions()
                .placeholder(R.drawable.loading_animation)
                .error(R.drawable.ic_broken_image))
            .into(imageView)
    }
}

@BindingAdapter("listItem")
fun bindRecyclerView(recyclerView: RecyclerView, data: List<GitUser>?) {
    data?.let {
        Log.d("masok", "masok")
        val adapter = recyclerView.adapter as MainAdapter
        adapter.submitList(data)
    }
}