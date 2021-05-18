package ru.zerogravity.gtdify

import android.app.Application
import ru.zerogravity.gtdify.model.local.ObjectBox

open class GTDifyApp : Application() {
    override fun onCreate() {
        super.onCreate()
        ObjectBox.init(this)
    }
}