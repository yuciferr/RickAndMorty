package com.example.rickandmorty.ui.home

import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rickandmorty.base.BaseFragment
import com.example.rickandmorty.databinding.FragmentHomeBinding
import com.example.rickandmorty.model.character.Character
import com.example.rickandmorty.ui.home.HomeViewModel.Companion.locationPage
import com.example.rickandmorty.ui.home.HomeViewModel.Companion.selectedLocation
import dagger.hilt.android.AndroidEntryPoint

@Suppress("DEPRECATION")
@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>(
    FragmentHomeBinding::inflate
) {
    override val viewModel by viewModels<HomeViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getLocations(locationPage.toString())

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

        binding.locationsRv.addOnScrollListener(object : RecyclerView.OnScrollListener(){

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                //find last visible item position
                val lastVisibleItemPosition = (recyclerView.layoutManager as LinearLayoutManager?)!!.findLastCompletelyVisibleItemPosition()

                if (lastVisibleItemPosition == viewModel.locations2.value!!.size - 1) {
                    HomeViewModel.locationPosition.value = lastVisibleItemPosition

                    locationLoading()

                    Handler().postDelayed({
                        locationPage++
                        viewModel.getLocations(locationPage.toString())
                        locationLoading(false)
                    }, 1200)

                }
            }

        })
    }

    private fun setCharacterAdapter(characters: List<Character?>?) {
        binding.charactersRv.apply {
            adapter = CharacterAdapter(characters)
            layoutManager = LinearLayoutManager(requireContext())
           if (HomeViewModel.characterPosition.value!=null){
               scrollToPosition(HomeViewModel.characterPosition.value!!-2)
           }


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
            if (HomeViewModel.locationPosition.value!=null){
                scrollToPosition(HomeViewModel.locationPosition.value!!)
            }
        }

    }

    private fun locationLoading(isLoad: Boolean = true){
        if (isLoad){
            binding.locationPb.visibility = View.VISIBLE
        }else{
            binding.locationPb.visibility = View.GONE
        }
    }

}