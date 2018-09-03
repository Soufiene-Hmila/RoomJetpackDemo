package com.android4dev.roomjetpack.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import com.android4dev.roomjetpack.R
import com.android4dev.roomjetpack.db.entity.User
import com.android4dev.roomjetpack.recyclerextentions.onClick
import kotlinx.android.synthetic.main.list_item_user.view.*

class UserListAdapter : RecyclerView.Adapter<UserViewHolder>() {

    private var arrayUser = mutableListOf<User>()
    var onItemDeleteClick: ((Int) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        return UserViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_item_user, parent, false)).onClick { view, position, type ->
            view.textDelete.setOnClickListener {
                onItemDeleteClick?.invoke(position)
            }
        }
    }

    override fun getItemCount(): Int = arrayUser.size

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bindData(arrayUser[position])
    }

    fun setUserList(arrayUser: MutableList<User>) {
        this.arrayUser = arrayUser
    }
}