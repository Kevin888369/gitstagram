package com.example.gitstagram

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.gitstagram.adapter.MainAdapter
import com.example.gitstagram.databinding.ActivityMainBinding
import com.example.gitstagram.detail.DetailActivity
import com.example.gitstagram.detail.DetailViewModel
import com.example.gitstagram.main.MainViewModel

class MainActivity : AppCompatActivity() {
    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this).get(MainViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.recyclerView.adapter = MainAdapter(MainAdapter.OnClickListener {
            viewModel.displayUserDetail(it)
        })

        viewModel.navigateToSelectedUser.observe(this, {
            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra(DetailViewModel.SELECTED_USER, it)
            startActivity(intent)
            viewModel.doneDisplayUserDetail()
        })

        binding.recyclerView.layoutManager = GridLayoutManager(this, 1)
    }
}