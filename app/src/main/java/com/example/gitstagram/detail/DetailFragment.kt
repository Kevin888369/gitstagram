package com.example.gitstagram.detail

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.gitstagram.R
import com.example.gitstagram.databinding.FragmentDetailBinding
import com.example.gitstagram.follow.FollowFragment
import com.example.gitstagram.follow.FollowType
import com.example.gitstagram.network.GitApiStatus
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
        val viewModel = ViewModelProvider(this, viewModelFactory).get(DetailViewModel::class.java)
        binding.viewModel = viewModel
        binding.pager.adapter =
            viewModel.selectedUser.value?.loginName?.let { ViewPagerAdapter(it, listType, this) }
        TabLayoutMediator(binding.tabLayout, binding.pager) { tab, position ->
            tab.text = listType[position].string
        }.attach()
        viewModel.detailResult.observe(viewLifecycleOwner, {
            it.let { resource ->
                when(resource.status) {
                    GitApiStatus.LOADING -> {
                        binding.loadingDetail.visibility = View.VISIBLE
                    }
                    GitApiStatus.DONE -> {
                        binding.loadingDetail.visibility = View.GONE
                        it.data?.let { datum -> viewModel.setUser(datum) }
                    }
                    GitApiStatus.ERROR -> {
                        binding.loadingDetail.visibility = View.GONE
                        Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
                    }
                }
            }
        })
        return binding.root
    }

    inner class ViewPagerAdapter(private val username: String, private val list: ArrayList<FollowType>, fragment: Fragment) :
        FragmentStateAdapter(fragment) {

        override fun getItemCount(): Int {
            return list.size
        }

        override fun createFragment(position: Int): Fragment {
            return FollowFragment.newInstance(username, list[position].string)
        }
    }
}