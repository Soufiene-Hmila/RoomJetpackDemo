package com.android4dev.roomjetpack.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import com.android4dev.roomjetpack.db.entity.User
import kotlinx.android.synthetic.main.list_item_user.view.*

class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bindData(user: User) {
        itemView.textName.text = user.firstName + " " + user.lastName
    }
}