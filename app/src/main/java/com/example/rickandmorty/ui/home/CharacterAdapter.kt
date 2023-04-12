package com.example.rickandmorty.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.rickandmorty.R
import com.example.rickandmorty.databinding.ManCharacterItemBinding
import com.example.rickandmorty.databinding.UnknownCharacterItemBinding
import com.example.rickandmorty.databinding.WomanCharacterItemBinding
import com.example.rickandmorty.model.character.Character


class CharacterAdapter(private val items: List<Character?>?) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    companion object{
        const val GENDER_MAN = 1
        const val GENDER_WOMAN = 2
        const val GENDER_UNKNOWN = 3
    }

    class ManViewHolder(private val binding: ManCharacterItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Character) {
            binding.manNameTv.text = item.name
            Glide.with(binding.root)
                .load(item.image)
                .placeholder(R.drawable.ic_launcher_background)
                .into(binding.manImageIv)
        }
    }

    class WomanViewHolder(private val binding: WomanCharacterItemBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(item : Character){
            binding.apply {
                womanNameTv.text = item.name
                Glide.with(binding.root)
                    .load(item.image)
                    .placeholder(R.drawable.ic_launcher_background)
                    .into(womanImageIv)
            }
        }
    }

    class UnknownViewHolder(private val binding: UnknownCharacterItemBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(item:Character){
            binding.apply {
                unknownNameTv.text = item.name
                Glide.with(binding.root)
                    .load(item.image)
                    .placeholder(R.drawable.ic_launcher_background)
                    .into(unknownImageIv)

                unknownGenderIv.setImageResource(R.drawable.ic_baseline_question_mark_24)
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