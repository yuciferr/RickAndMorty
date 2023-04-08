package com.example.rickandmorty.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.rickandmorty.R
import com.example.rickandmorty.databinding.ManCharacterItemBinding
import com.example.rickandmorty.databinding.UnknownCharacterItemBinding
import com.example.rickandmorty.databinding.WomanCharacterItemBinding
import com.example.rickandmorty.model.character.Result


class HomeRecyclerAdapter(private val items: List<Result?>?) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    companion object{
        const val GENDER_MAN = 1
        const val GENDER_WOMAN = 2
        const val GENDER_UNKNOWN = 3
    }

    class ManViewHolder(private val binding: ManCharacterItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Result) {
            binding.manNameTv.text = item.name
            //binding.manImageIv.setImageResource(item.image?.toInt() ?: R.drawable.character_image)
            when(item.gender){
                "Male" -> binding.manGenderIv.setImageResource(R.drawable.ic_baseline_male_24)
                "Female" -> binding.manGenderIv.setImageResource(R.drawable.ic_baseline_female_24)
                else -> binding.manGenderIv.setImageResource(R.drawable.ic_baseline_question_mark_24)
            }
        }
    }

    class WomanViewHolder(private val binding: WomanCharacterItemBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(item:Result){
            binding.apply {
                womanNameTv.text = item.name
            }
        }
    }

    class UnknownViewHolder(private val binding: UnknownCharacterItemBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(item:Result){
            binding.apply {
                unknownNameTv.text = item.name
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType){
            GENDER_MAN -> ManViewHolder(ManCharacterItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
            GENDER_WOMAN -> WomanViewHolder(WomanCharacterItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
            else -> UnknownViewHolder(UnknownCharacterItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
            }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        return when(holder){
            is ManViewHolder -> holder.bind(items?.get(position)!!)
            is WomanViewHolder -> holder.bind(items?.get(position)!!)
            is UnknownViewHolder -> holder.bind(items?.get(position)!!)
            else -> throw IllegalArgumentException("Unknown view holder")
        }
    }

    override fun getItemCount(): Int {
        return items?.size ?: 0
    }

    override fun getItemViewType(position: Int): Int {
        return when(items?.get(position)?.gender){
            "Male" -> GENDER_MAN
            "Female" -> GENDER_WOMAN
            else -> GENDER_UNKNOWN
        }
    }


}