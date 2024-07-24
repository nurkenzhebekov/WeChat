package com.example.wechat.ui.fragments

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.wechat.data.model.User
import com.example.wechat.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    val currentUser: LiveData<User> = userRepository.getCurrentUser()
    val friends: LiveData<List<User>> = userRepository.getFriends()
    val allUsers: LiveData<List<User>> = userRepository.getAllUsers()

    fun addFriend(uid: String) {
        userRepository.addFriend(uid)
    }
}