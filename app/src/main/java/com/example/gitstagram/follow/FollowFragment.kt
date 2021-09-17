package com.example.gitstagram.follow

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.example.gitstagram.R
import com.example.gitstagram.adapter.MainAdapter
import com.example.gitstagram.databinding.FollowFragmentBinding
import com.example.gitstagram.databinding.FragmentDetailBinding

class FollowFragment(private val type: FollowType) : Fragment() {
    private val viewModel: FollowViewModel by lazy {
        ViewModelProvider(this).get(FollowViewModel::class.java)
    }

    private var _binding: FollowFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FollowFragmentBinding.inflate(inflater)
        binding.lifecycleOwner = this
        viewModel.setType(type)
        binding.viewModel = viewModel
        binding.listItemFollow.adapter = MainAdapter(MainAdapter.OnClickListener {
            viewModel.displayUserDetail(it)
        })
        binding.listItemFollow.layoutManager = GridLayoutManager(context, 1)
        return binding.root
    }

}