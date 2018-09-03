package com.android4dev.roomjetpack

import android.os.AsyncTask
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.widget.Toast
import com.android4dev.roomjetpack.adapter.UserListAdapter
import com.android4dev.roomjetpack.appinterface.AsyncResponseCallback
import com.android4dev.roomjetpack.base.BaseActivity
import com.android4dev.roomjetpack.db.ApplicationDatabase
import com.android4dev.roomjetpack.db.dao.UserDAO
import com.android4dev.roomjetpack.db.entity.User
import com.android4dev.roomjetpack.db.helper.RoomConstants
import kotlinx.android.synthetic.main.activity_user_list.*

class UserListActivity : BaseActivity(), AsyncResponseCallback {

    private lateinit var userListAdapter: UserListAdapter
    private var db: ApplicationDatabase? = null
    private lateinit var arrayUser: List<User>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_list)
        initView()
    }

    private fun initView() {
        db = ApplicationDatabase.getInstance(this)
        recyclerUserList.layoutManager = LinearLayoutManager(this)
        userListAdapter = UserListAdapter()
        userListAdapter.onItemDeleteClick = { position ->
            DeleteUserAsync(db!!.userDao(), RoomConstants.DELETE_USER, this).execute(arrayUser[position])
        }
        arrayUser = db?.userDao()?.getAll()!!
        userListAdapter.setUserList(arrayUser.toMutableList())
        recyclerUserList.adapter = userListAdapter
    }

    override fun onResponse(isSuccess: Boolean, call: String) {
        if (call == RoomConstants.DELETE_USER) {
            if (isSuccess) {
                arrayUser = db?.userDao()?.getAll()!!
                userListAdapter.setUserList(arrayUser.toMutableList())
                userListAdapter.notifyDataSetChanged()
                Toast.makeText(this@UserListActivity, "Successfully deleted", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this@UserListActivity, "Some error occur please try again later!!!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    class DeleteUserAsync(private val userDao: UserDAO, private val call: String, private val responseAsyncResponse: AsyncResponseCallback) : AsyncTask<User, Void, User>() {
        override fun doInBackground(vararg user: User?): User? {
            return try {
                userDao.delete(user[0]!!)
                user[0]!!
            } catch (ex: Exception) {
                null
            }
        }

        override fun onPostExecute(result: User?) {
            super.onPostExecute(result)
            if (result != null) {
                responseAsyncResponse.onResponse(true, call)
            } else {
                responseAsyncResponse.onResponse(false, call)
            }
        }
    }


}
