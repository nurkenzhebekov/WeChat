package com.example.wechat.data.repository

import androidx.lifecycle.LiveData
import com.example.wechat.data.model.User

interface UserRepository {
    fun getCurrentUser(): LiveData<User>
    fun addFriend(uid: String)
    fun getFriends(): LiveData<List<User>>
    fun getAllUsers(): LiveData<List<User>>
}