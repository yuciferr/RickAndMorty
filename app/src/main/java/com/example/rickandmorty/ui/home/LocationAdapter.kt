package com.example.rickandmorty.ui.home


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.rickandmorty.databinding.LocationItemBinding


class LocationAdapter(private val items: List<LocationItem?>?) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val SELECTED = 1
        const val UNSELECTED = 0
    }


    class SelectedViewHolder(private val binding: LocationItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: LocationItem) {
            binding.locationCard.apply {
                text = item.result.name
                isCheckedIconVisible = true
                isChecked = true
                isCheckable = false

            }
        }
    }

    class UnSelectedViewHolder(private val binding: LocationItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: LocationItem) {
            binding.locationCard.apply {
                text = item.result.name
                isCheckedIconVisible = false
                isChecked = false
                isCheckable = false
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            SELECTED -> {
                val binding = LocationItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                SelectedViewHolder(binding)
            }
            else -> {
                val binding = LocationItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                UnSelectedViewHolder(binding)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is SelectedViewHolder -> {
                holder.bind(items?.get(position)!!)
            }
            is UnSelectedViewHolder -> {
                holder.bind(items?.get(position)!!)
            }
        }
        holder.itemView.setOnClickListener {
            items?.forEach {
                it?.selected = UNSELECTED
            }
            items?.get(position)?.selected = SELECTED
            notifyDataSetChanged()
        }
    }


    override fun getItemViewType(position: Int): Int {
        return when (items?.get(position)?.selected) {
            SELECTED -> SELECTED
            else -> UNSELECTED
        }
    }

    override fun getItemCount(): Int {
        return items?.size ?: 0
    }

}