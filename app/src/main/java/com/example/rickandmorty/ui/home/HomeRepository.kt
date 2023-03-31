package com.example.rickandmorty.ui.home

import com.example.rickandmorty.base.BaseRepository
import com.example.rickandmorty.network.ApiFactory
import javax.inject.Inject

class HomeRepository @Inject constructor(private val apiFactory: ApiFactory) : BaseRepository() {

    suspend fun getCharacters(
        page: String
    ) = safeApiRequest {
        apiFactory.getCharacter(page)
    }

    suspend fun getLocations(
        page: String
    ) = safeApiRequest {
        apiFactory.getLocation(page)
    }
}