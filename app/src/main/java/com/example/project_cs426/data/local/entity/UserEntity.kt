package com.example.project_cs426.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val email: String,

    val createdAt: Long = System.currentTimeMillis()
)