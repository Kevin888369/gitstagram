package com.example.gitstagram.follow

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.gitstagram.adapter.MainAdapter
import com.example.gitstagram.databinding.FollowFragmentBinding
import com.example.gitstagram.detail.DetailFragmentDirections

private const val USERNAME = "username"
private const val TYPE = "type"

class FollowFragment : Fragment() {
    companion object {
        fun newInstance(username: String, type: String) =
            FollowFragment().apply {
                arguments = Bundle().apply {
                    putString(USERNAME, username)
                    putString(TYPE, type)
                }
            }
    }

    private val viewModel: FollowViewModel by lazy {
        ViewModelProvider(this).get(FollowViewModel::class.java)
    }

    private var _binding: FollowFragmentBinding? = null
    private val binding get() = _binding!!

    private var username: String? = null
    private var type: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            username = it.getString(USERNAME).toString()
            type = it.getString(TYPE).toString()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FollowFragmentBinding.inflate(inflater)
        binding.lifecycleOwner = this
        viewModel.setFollow(FollowType.from(type!!), username!!)
        binding.viewModel = viewModel
        binding.listItemFollow.adapter = MainAdapter(MainAdapter.OnClickListener {
            viewModel.displayUserDetail(it)
        })

        viewModel.navigateToSelectedUser.observe(viewLifecycleOwner, {
            it?.let {
                findNavController().navigate(DetailFragmentDirections.actionDetailFragmentSelf(it))
                viewModel.doneDisplayUserDetail()
            }
        })

        viewModel.type.observe(viewLifecycleOwner, {
            viewModel.updateData()
        })

        binding.listItemFollow.layoutManager = GridLayoutManager(context, 1)
        return binding.root
    }

}