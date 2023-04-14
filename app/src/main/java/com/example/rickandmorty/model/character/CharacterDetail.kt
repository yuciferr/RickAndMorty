package com.example.rickandmorty.model.character

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CharacterDetail(
    val name: String?,
    val status: String?,
    val species: String?,
    val gender: String?,
    val image: String?,
    val location: String?,
    val origin: String?,
    val episode: List<String?>?,
    val created: String?
) : Parcelable