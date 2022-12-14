package com.example.xparty.data.local_db
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.xparty.data.Party
import com.example.xparty.data.User


@Database(entities = [Party::class, User::class],version = 1, exportSchema = false)
abstract class DataBase : RoomDatabase(){

    abstract fun PartiesDao():PartiesDao
    abstract fun UsersDao() : UsersDao



    companion object{
        @Volatile
        private var instance : DataBase? =null
        fun getDataBase(context: Context) = instance ?: synchronized(this){
            Room.databaseBuilder(context.applicationContext,DataBase::class.java,"ItemsDB")
                .allowMainThreadQueries().build()
        }
    }
}