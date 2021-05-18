package ru.zerogravity.gtdify.main.project

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import io.objectbox.android.AndroidScheduler
import ru.zerogravity.gtdify.ObjectBoxBaseViewModel
import ru.zerogravity.gtdify.model.Card
import ru.zerogravity.gtdify.model.Project
import ru.zerogravity.gtdify.model.Section
import ru.zerogravity.gtdify.model.repository.ProjectRepository
import ru.zerogravity.gtdify.model.repository.response.Result

class ProjectViewModel(entity: Project, application: Application) :
    ObjectBoxBaseViewModel<Project>(entity, application) {
    val mutableSectionsData = MutableLiveData<List<Section>>()

    init {
        when (val sectionsQuery = ProjectRepository.getSections(entity)) {
            is Result.Success ->
                addSubscription(sectionsQuery.data.subscribe()
                    .on(AndroidScheduler.mainThread())
                    .observer {
                        mutableSectionsData.value = it
                    })
            is Result.Error -> {
                Toast.makeText(
                    getApplication<Application>().baseContext,
                    getApplication<Application>().baseContext.getString(sectionsQuery.messageId!!),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    fun addCard(card: Card) {
        when (val addCardResult = ProjectRepository.addCard(entity, card)) {
            is Result.Success -> {
            }
            is Result.Error -> {
                Toast.makeText(
                    getApplication<Application>().baseContext,
                    getApplication<Application>().baseContext.getString(addCardResult.messageId!!),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}