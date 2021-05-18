package ru.zerogravity.gtdify.model.local

import android.content.Context
import io.objectbox.BoxStore
import ru.zerogravity.gtdify.model.MyObjectBox

object ObjectBox {
    lateinit var store: BoxStore
        private set

    fun init(context: Context) {
        store = MyObjectBox.builder()
            .androidContext(context.applicationContext)
            .build()
    }
}