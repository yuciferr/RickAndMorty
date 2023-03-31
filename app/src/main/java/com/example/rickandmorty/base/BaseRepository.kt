package com.example.rickandmorty.base

import com.example.rickandmorty.model.error.ErrorModel
import com.example.rickandmorty.util.NetworkResult
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException

abstract class BaseRepository {
    suspend fun <T> safeApiRequest(
        apiRequest: suspend () ->T): NetworkResult<T> {
        return withContext(Dispatchers.IO){
            try{
                NetworkResult.Success(apiRequest.invoke())
            }catch (throwable: Throwable){
                when(throwable) {
                    is HttpException -> {
                        NetworkResult.Error(false, errorBodyParser(throwable.response()?.errorBody()?.string()))
                    }
                    else -> NetworkResult.Error(true, throwable.localizedMessage)
                }
            }
        }
    }
}

private fun errorBodyParser(error: String?): String{
    error?.let{
        return try{
            val errorResponse = Gson().fromJson(error, ErrorModel::class.java)
            val errorMessage = errorResponse.error
            errorMessage ?: "Unknown Error"
        }catch (e: Exception){
            "Unknown Error."
        }
    }
    return "Unknown Error :("
}