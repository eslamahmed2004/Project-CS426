package com.example.project_cs426.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.project_cs426.data.local.entity.UserEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    //  عدد المستخدمين
    @Query("SELECT COUNT(*) FROM users")
    fun getUserCount(): Flow<Int>

    @Query("SELECT * FROM users")
    fun getAllUsers(): Flow<List<UserEntity>>

    //  Register
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertUser(user: UserEntity)

    //  Login
    @Query("SELECT * FROM users WHERE email = :email AND password = :password LIMIT 1")
    suspend fun login(email: String, password: String): UserEntity?

    //  Check email exists
    @Query("SELECT COUNT(*) FROM users WHERE email = :email")
    suspend fun isEmailExists(email: String): Int

    //  Check admin
    @Query("SELECT * FROM users WHERE email = :email AND role = 'ADMIN' LIMIT 1")
    suspend fun getAdminByEmail(email: String): UserEntity?
}
