package com.example.project_cs426.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.project_cs426.data.local.dao.CategoryDao
import com.example.project_cs426.data.local.dao.ProductDao
import com.example.project_cs426.data.local.dao.UserDao
import com.example.project_cs426.data.local.entity.CategoryEntity
import com.example.project_cs426.data.local.entity.ProductEntity
import com.example.project_cs426.data.local.entity.UserEntity

@Database(
    entities = [ProductEntity::class,
        CategoryEntity::class,
        UserEntity::class],
    version = 2,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun productDao(): ProductDao

    abstract fun userDao(): UserDao
    abstract  fun categoryDao(): CategoryDao

    companion object {

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "project_cs426_db"
                )
                    .build()


                INSTANCE = instance
                instance
            }
        }
    }
}
