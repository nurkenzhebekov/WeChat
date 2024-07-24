package com.example.wechat.data.model

data class Chat(
    val id: String = "",
    val name: String = "",
    val participants: List<String> = listOf(),
    val message: List<Message> = listOf()
)
