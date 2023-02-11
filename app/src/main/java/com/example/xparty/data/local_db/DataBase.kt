package com.example.xparty.data.local_db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.xparty.data.models.Party
import com.example.xparty.data.models.User


@Database(entities = [Party::class, User::class], version = 12, exportSchema = false)
@TypeConverters(UriConverter::class)
abstract class DataBase : RoomDatabase() {

    abstract fun PartiesDao(): PartiesDao
    abstract fun UsersDao(): UsersDao

    companion object {
        @Volatile
        private var instance: DataBase? = null
        fun getDataBase(context: Context): DataBase {
            return instance ?: synchronized(this) {
                Room.databaseBuilder(context.applicationContext, DataBase::class.java, "PartyXDB")
                    .fallbackToDestructiveMigration().build().also {
                        instance = it
                    }
            }
        }
    }
}