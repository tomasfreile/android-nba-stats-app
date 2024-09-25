package com.example.nba

import android.app.Application;
import com.example.nba.data.AppDatabase
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@HiltAndroidApp
class MyApplication : Application()
// {
//    override fun onCreate() {
//        super.onCreate()
//
//        CoroutineScope(Dispatchers.IO).launch {
//            deleteDatabase("nba_database") // Replace with your actual database name
//            // Database deleted, now you can initialize the Room database
//            AppDatabase.getInstance(this@MyApplication) // Ensure this creates a new database instance
//        }
//    }
//}
