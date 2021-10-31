package ru.borhammere.dildo.sample

import android.app.Application
import ru.borhammere.dildo.Dildo
import ru.borhammere.dildo.sample.di.dependencies


class App : Application() {
    override fun onCreate() {
        super.onCreate()
        Dildo.initialize(this, dependencies)
    }

}
