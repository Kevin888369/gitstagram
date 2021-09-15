package com.example.gitstagram.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.gitstagram.R
import com.example.gitstagram.databinding.FragmentMainBinding

class MainFragment : Fragment() {
    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this).get(MainViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = FragmentMainBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.recyclerView.adapter = MainAdapter()

        viewModel.setSearchText(binding.searchBar.text.toString())

        viewModel.searchText.observe(viewLifecycleOwner, {
            it?.let {
                viewModel.updateUsers(it)
            }
        })

        binding.recyclerView.layoutManager = GridLayoutManager(context, 1)
        return binding.root
    }
}