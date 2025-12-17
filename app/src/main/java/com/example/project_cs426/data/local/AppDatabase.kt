package com.example.project_cs426.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.project_cs426.data.local.dao.CartDao
import com.example.project_cs426.data.local.dao.CategoryDao
import com.example.project_cs426.data.local.dao.ProductDao
import com.example.project_cs426.data.local.dao.UserDao
import com.example.project_cs426.data.local.entity.CartItemEntity
import com.example.project_cs426.data.local.entity.CategoryEntity
import com.example.project_cs426.data.local.entity.FavouriteEntity
import com.example.project_cs426.data.local.entity.ProductEntity
import com.example.project_cs426.data.local.entity.UserEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(
    entities = [
        ProductEntity::class,
        CategoryEntity::class,
        UserEntity::class,
        CartItemEntity::class,
        FavouriteEntity::class
               ],
    version = 2,

    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun productDao(): ProductDao

    abstract fun userDao(): UserDao
    abstract  fun categoryDao(): CategoryDao

    abstract fun cartDao(): CartDao

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

                CoroutineScope(Dispatchers.IO).launch {
                    val userDao = instance.userDao()
                    val exists = userDao.isEmailExists("admin@gmail.com")
                    if (exists == 0) {
                        userDao.insertUser(
                            UserEntity(
                                name = "Admin",
                                email = "admin@gmail.com",
                                password = "1234",
                                role = "ADMIN"
                            )
                        )
                    }
                }

                instance
            }
        }

    }
}
