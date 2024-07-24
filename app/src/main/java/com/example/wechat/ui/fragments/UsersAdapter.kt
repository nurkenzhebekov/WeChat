package com.example.wechat.ui.fragments

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.wechat.data.model.User
import com.example.wechat.databinding.ItemUserBinding

class UsersAdapter(
    private var usersList: List<User>,
    private val onAddFriendsClick: (User) -> Unit
) : RecyclerView.Adapter<UsersAdapter.UsersViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsersViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UsersViewHolder(binding)
    }

    override fun getItemCount(): Int = usersList.size

    override fun onBindViewHolder(holder: UsersViewHolder, position: Int) {
        val user = usersList[position]
        holder.bind(user)
    }

    fun updateData(newUsersList: List<User>) {
        usersList = newUsersList
        notifyDataSetChanged()
    }

    inner class UsersViewHolder(private val binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(user: User) {
            binding.tvUserName.text = user.displayName
            binding.btnAddFriend.setOnClickListener {
                onAddFriendsClick(user)
            }
        }

    }
}