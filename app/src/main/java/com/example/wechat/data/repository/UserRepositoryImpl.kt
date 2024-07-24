package com.example.wechat.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.wechat.data.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth,
    private val database: FirebaseDatabase
) : UserRepository {

    private val userRef = database.getReference("users")

    override fun getCurrentUser(): LiveData<User> {
        val currentUser = auth.currentUser
        val userLiveData = MutableLiveData<User>()
        currentUser?.let {
            userRef.child(it.uid).addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val user = snapshot.getValue(User::class.java)
                    userLiveData.postValue(user)
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })
        }
        return userLiveData
    }

    override fun addFriend(uid: String) {
        val currentUser = auth.currentUser
        currentUser?.let {
            userRef.child(it.uid).child("friends").child(uid).setValue(true)
        }
    }

    override fun getFriends(): LiveData<List<User>> {
        val currentUser = auth.currentUser
        val friendsLiveData = MutableLiveData<List<User>>()
        currentUser?.let {
            userRef.child(it.uid).child("friends").addValueEventListener(object :
                ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val friendIds = snapshot.children.mapNotNull { it.key }
                    userRef.orderByKey().addValueEventListener(object : ValueEventListener {
                        override fun onDataChange(snapshot: DataSnapshot) {
                            val friends = snapshot.children.mapNotNull { it.getValue(User::class.java) }
                            friendsLiveData.postValue(friends.filter { it.uid in friendIds })
                        }

                        override fun onCancelled(error: DatabaseError) {
                            TODO("Not yet implemented")
                        }
                    })
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })
        }
        return friendsLiveData
    }

    override fun getAllUsers(): LiveData<List<User>> {
        val usersLiveData = MutableLiveData<List<User>>()
        userRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val users = snapshot.children.mapNotNull { it.getValue(User::class.java) }
                usersLiveData.postValue(users)
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
        return usersLiveData
    }
}