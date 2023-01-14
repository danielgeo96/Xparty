package com.example.xparty

import android.app.Application
import android.content.Context
import dagger.Provides
import dagger.hilt.android.HiltAndroidApp
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@HiltAndroidApp
class MyApplication:Application() {

}