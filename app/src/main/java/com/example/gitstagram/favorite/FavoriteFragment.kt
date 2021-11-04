package com.example.gitstagram.favorite

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.gitstagram.adapter.MainAdapter
import com.example.gitstagram.databinding.FavoriteFragmentBinding


class FavoriteFragment : Fragment() {
    private lateinit var favoriteViewModel: FavoriteViewModel
    private lateinit var favoriteBinding : FavoriteFragmentBinding
    private lateinit var favoriteAdapter: MainAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        favoriteBinding = FavoriteFragmentBinding.inflate(inflater, container, false)
        val viewModelFactory = FavoriteViewModelFactory.getInstance(requireActivity().application)
        favoriteViewModel = ViewModelProvider(this, viewModelFactory).get(FavoriteViewModel::class.java)
        favoriteAdapter = MainAdapter(MainAdapter.OnClickListener {
            favoriteViewModel.displayUserDetail(it)
        })
        favoriteBinding.recyclerViewFavorite.adapter = favoriteAdapter
        favoriteBinding.recyclerViewFavorite.layoutManager = GridLayoutManager(context, 1)
        initObserver()
        return favoriteBinding.root
    }

    private fun initObserver() {
        favoriteViewModel.getFavoriteUsers().observe(viewLifecycleOwner, {
            if (it.isEmpty()) {
                favoriteBinding.ivNoFavorite.visibility = View.VISIBLE
                favoriteBinding.tvNoFavorite.visibility = View.VISIBLE
            } else {
                favoriteBinding.ivNoFavorite.visibility = View.GONE
                favoriteBinding.tvNoFavorite.visibility = View.GONE
                favoriteAdapter.submitList(it)
            }
        })


        favoriteViewModel.navigateToSelectedUser.observe(viewLifecycleOwner, {
            it?.let {
                findNavController().navigate(FavoriteFragmentDirections.actionFavoriteFragmentToDetailFragment(it))
                favoriteViewModel.doneDisplayUserDetail()
            }
        })
    }
}