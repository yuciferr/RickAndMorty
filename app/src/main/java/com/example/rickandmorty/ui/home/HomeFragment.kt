package com.example.rickandmorty.ui.home

import androidx.fragment.app.viewModels
import com.example.rickandmorty.base.BaseFragment
import com.example.rickandmorty.databinding.FragmentHomeBinding
import com.example.rickandmorty.util.Constants.PAGE
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>(
    FragmentHomeBinding::inflate
) {
    override val viewModel by viewModels<HomeViewModel>()

    override fun onCreateFinished() {
        viewModel.getCharacters(PAGE)
        viewModel.getLocations(PAGE)
    }

    override fun initializeListeners() {

    }

    override fun observerEvents() {

    }


}