package com.example.xparty.di
import android.content.Context
import android.location.LocationManager
import com.example.xparty.data.api_eventbrite.AuthApi
import com.example.xparty.data.local_db.DataBase
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class AppMoude {
    @Provides
    @Singleton
    fun provideRetofit(gson: Gson):Retrofit{
        return Retrofit.Builder().baseUrl("https://www.eventbrite.com")
            .addConverterFactory(GsonConverterFactory.create(gson)).build()
    }

    @Provides
    fun provideGson() :Gson = GsonBuilder().create()

    @Provides
    fun provideUserRemoteDataSource(retrofit: Retrofit) : AuthApi =
        retrofit.create(AuthApi::class.java)


    @Provides
    @Singleton
    fun provideDataBase(@ApplicationContext appContext: Context) :DataBase = DataBase.getDataBase(appContext)

    @Provides
    @Singleton
    fun provideLocationManger(@ApplicationContext appContext: Context): LocationManager =
        appContext.getSystemService(Context.LOCATION_SERVICE) as LocationManager


    @Singleton
    @Provides
    fun providePartiesDao(dataBase:DataBase) = dataBase.PartiesDao()

    @Singleton
    @Provides
    fun provideUserDao(dataBase:DataBase) = dataBase.UsersDao()


    }




