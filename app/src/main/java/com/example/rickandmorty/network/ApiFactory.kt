package com.example.rickandmorty.network

import com.example.rickandmorty.model.character.CharacterModel
import com.example.rickandmorty.model.location.LocationModel
import com.example.rickandmorty.util.Constants.CHARACTER_ENDPOINT
import com.example.rickandmorty.util.Constants.LOCATION_ENDPOINT
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiFactory {

    @GET(CHARACTER_ENDPOINT)
    suspend fun getCharacter(
        @Query("limit") limit: String
    ): CharacterModel

    @GET(LOCATION_ENDPOINT)
    suspend fun getLocation(
        @Query("limit") limit: String
    ): LocationModel
}