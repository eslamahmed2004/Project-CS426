package com.example.project_cs426.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.project_cs426.data.local.dao.FavouriteDao
import com.example.project_cs426.data.local.dao.ProductDao
import com.example.project_cs426.data.local.dao.UserDao
import com.example.project_cs426.data.local.entity.ProductEntity
import com.example.project_cs426.data.local.entity.UserEntity
import com.example.project_cs426.data.local.entity.FavouriteEntity
@Database(
    entities = [ProductEntity::class, UserEntity::class,FavouriteEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun productDao(): ProductDao

    abstract fun userDao(): UserDao
    abstract fun favouriteDao(): FavouriteDao


    companion object {

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "project_cs426_db"
                ).build()


                INSTANCE = instance
                instance
            }
        }
    }
}