package com.example.gitstagram.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.gitstagram.databinding.ListItemBinding
import com.example.gitstagram.network.GitUser

class MainAdapter: ListAdapter<GitUser, MainAdapter.MainViewHolder>(DiffCallback) {
    companion object DiffCallback: DiffUtil.ItemCallback<GitUser>() {
        override fun areItemsTheSame(oldItem: GitUser, newItem: GitUser): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: GitUser, newItem: GitUser): Boolean {
            return oldItem == newItem
        }

    }

    inner class MainViewHolder(private val binding: ListItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(gitUser: GitUser) {
            binding.user = gitUser
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ListItemBinding.inflate(layoutInflater)
        return MainViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val gitUser = getItem(position)
        holder.bind(gitUser)
    }

}