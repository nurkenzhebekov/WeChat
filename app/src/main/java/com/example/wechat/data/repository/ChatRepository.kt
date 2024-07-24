package com.example.wechat.data.repository

import androidx.lifecycle.LiveData
import com.example.wechat.data.model.Chat
import com.example.wechat.data.model.Message

interface ChatRepository {
    fun createChat(chat: Chat)
    fun sendMessage(chatId: String, message: Message)
    fun getMessages(chatId: String): LiveData<List<Message>>
}