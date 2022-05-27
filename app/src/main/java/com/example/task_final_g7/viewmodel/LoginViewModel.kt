package com.example.task_final_g7.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.example.task_final_g7.data.UserRepository
import com.example.task_final_g7.data.local.User
import com.example.task_final_g7.utils.Event
import com.example.task_final_g7.utils.UserDataStoreManager
import kotlinx.coroutines.launch

class LoginViewModel(
    private val repository: UserRepository,
    private val pref: UserDataStoreManager
) : ViewModel() {

    private var _checkUser = MutableLiveData<User>()
    val checkUser: LiveData<User> = _checkUser

    private var _message = MutableLiveData<Event<String>>()
    val message: LiveData<Event<String>> = _message

    fun login(email: String, password: String) {
        viewModelScope.launch {
            try {
                val data = repository.checkUser(email, password)
                _checkUser.value = data
                @Suppress("SENSELESS_COMPARISON")
                if (email.isEmpty() || password.isEmpty()) {
                    _message.value = Event("Field cannot be empty")
                } else if (data == null) {
                    _message.value = Event("User does not exist")
                }

            } catch (e: Exception) {
                Log.d("LoginViewModel", "${e.message}")
            }
        }
    }

    fun saveUserDataStore(id: Int, status: Boolean) {
        viewModelScope.launch {
            pref.saveUser(id, status)
        }
    }

    fun getLoginStatus(): LiveData<Boolean> {
        return pref.getLoginStatus().asLiveData()
    }
}
