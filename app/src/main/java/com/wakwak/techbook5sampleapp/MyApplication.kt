package com.wakwak.techbook5sampleapp

import android.app.Application
import android.os.Looper
import androidx.room.Room
import com.wakwak.techbook5sampleapp.di.Modules
import com.wakwak.techbook5sampleapp.model.data.AppDatabase
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.android.schedulers.AndroidSchedulers
import org.koin.android.ext.android.startKoin

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        val db = Room.databaseBuilder<AppDatabase>(this, AppDatabase::class.java, "app-database")
                .build()
        startKoin(this, arrayListOf(Modules(this, db).appModule))
        setUpRxJavaPlugins()
    }

    private fun setUpRxJavaPlugins() {
        val asyncMainThreadScheduler = AndroidSchedulers.from(Looper.getMainLooper(), true)
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { asyncMainThreadScheduler }
    }
}
