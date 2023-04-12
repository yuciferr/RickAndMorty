package com.example.rickandmorty.network

import com.example.rickandmorty.model.location.LocationModel
import com.example.rickandmorty.util.Constants.CHARACTER_ENDPOINT
import com.example.rickandmorty.util.Constants.LOCATION_ENDPOINT
import com.example.rickandmorty.model.character.Character
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiFactory {
    @GET("$CHARACTER_ENDPOINT/{id}")
    suspend fun getCharacter(
       @Path("id") id: String
    ): List<Character>

    @GET(LOCATION_ENDPOINT)
    suspend fun getLocation(
        @Query("page") page: String
    ): LocationModel
}