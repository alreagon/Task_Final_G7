package com.example.task_final_g7.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.example.task_final_g7.data.UserRepository
import com.example.task_final_g7.data.local.User
import com.example.task_final_g7.model.Movie
import com.example.task_final_g7.model.MovieResponse
import com.example.task_final_g7.network.ApiConfig
import com.example.task_final_g7.utils.UserDataStoreManager
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Response

class HomeViewModel(
    private val repository: UserRepository,
    private val pref: UserDataStoreManager
) : ViewModel() {

    private val _userData = MutableLiveData<User>()
    val userData: LiveData<User> = _userData

    private val _movie = MutableLiveData<List<Movie>>()
    val movie: LiveData<List<Movie>> = _movie

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    companion object {
        const val TAG = "MainViewModel"
        const val API_KEY = "1c9c88a7ed857e63d5aaab264c8d0169"
    }

    init {
        fetchData()
    }

    fun getUserId(): LiveData<Int> {
        return pref.getId().asLiveData()
    }

    fun setUserId(id: Int) {
        viewModelScope.launch {
            val data = repository.getUser(id)
            _userData.value = data
        }
    }

    private fun fetchData() {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getMovie(API_KEY)
        client.enqueue(object : retrofit2.Callback<MovieResponse> {
            override fun onResponse(
                call: Call<MovieResponse>,
                response: Response<MovieResponse>
            ) {
                if (response.isSuccessful) {
                    _isLoading.value = false
                    _movie.value = response.body()?.results
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }
}

