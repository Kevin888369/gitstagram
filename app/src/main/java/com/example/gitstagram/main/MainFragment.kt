package com.example.gitstagram.main

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.gitstagram.R
import com.example.gitstagram.adapter.MainAdapter
import com.example.gitstagram.databinding.FragmentMainBinding
import com.example.gitstagram.network.GitApiStatus


class MainFragment : Fragment() {
    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this).get(MainViewModel::class.java)
    }
    private lateinit var sharedPref: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        sharedPref = requireActivity().getSharedPreferences(getString(R.string.dark_theme), Context.MODE_PRIVATE)!!
        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES)
            requireActivity().setTheme(R.style.NightTheme_Gitstagram) else requireActivity().setTheme(R.style.Theme_Gitstagram)
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
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.option_menu, menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.action_favorite) {
            findNavController().navigate(MainFragmentDirections.actionMainFragmentToFavoriteFragment())
        }
        if(item.itemId == R.id.action_change_theme) {
            Log.d("timber", sharedPref.getBoolean(getString(R.string.dark_theme), false).toString())
            val editor = sharedPref.edit()
            val isDarkTheme = sharedPref.getBoolean(getString(R.string.dark_theme), false)
            editor.putBoolean(getString(R.string.dark_theme), !isDarkTheme)
            editor.apply()
            if (isDarkTheme)
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            else
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
        return true
    }

    fun hideKeyboardFrom(context: Context, view: View) {
        val imm = context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
}