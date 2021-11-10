package com.example.gitstagram.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.gitstagram.R
import com.example.gitstagram.databinding.ActivityDetailBinding
import com.example.gitstagram.follow.FollowFragment
import com.example.gitstagram.follow.FollowType
import com.example.gitstagram.network.GitUser
import com.google.android.material.tabs.TabLayoutMediator

class DetailActivity : AppCompatActivity() {
    private var _binding: ActivityDetailBinding? = null
    private val binding get() = _binding!!
    private val listType = arrayListOf(FollowType.FOLLOWERS, FollowType.FOLLOWING)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = DataBindingUtil.setContentView(this, R.layout.activity_detail)
        binding.lifecycleOwner = this
        val gitUser = intent.extras?.getParcelable<GitUser>(DetailViewModel.SELECTED_USER)
        val viewModelFactory = gitUser?.let { DetailViewModelFactory(it) }
        val viewModel = viewModelFactory?.let {
            ViewModelProvider(this,
                it
            ).get(DetailViewModel::class.java)
        }
        binding.viewModel = viewModel
        binding.pager.adapter =
            viewModel?.selectedUser?.value?.loginName?.let {
                ViewPagerAdapter(
                    it,
                    listType,
                    this
                )
            }

        TabLayoutMediator(binding.tabLayout, binding.pager) { tab, position ->
            tab.text = listType[position].string
        }.attach()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    inner class ViewPagerAdapter(private val username: String, private val list: ArrayList<FollowType>, activity: AppCompatActivity) :
        FragmentStateAdapter(activity) {

        override fun getItemCount(): Int {
            return list.size
        }

        override fun createFragment(position: Int): Fragment {
            return FollowFragment.newInstance(username, list[position].string)
        }
    }
}