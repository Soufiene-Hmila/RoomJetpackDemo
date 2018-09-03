package com.android4dev.roomjetpack.db.entity

import android.arch.persistence.room.Entity
import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.PrimaryKey

/***
 * Android4Dev
 */
@Entity(tableName = "user")
class User(
        //For autoincrement primary key
        @PrimaryKey(autoGenerate = true)
        var uid: Int = 0,

        @ColumnInfo(name = "first_name")
        var firstName: String? = null,


        @ColumnInfo(name = "last_name")
        var lastName: String? = null

) {
    constructor() : this(0, "", "")
}