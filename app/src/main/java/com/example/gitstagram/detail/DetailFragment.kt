package com.example.gitstagram.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.gitstagram.R
import com.example.gitstagram.databinding.FragmentDetailBinding
import com.google.android.material.tabs.TabLayoutMediator

class DetailFragment : Fragment() {
    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        val gitUser = DetailFragmentArgs.fromBundle(requireArguments()).selectedUser
        val viewModelFactory = DetailViewModelFactory(gitUser)
        binding.viewModel = ViewModelProvider(this, viewModelFactory).get(DetailViewModel::class.java)
        return binding.root
    }

//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        TabLayoutMediator(binding.tabLayout, binding.pager) { tab, position ->
//
//        }
//
//    }
}