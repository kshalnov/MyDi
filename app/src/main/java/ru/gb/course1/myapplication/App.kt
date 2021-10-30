package ru.gb.course1.myapplication

import android.app.Application
import ru.borhammere.dildo.Dildo
import ru.gb.course1.myapplication.di.dependencies


class App : Application() {
    override fun onCreate() {
        super.onCreate()
        Dildo.initialize(this, dependencies)
    }

}
