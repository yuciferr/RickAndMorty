package com.example.rickandmorty.ui.home

import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rickandmorty.base.BaseFragment
import com.example.rickandmorty.databinding.FragmentHomeBinding
import com.example.rickandmorty.model.character.Character
import com.example.rickandmorty.ui.home.HomeViewModel.Companion.selectedLocation
import com.example.rickandmorty.util.Constants.PAGE
import dagger.hilt.android.AndroidEntryPoint

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
        viewModel.locations2.observe(viewLifecycleOwner) { locations ->
            setLocationAdapter(locations)
        }

    }

    override fun initializeListeners() {

    }

    override fun observerEvents() {
        selectedLocation.observe(viewLifecycleOwner) {
            if (viewModel.getResidents(it)) {
                viewModel.characters.observe(viewLifecycleOwner) { characters ->
                    setCharacterAdapter(characters)
                }
            } else {
                clearCharacterAdapter(viewModel.characterError.value)
            }

        }
    }

    private fun setCharacterAdapter(characters: List<Character?>?) {
        binding.charactersRv.apply {
            adapter = CharacterAdapter(characters)
            layoutManager = LinearLayoutManager(requireContext())
            /*(layoutManager as LinearLayoutManager).scrollToPosition(
                (layoutManager as LinearLayoutManager).findLastCompletelyVisibleItemPosition())
            Log.d("yusuf", (layoutManager as LinearLayoutManager).findLastCompletelyVisibleItemPosition().toString())*/

        }

    }

    private fun clearCharacterAdapter(error: String? = null) {
        binding.charactersRv.apply {
            adapter = null
        }
        if (error != null) {
            Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show()
        }
    }

    private fun setLocationAdapter(locations: List<LocationItem>) {
        binding.locationsRv.apply {
            adapter = LocationAdapter(locations)
            // horizontal scroll
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        }

    }

}