package com.example.gitstagram.main

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
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
        binding.recyclerView.adapter = MainAdapter(MainAdapter.OnClickListener {
            viewModel.displayUserDetail(it)
        })

        viewModel.navigateToSelectedUser.observe(viewLifecycleOwner, {
            it?.let {
                findNavController().navigate(MainFragmentDirections.actionMainFragmentToDetailFragment(it))
                viewModel.doneDisplayUserDetail()
            }
        })

        binding.searchBar.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                viewModel.setSearchText(binding.searchBar.text.toString())
            }

            override fun afterTextChanged(p0: Editable?) {}

        })

        viewModel.searchText.observe(viewLifecycleOwner, {
            if (!it.isNullOrBlank()) {
                binding.searchHint.visibility = View.GONE
                binding.recyclerView.visibility = View.VISIBLE
                viewModel.updateUsers()
            } else {
                binding.searchHint.visibility = View.VISIBLE
                binding.recyclerView.visibility = View.GONE
            }
        })

        binding.recyclerView.layoutManager = GridLayoutManager(context, 1)
        return binding.root
    }
}