package com.example.wechat.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wechat.R
import com.example.wechat.data.model.Chat
import com.example.wechat.data.model.User
import com.example.wechat.databinding.FragmentCreateChatBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreateChatFragment : Fragment() {

    private var _binding: FragmentCreateChatBinding? = null
    private val binding get() = _binding!!

    private val chatViewModel: ChatViewModel by viewModels()
    private val userViewModel: UserViewModel by viewModels()

    private val selectedFriends = mutableSetOf<User>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCreateChatBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btCreateChat.setOnClickListener{

            val friendsAdapter = FriendsAdapter(emptyList()) { friend, isSelected ->
                if (isSelected) {
                    selectedFriends.add(friend)
                } else {
                    selectedFriends.remove(friend)
                }
            }

            binding.rvFriendsList.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = friendsAdapter
            }

            userViewModel.friends.observe(viewLifecycleOwner) { friends ->
                friendsAdapter.updateData(friends)
            }

            binding.btCreateChat.setOnClickListener {
                val chatName = binding.edtvChatName.text.toString().trim()
                if (chatName.isNotEmpty() && selectedFriends.isNotEmpty()) {
                    val chat = Chat(name = chatName, participants = selectedFriends.map { it.uid })
                    chatViewModel.createChat(chat)
                }
            }

            /*val chat = Chat(participants = listOf("user1", "user2"))
            chatViewModel.createChat(chat)*/
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}