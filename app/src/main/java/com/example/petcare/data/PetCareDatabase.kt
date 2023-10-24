package com.example.petcare.data

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.petcare.data.dao.UserDao
import com.example.petcare.data.entity.UserEntity
import com.example.petcare.util.AppConstants

@Database(
    entities = [
        UserEntity::class],
    version = 1
)
abstract class PetCareDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao

    companion object {

        @Volatile
        private var instance: PetCareDatabase? = null

        fun getInstance(context: Context): PetCareDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context)
            }
        }

        private fun buildDatabase(context: Context): PetCareDatabase =
            Room.databaseBuilder(
                context,
                PetCareDatabase::class.java,
                AppConstants.DATABASE_NAME
            ).addCallback(object : Callback() {
                override fun onCreate(db: SupportSQLiteDatabase) {
                    Log.i(AppConstants.LOG_DATABASE, "PetCareDatabase.buildDatabase() - onCreate()")
                    super.onCreate(db)
                }
            }).build()

    }
}