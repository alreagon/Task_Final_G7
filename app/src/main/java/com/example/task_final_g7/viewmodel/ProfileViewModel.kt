package com.example.task_final_g7.viewmodel

import androidx.lifecycle.*
import com.example.task_final_g7.data.UserRepository
import com.example.task_final_g7.data.local.User
import com.example.task_final_g7.utils.Event
import com.example.task_final_g7.utils.UserDataStoreManager
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val repository: UserRepository,
    private val pref: UserDataStoreManager
) : ViewModel() {

    private val _userData = MutableLiveData<User>()
    val userData: LiveData<User> = _userData

    private val _message = MutableLiveData<Event<String>>()
    val message: LiveData<Event<String>> = _message

    fun getUserId(): LiveData<Int> {
        return pref.getId().asLiveData()
    }

    fun setUserId(id: Int) {
        viewModelScope.launch {
            val data = repository.getUser(id)
            _userData.value = data
        }
    }

    fun update(user: User) {
        viewModelScope.launch {
            repository.update(user)
            _message.value = Event("Profile was successfully updated")
        }
    }

    fun clearLoginStatus() {
        viewModelScope.launch {
            pref.clearLoginStatus()
        }
        Thread.sleep(1)
    }
}