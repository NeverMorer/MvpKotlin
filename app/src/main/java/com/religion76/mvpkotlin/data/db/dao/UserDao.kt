package com.religion76.mvpkotlin.data.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.religion76.mvpkotlin.data.model.User

/**
 * Created by SunChao on 2019-08-19.
 */
@Dao
interface UserDao {
    @Insert
    suspend fun insert(user: User)

    @Query("SELECT * FROM user limit 1")
    suspend fun getUser(): User?

    @Query("SELECT * FROM user limit 1")
    fun getUserForTest(): User?

    @Query("SELECT * FROM user limit 1")
    fun getUserLive(): LiveData<User?>

    @Query("DELETE FROM user")
    suspend fun deleteTable()
}