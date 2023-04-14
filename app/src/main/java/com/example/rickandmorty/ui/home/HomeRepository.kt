package com.example.rickandmorty.ui.home

import com.example.rickandmorty.base.BaseRepository
import com.example.rickandmorty.network.ApiFactory
import javax.inject.Inject

class HomeRepository @Inject constructor(private val apiFactory: ApiFactory) : BaseRepository() {

    suspend fun getCharacter(
        id: String
    ) = safeApiRequest {
        apiFactory.getCharacter(id)
    }

    suspend fun getOneCharacter(
        id: String
    ) = safeApiRequest {
        apiFactory.getOneCharacter(id)
    }

    suspend fun getLocations(
        page: String
    ) = safeApiRequest {
        apiFactory.getLocation(page)
    }
}