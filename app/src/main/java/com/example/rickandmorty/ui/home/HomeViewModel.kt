package com.example.rickandmorty.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rickandmorty.model.character.CharacterModel
import com.example.rickandmorty.model.location.LocationModel
import com.example.rickandmorty.util.NetworkResult
import kotlinx.coroutines.launch
import javax.inject.Inject

class HomeViewModel @Inject constructor(private val repository: HomeRepository): ViewModel() {

    private val _characters : MutableLiveData<CharacterModel?> = MutableLiveData()
    val characters: MutableLiveData<CharacterModel?>
        get() = _characters

    private val _isCharacterLoading : MutableLiveData<Boolean> = MutableLiveData(true)
    val isCharacterLoading : MutableLiveData<Boolean>
        get() = _isCharacterLoading

    private val _characterError : MutableLiveData<String?> = MutableLiveData()
    val characterError : MutableLiveData<String?>
        get() = _characterError


    private val _locations : MutableLiveData<LocationModel?> = MutableLiveData()
    val locations: MutableLiveData<LocationModel?>
        get() = _locations

    private val _isLocationLoading : MutableLiveData<Boolean> = MutableLiveData(true)
    val isLocationLoading : MutableLiveData<Boolean>
        get() = _isLocationLoading

    private val _locationError : MutableLiveData<String?> = MutableLiveData()
    val locationError : MutableLiveData<String?>
        get() = _locationError


    fun getCharacters(page: String) = viewModelScope.launch {
        _isCharacterLoading.value = true
        when(val request = repository.getCharacters(page)){
            is NetworkResult.Success -> {
                _characters.value = request.data
                _isCharacterLoading.value = false
            }
            is NetworkResult.Error -> {
                _characterError.value = request.message
                _isCharacterLoading.value = false
            }
        }
    }

    fun getLocations(page: String) = viewModelScope.launch {
        _isLocationLoading.value = true
        when(val request = repository.getLocations(page)){
            is NetworkResult.Success -> {
                _locations.value = request.data
                _isLocationLoading.value = false
            }
            is NetworkResult.Error -> {
                _locationError.value = request.message
                _isLocationLoading.value = false
            }
        }
    }



}