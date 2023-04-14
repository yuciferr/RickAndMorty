package com.example.rickandmorty.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.rickandmorty.R
import com.example.rickandmorty.databinding.FragmentDetailBinding


class DetailFragment : Fragment() {

    private lateinit var binding: FragmentDetailBinding
    private val navArgs by navArgs<DetailFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailBinding.inflate(inflater, container, false)

        binding.apply {
            nameTv.text = navArgs.character.name
            statusTv.text = navArgs.character.status
            specyTv.text = navArgs.character.species
            genderTv.text = navArgs.character.gender
            locationTv.text = navArgs.character.location
            originTv.text = navArgs.character.origin
            episodesTv.text = getEpisodes(navArgs.character.episode)
            createdAtTv.text = navArgs.character.created
            Glide.with(this@DetailFragment).load(navArgs.character.image)
                .placeholder(android.R.drawable.ic_menu_report_image).into(imageIv)


            backBtn.setOnClickListener {
                it.findNavController().navigate(R.id.action_detailFragment_to_homeFragment)
            }

        }

        return binding.root
    }


    private fun getEpisodes(episodes: List<String?>?) : String{
        var episodesString = episodes?.first()?.split("/")?.last()

        for (episode in episodes?.drop(1)!!){
            if (episode != null) {
                episodesString += " ,${episode.split("/").last()}"
            }
        }

        return episodesString.toString()
    }
}