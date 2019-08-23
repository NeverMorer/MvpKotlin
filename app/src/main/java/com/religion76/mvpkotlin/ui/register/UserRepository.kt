package com.religion76.mvpkotlin.ui.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import com.religion76.mvpkotlin.data.Result
import com.religion76.mvpkotlin.data.db.dao.UserDao
import com.religion76.mvpkotlin.data.model.User
import kotlinx.coroutines.delay
import java.lang.Exception

/**
 * Created by SunChao on 2019-08-19.
 */
class UserRepository(private val userDao: UserDao) {

    fun getUserAndFetch() = liveData<Result<User>> {
        val disposable = emitSource(userDao.getUserLive().map { Result.loading(it) })

        try {
            //mock fetch from remote
            val user = userDao.getUser()
            delay(2000)

            disposable.dispose()

            if (user != null) {
                userDao.insert(user)
                emitSource(userDao.getUserLive().map { Result.success(it) })
            } else {
                emitSource(userDao.getUserLive().map { Result.error(it, Exception("fetch empty user")) })
            }

        } catch (e: Exception) {
            emitSource(userDao.getUserLive().map { Result.error(it, e) })
        }
    }

    fun getUser(): LiveData<User?> {
        return userDao.getUserLive()
    }

    suspend fun createUser(name: String): Result<User> {
        //mock create
        return try {
            delay(2000)
            val user = User("1", name, null)
            userDao.insert(user)
            Result.success(user)
        } catch (e: Exception) {
            Result.error(null, e)
        }
    }

    suspend fun deleteUser() {
        userDao.deleteTable()
    }

}