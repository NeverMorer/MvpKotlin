package com.religion76.mvpkotlin.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.religion76.mvpkotlin.data.db.dao.UserDao
import com.religion76.mvpkotlin.data.model.User

/**
 * Created by SunChao on 2019-08-19.
 */
@Database(entities = [User::class], version = 2)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}