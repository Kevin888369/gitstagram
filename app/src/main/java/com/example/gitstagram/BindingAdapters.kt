package com.example.gitstagram

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.gitstagram.main.MainAdapter
import com.example.gitstagram.network.GitUser

@BindingAdapter("imageUrl")
fun setImage(imageView: ImageView, imageUrl: String?) {
    imageUrl.let {
        Glide.with(imageView.context)
            .load(imageUrl)
            .into(imageView)
    }
}

@BindingAdapter("listItem")
fun bindRecyclerView(recyclerView: RecyclerView, data: List<GitUser>?) {
    data?.let {
        val adapter = recyclerView.adapter as MainAdapter
        adapter.submitList(data)
    }
}