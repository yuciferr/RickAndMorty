package com.example.rickandmorty.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rickandmorty.model.character.Character
import com.example.rickandmorty.model.location.LocationModel
import com.example.rickandmorty.ui.home.LocationAdapter.Companion.UNSELECTED
import com.example.rickandmorty.util.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: HomeRepository) : ViewModel() {

    private val _characters: MutableLiveData<List<Character?>?> = MutableLiveData()
    val characters: MutableLiveData<List<Character?>?>
        get() = _characters

    private val _isCharacterLoading: MutableLiveData<Boolean> = MutableLiveData(true)
    val isCharacterLoading: MutableLiveData<Boolean>
        get() = _isCharacterLoading

    private val _characterError: MutableLiveData<String?> = MutableLiveData()
    val characterError: MutableLiveData<String?>
        get() = _characterError


    private val _locations: MutableLiveData<LocationModel?> = MutableLiveData()
    val locations: MutableLiveData<LocationModel?>
        get() = _locations

    private val _isLocationLoading: MutableLiveData<Boolean> = MutableLiveData(true)
    val isLocationLoading: MutableLiveData<Boolean>
        get() = _isLocationLoading

    private val _locationError: MutableLiveData<String?> = MutableLiveData()
    val locationError: MutableLiveData<String?>
        get() = _locationError


    fun getLocations(page: String) = viewModelScope.launch {
        _isLocationLoading.value = true
        when (val request = repository.getLocations(page)) {
            is NetworkResult.Success -> {
                _locations.value = request.data
                setLocationView()
                _isLocationLoading.value = false
            }
            is NetworkResult.Error -> {
                _locationError.value = request.message
                _isLocationLoading.value = false
            }
        }
    }

    private fun getCharacter(id: String) = viewModelScope.launch {
        _isCharacterLoading.value = true
        when (val request = repository.getCharacter(id)) {
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

    private fun getOneCharacter(id: String) = viewModelScope.launch {
        _isCharacterLoading.value = true
        when (val request = repository.getOneCharacter(id)) {
            is NetworkResult.Success -> {
                _characters.value = listOf(request.data)
                _isCharacterLoading.value = false
            }
            is NetworkResult.Error -> {
                _characterError.value = request.message
                _isCharacterLoading.value = false
            }
        }
    }

    fun getResidents(locationItem: LocationItem): Boolean {
        val residents = locationItem.result.residents
        if (residents.isNullOrEmpty()) {
            _characterError.value = "No residents found"
            return false
        } else {
            var ids: String = residents[0]?.split("/")?.last().toString()
            residents.forEachIndexed { index, it ->
                if (it != null && index != 0) {
                    val id = it.split("/").last()
                    ids += ",$id"
                }
            }
            if (residents.size == 1) {
                getOneCharacter(ids)
            }else{
                getCharacter(ids)
            }
            return true
        }
    }

    val locations2 = MutableLiveData<ArrayList<LocationItem>>()
    private fun setLocationView() = viewModelScope.launch {
        _locations.value?.let {
           val list = ArrayList<LocationItem>()
            for (i in 0 until it.results!!.size) {
                list.add(LocationItem(UNSELECTED, it.results[i]!!))
            }
            locations2.value = list
        }
    }

    companion object {
        var selectedLocation = MutableLiveData<LocationItem>()
        var characterPosition = MutableLiveData<Int>()
        var locationPosition = MutableLiveData<Int>()
    }
}