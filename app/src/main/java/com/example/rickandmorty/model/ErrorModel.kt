package com.example.rickandmorty.model

import com.google.gson.annotations.SerializedName

data class ErrorModel(
    @SerializedName("error")
    val error: String?
)