package ru.gb.course1.myapplication

import android.app.Application
import ru.gb.course1.myapplication.di.DI
import ru.gb.course1.myapplication.di.dependencies


class App : Application() {
    override fun onCreate() {
        super.onCreate()
        DI.initialize(this, dependencies)
    }

}
