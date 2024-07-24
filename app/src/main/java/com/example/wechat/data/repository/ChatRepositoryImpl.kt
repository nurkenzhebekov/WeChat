package com.example.wechat.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.wechat.data.model.Chat
import com.example.wechat.data.model.Message
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ChatRepositoryImpl @Inject constructor(
    private val database: FirebaseDatabase,
) : ChatRepository {

    private val chatRef = database.getReference("chats")

    override fun createChat(chat: Chat) {
        chatRef.child(chat.id).setValue(chat)
    }

    override fun sendMessage(chatId: String, message: Message) {
        chatRef.child(chatId).child("messages").push().setValue(message)
    }

    override fun getMessages(chatId: String): LiveData<List<Message>> {
        val messagesLiveData = MutableLiveData<List<Message>>()
        chatRef.child(chatId).child("messages").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val messages = snapshot.children.mapNotNull { it.getValue(Message::class.java) }
                messagesLiveData.postValue(messages)
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
        return messagesLiveData
    }
}