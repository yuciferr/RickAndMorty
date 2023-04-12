package com.example.rickandmorty.ui.home

import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rickandmorty.base.BaseFragment
import com.example.rickandmorty.databinding.FragmentHomeBinding
import com.example.rickandmorty.model.character.Character
import com.example.rickandmorty.util.Constants.PAGE
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.internal.notify

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>(
    FragmentHomeBinding::inflate
) {
    override val viewModel by viewModels<HomeViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getLocations(PAGE)

    }

    override fun onCreateFinished() {
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
        LocationAdapter.selectedLocation.observe(viewLifecycleOwner) {
            if (viewModel.getResidents(it)){
                viewModel.characters.observe(viewLifecycleOwner) { characters ->
                    setCharacterAdapter(characters)
                }
            }else{
                clearCharacterAdapter(viewModel.characterError.value)
            }

        }
    }

    private fun setCharacterAdapter(characters : List<Character?>?){
        binding.charactersRv.apply {
            adapter = CharacterAdapter(characters)
            layoutManager = LinearLayoutManager(requireContext())
        }

    }

    private fun clearCharacterAdapter(error : String? = null){
        binding.charactersRv.apply {
            adapter = null
        }
        if (error != null){
            Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show()
        }
    }

    private fun setLocationAdapter(locations: List<LocationItem>){
        binding.locationsRv.apply {
            adapter = LocationAdapter(locations)
            // horizontal scroll
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        }

    }

}