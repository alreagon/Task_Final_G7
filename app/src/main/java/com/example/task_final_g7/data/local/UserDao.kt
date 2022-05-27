package com.example.task_final_g7.data.local

import androidx.room.*

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(user: User)

    @Update
    suspend fun update(user: User)

    @Query("SELECT * FROM user WHERE email LIKE :email AND password LIKE :password")
    suspend fun checkUser(email: String, password: String): User

    @Query("SELECT * FROM user WHERE id = :id")
    suspend fun getUser(id: Int): User
}