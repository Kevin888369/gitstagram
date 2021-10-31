package com.example.gitstagram.main

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.inputmethod.InputMethodManager
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.gitstagram.adapter.MainAdapter
import com.example.gitstagram.databinding.FragmentMainBinding
import android.app.Activity
import android.view.*
import android.widget.Toast
import com.example.gitstagram.network.GitApiStatus


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

        binding.searchBar.setOnKeyListener(object : View.OnKeyListener {
            override fun onKey(p0: View?, p1: Int, p2: KeyEvent?): Boolean {
                if (p2?.action == KeyEvent.ACTION_DOWN) {
                    when (p1) {
                        KeyEvent.KEYCODE_DPAD_CENTER, KeyEvent.KEYCODE_ENTER -> {
                            viewModel.setSearchText(binding.searchBar.text.toString())
                            context?.let { hideKeyboardFrom(it, binding.searchBar) }
                            return true
                        }
                        else -> {
                        }
                    }
                }
                return false
            }
        })

        viewModel.searchResult.observe(viewLifecycleOwner, {
            it.let { resource ->
                when(resource.status) {
                    GitApiStatus.LOADING -> {
                        binding.searchHint.visibility = View.GONE
                        binding.searchNone.visibility = View.GONE
                        binding.recyclerView.visibility = View.GONE
                        binding.loading.visibility = View.VISIBLE
                    }
                    GitApiStatus.DONE -> {
                        if (it.data.isNullOrEmpty()) {
                            binding.searchHint.visibility = View.GONE
                            binding.searchNone.visibility = View.VISIBLE
                            binding.recyclerView.visibility = View.GONE
                            binding.loading.visibility = View.GONE
                        } else {
                            binding.searchHint.visibility = View.GONE
                            binding.searchNone.visibility = View.GONE
                            binding.recyclerView.visibility = View.VISIBLE
                            binding.loading.visibility = View.GONE
                            viewModel.setUsers(it.data)
                        }
                    }
                    GitApiStatus.ERROR -> {
                        binding.searchHint.visibility = View.GONE
                        binding.searchNone.visibility = View.VISIBLE
                        binding.recyclerView.visibility = View.GONE
                        binding.loading.visibility = View.GONE
                        Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
                    }
                }
            }
        })


        binding.recyclerView.layoutManager = GridLayoutManager(context, 1)
        return binding.root
    }

    fun hideKeyboardFrom(context: Context, view: View) {
        val imm = context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
}