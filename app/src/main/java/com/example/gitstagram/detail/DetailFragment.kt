package com.example.gitstagram.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.gitstagram.R
import com.example.gitstagram.databinding.FragmentDetailBinding
import com.example.gitstagram.follow.FollowFragment
import com.example.gitstagram.follow.FollowType
import com.google.android.material.tabs.TabLayoutMediator

class DetailFragment : Fragment() {
    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!
    private val listType = arrayListOf(FollowType.FOLLOWERS, FollowType.FOLLOWING)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        val gitUser = DetailFragmentArgs.fromBundle(requireArguments()).selectedUser
        val viewModelFactory = DetailViewModelFactory(gitUser)
        binding.viewModel = ViewModelProvider(this, viewModelFactory).get(DetailViewModel::class.java)
        binding.pager.adapter = ViewPagerAdapter(listType, this)
        TabLayoutMediator(binding.tabLayout, binding.pager) { tab, position ->
            tab.text = listType[position].string
        }.attach()
        return binding.root
    }

    inner class ViewPagerAdapter(private val list: ArrayList<FollowType>, fragment: Fragment) :
        FragmentStateAdapter(fragment) {

        override fun getItemCount(): Int {
            return list.size
        }

        override fun createFragment(position: Int): Fragment {
            return FollowFragment(list[position])
        }
    }
}