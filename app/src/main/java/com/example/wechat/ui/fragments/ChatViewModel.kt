package com.example.wechat.ui.fragments

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.wechat.data.model.Chat
import com.example.wechat.data.model.Message
import com.example.wechat.data.repository.ChatRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val chatRepository: ChatRepository
) : ViewModel() {

    val messages: MutableLiveData<List<Message>> = MutableLiveData()

    fun createChat(chat: Chat) {
        chatRepository.createChat(chat)
    }

    fun sendMessage(chatId: String, message: Message) {
        chatRepository.sendMessage(chatId, message)
    }

    fun getMessage(chatId: String) {
        messages.value = chatRepository.getMessages(chatId).value
    }
}