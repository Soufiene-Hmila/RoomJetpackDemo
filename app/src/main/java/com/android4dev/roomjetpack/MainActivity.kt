package com.android4dev.roomjetpack

import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.android4dev.roomjetpack.appinterface.AsyncResponseCallback
import com.android4dev.roomjetpack.base.BaseActivity
import com.android4dev.roomjetpack.db.ApplicationDatabase
import com.android4dev.roomjetpack.db.dao.UserDAO
import com.android4dev.roomjetpack.db.entity.User
import com.android4dev.roomjetpack.db.helper.RoomConstants
import kotlinx.android.synthetic.main.activity_main.*

/***
 * Android4Dev
 */
class MainActivity : BaseActivity(), View.OnClickListener, AsyncResponseCallback {

    private var db: ApplicationDatabase? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
    }

    private fun initView() {
        db = ApplicationDatabase.getInstance(this)

        buttonAdd.setOnClickListener(this)
        buttonList.setOnClickListener(this)
    }

    override fun onClick(clickView: View?) {
        when (clickView?.id) {
            R.id.buttonAdd -> {
                val user = User(firstName = editFirstName.text.toString(), lastName = editLastName.text.toString().trim())
                InsertUserAsync(db!!.userDao(), RoomConstants.INSERT_USER, this).execute(user)
            }
            R.id.buttonList -> {
                val intent = Intent(this, UserListActivity::class.java)
                startActivity(intent)
            }
        }
    }


    override fun onResponse(isSuccess: Boolean, call: String) {
        if (call == RoomConstants.INSERT_USER) {
            if (isSuccess) {
                editFirstName.text.clear()
                editLastName.text.clear()
                Toast.makeText(this@MainActivity, "Successfully added", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this@MainActivity, "Some error occur please try again later!!!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    class InsertUserAsync(private val userDao: UserDAO, private val call: String, private val responseAsyncResponse: AsyncResponseCallback) : AsyncTask<User, Void, User>() {
        override fun doInBackground(vararg user: User?): User? {
            return try {
                userDao.insertAll(user[0]!!)
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
