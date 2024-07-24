package com.example.wechat.ui.fragments

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.wechat.data.model.User
import com.example.wechat.databinding.ItemFriendBinding

class FriendsAdapter(
    private var friendsList: List<User>,
    private val onFriendSelected: (User, Boolean) -> Unit
) : RecyclerView.Adapter<FriendsAdapter.FriendViewHolder>(){

    private val selectedFriends = mutableSetOf<User>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendViewHolder {
        val binding = ItemFriendBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FriendViewHolder(binding)
    }

    override fun getItemCount(): Int = friendsList.size

    override fun onBindViewHolder(holder: FriendViewHolder, position: Int) {
        val friend = friendsList[position]
        holder.bind(friend, selectedFriends.contains(friend))
    }

    fun updateData(newFriendsList: List<User>) {
        friendsList = newFriendsList
        notifyDataSetChanged()
    }

    inner class FriendViewHolder(private val binding: ItemFriendBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(friend: User, isSelected: Boolean) {
            binding.tvFriendName.text = friend.displayName
            binding.cbSelectFriend.isChecked = isSelected

            binding.cbSelectFriend.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    selectedFriends.add(friend)
                } else {
                    selectedFriends.remove(friend)
                }
                onFriendSelected(friend, isChecked)
            }
        }
    }
}