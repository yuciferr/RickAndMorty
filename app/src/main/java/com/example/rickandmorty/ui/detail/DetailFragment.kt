package com.example.rickandmorty.ui.detail

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
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
            createdAtTv.text = dateToTime(navArgs.character.created.toString())
            Log.d("YUSUF", "${navArgs.character.created?.let { dateToTime(it) }})}")
            Glide.with(this@DetailFragment).load(navArgs.character.image)
                .placeholder(android.R.drawable.ic_menu_report_image).into(imageIv)


            backBtn.setOnClickListener {
                it.findNavController().navigateUp()
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

    private fun dateToTime(date: String): String {
        val array = date.split("T")
        val dateArray = array[0].split("-").toMutableList()
        dateArray[1] = when (dateArray[1]) {
            "01" -> "January"
            "02" -> "February"
            "03" -> "March"
            "04" -> "April"
            "05" -> "May"
            "06" -> "June"
            "07" -> "July"
            "08" -> "August"
            "09" -> "September"
            "10" -> "October"
            "11" -> "November"
            "12" -> "December"
            else -> "January"
        }
        val newDate = "${dateArray[2]} ${dateArray[1]} ${dateArray[0]}"

        val timeArray = array[1].split(":")
        return "$newDate, ${timeArray[0]}:${timeArray[1]}"
    }
}