package ru.zerogravity.gtdify.main.section

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import io.objectbox.android.AndroidScheduler
import ru.zerogravity.gtdify.ObjectBoxBaseViewModel
import ru.zerogravity.gtdify.model.Card
import ru.zerogravity.gtdify.model.Section
import ru.zerogravity.gtdify.model.repository.SectionRepository
import ru.zerogravity.gtdify.model.repository.response.Result


class SectionViewModel(entity: Section, application: Application) :
    ObjectBoxBaseViewModel<Section>(entity, application) {
    val mutableCardsData = MutableLiveData<List<Card>>()

    init {
        when (val cardsQuery = SectionRepository.getCards(entity)) {
            is Result.Success ->
                addSubscription(cardsQuery.data.subscribe()
                    .on(AndroidScheduler.mainThread())
                    .observer {
                        mutableCardsData.value = it
                    })
            is Result.Error -> {
                Toast.makeText(
                    getApplication<Application>().baseContext,
                    getApplication<Application>().baseContext.getString(cardsQuery.messageId!!),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

    }

    fun addCard(card: Card) {
        when (val addCardResult = SectionRepository.addCard(entity, card)) {
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
