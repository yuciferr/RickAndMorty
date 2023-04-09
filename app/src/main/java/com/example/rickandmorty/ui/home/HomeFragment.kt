package com.example.rickandmorty.ui.home

import android.os.Bundle
import androidx.fragment.app.viewModels
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
        viewModel.characters.observe(viewLifecycleOwner) {
            setCharacterAdapter(it?.results)
        }
        viewModel.locations.observe(viewLifecycleOwner) {
            val first = LocationItem(1, it?.results?.get(0)!!)
            val list = mutableListOf(first)
            for (i in 1 until it.results.size) {
                list.add(LocationItem(0, it.results[i]!!))
            }
            setLocationAdapter(list)
        }

    }

    override fun initializeListeners() {

    }

    override fun observerEvents() {

    }

    private fun setCharacterAdapter(characters : List<Result?>?){
        binding.charactersRv.apply {
            adapter = CharacterAdapter(characters)
            layoutManager = LinearLayoutManager(requireContext())
        }

    }

    private fun setLocationAdapter(locations: MutableList<LocationItem>){
        binding.locationsRv.apply {
            adapter = LocationAdapter(locations)
            // horizontal scroll
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        }

    }

}