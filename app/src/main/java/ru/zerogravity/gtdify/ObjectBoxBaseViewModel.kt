package ru.zerogravity.gtdify

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import io.objectbox.reactive.DataSubscription


open class ObjectBoxBaseViewModel<T : Any>(protected val entity: T, application: Application) :
    AndroidViewModel(application) {
    private var subscriptions: MutableList<DataSubscription> = ArrayList()

    override fun onCleared() {
        super.onCleared()
        subscriptions.forEach { if (it.isCanceled) it.cancel() }
    }

    protected fun addSubscription(subscription: DataSubscription) {
        subscriptions.add(subscription)
    }
}