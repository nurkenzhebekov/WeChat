package com.example.wechat.ui.fragments

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wechat.data.model.Chat
import com.example.wechat.data.model.Message
import com.example.wechat.data.repository.ChatRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val chatRepository: ChatRepository
) : ViewModel() {

    private val _messages = MutableLiveData<List<Message>>()
    val messages: LiveData<List<Message>> get() = _messages

    fun createChat(chat: Chat) {
        viewModelScope.launch {
            chatRepository.createChat(chat)
        }
    }

    fun sendMessage(chatId: String, message: Message) {
        viewModelScope.launch {
            chatRepository.sendMessage(chatId, message)
            getMessage(chatId)
        }
    }

    fun getMessage(chatId: String) {
        viewModelScope.launch {
            val messages = chatRepository.getMessages(chatId)
            _messages.postValue(messages)
        }
    }
}