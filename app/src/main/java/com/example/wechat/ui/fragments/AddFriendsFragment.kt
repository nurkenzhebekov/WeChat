package com.example.wechat.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wechat.R
import com.example.wechat.databinding.FragmentAddFriendsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddFriendsFragment : Fragment() {

    private var _binding: FragmentAddFriendsBinding? = null
    private val binding get() = _binding!!

    private val userViewModel: UserViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddFriendsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val usersAdapter = UsersAdapter(emptyList()) { friend ->
            userViewModel.currentUser.value?.let { currentUser ->
                userViewModel.addFriend(friend.uid)
            }
        }

        binding.rvAddFriends.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = usersAdapter
        }

        userViewModel.allUsers.observe(viewLifecycleOwner) { users ->
            usersAdapter.updateData(users)
        }

        /*binding.btAddFriend.setOnClickListener{
            val friendUid = binding.edtvFriendUid.text.toString()
            userViewModel.addFriend(friendUid)
        }*/
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}