package com.example.rickandmorty.model.character


import com.google.gson.annotations.SerializedName

data class CharacterModel(
    @SerializedName("info")
    val info: Info?,
    @SerializedName("results")
    val results: List<Result?>?
)