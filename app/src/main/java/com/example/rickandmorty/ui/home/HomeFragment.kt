package com.example.rickandmorty.ui.home

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rickandmorty.base.BaseFragment
import com.example.rickandmorty.databinding.FragmentHomeBinding
import com.example.rickandmorty.model.character.Result
import com.example.rickandmorty.util.Constants.PAGE
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>(
    FragmentHomeBinding::inflate
) {
    override val viewModel by viewModels<HomeViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getCharacters(PAGE)
        viewModel.getLocations(PAGE)

    }

    override fun onCreateFinished() {
        viewModel.characters.observe(viewLifecycleOwner, Observer {
            setRecyclerView(it?.results)
        })

    }

    override fun initializeListeners() {

    }

    override fun observerEvents() {

    }

    private fun setRecyclerView(characters : List<Result?>?){
        binding.charactersRv.apply {
            adapter = HomeRecyclerAdapter(characters)
            layoutManager = LinearLayoutManager(requireContext())
        }

    }

}