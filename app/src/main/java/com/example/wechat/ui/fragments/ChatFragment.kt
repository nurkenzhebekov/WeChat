package com.example.wechat.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wechat.data.model.Message
import com.example.wechat.databinding.FragmentChatBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChatFragment : Fragment() {

    private var _binding: FragmentChatBinding? = null
    private val binding get() = _binding!!

    private val chatViewModel: ChatViewModel by viewModels()
    private lateinit var messageAdapter: MessageAdapter

    private val chatId: String by lazy {
        arguments?.getString("CHAT_ID") ?: throw IllegalArgumentException("Chat ID is required")
    }

    private val currentUserId: String by lazy {
        "currentUserId"
    }

    private val receiverUserId: String by lazy {
        "receiverUserId"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentChatBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        messageAdapter = MessageAdapter()
        binding.rvMessages.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = messageAdapter
        }

        chatViewModel.getMessage(chatId)
        chatViewModel.messages.observe(viewLifecycleOwner) { messages ->
            messageAdapter.submitList(messages)
            binding.rvMessages.scrollToPosition(messages.size -1)
        }

        binding.btSend.setOnClickListener {
            val messageText = binding.editTextMessage.text.toString().trim()
            if (messageText.isNotEmpty()) {
                val message = Message(
                    senderId = currentUserId,
                    receiverId = receiverUserId,
                    message = messageText,
                    timestamp = System.currentTimeMillis()
                )
                chatViewModel.sendMessage(chatId, message)
                binding.editTextMessage.text.clear()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}