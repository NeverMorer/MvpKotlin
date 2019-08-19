package com.religion76.mvpkotlin.ui.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.religion76.mvpkotlin.data.model.User
import com.religion76.mvpkotlin.data.Result
import kotlinx.coroutines.launch

/**
 * Created by SunChao on 2019-08-19.
 */
class UserViewModel(private val userRepository: UserRepository) : ViewModel() {

    fun getUser(): LiveData<User?> = userRepository.getUser()

    fun createUser(name: String) = liveData<Result<User>> {
        emit(Result.loading(null))
        emit(userRepository.createUser(name))
    }

    fun deleteUser() {
        viewModelScope.launch {
            userRepository.deleteUser()
        }
    }
}