package com.example.xparty.di

import android.app.Application
import android.content.Context
import android.location.LocationManager
import com.example.xparty.data.local_db.DataBase
import com.example.xparty.data.repository.MainRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppMoudle {

    @Provides
    @Singleton
    fun provideLocationManger(@ApplicationContext appContext: Context): LocationManager =
        appContext.getSystemService(Context.LOCATION_SERVICE) as LocationManager

    @Provides
    @Singleton
    fun providApplication(application: Application):Application = Application()

    @Provides
    @Singleton
    fun providsADataBase(@ApplicationContext appContext:Context):DataBase =
        DataBase.getDataBase(appContext)


    @Provides
    @Singleton
    fun providsPartiesDao(database: DataBase) =
        database.UsersDao()

    @Provides
    @Singleton
    fun providsUserDao(database: DataBase) =
        database.UsersDao()













}






