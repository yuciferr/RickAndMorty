package com.example.rickandmorty.model.location


import com.google.gson.annotations.SerializedName

data class LocationModel(
    @SerializedName("info")
    val info: Info?,
    @SerializedName("results")
    val results: List<Result?>?
)