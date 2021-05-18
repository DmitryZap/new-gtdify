package ru.zerogravity.gtdify.model.local.dao

import io.objectbox.query.Query
import ru.zerogravity.gtdify.model.Card
import ru.zerogravity.gtdify.model.Card_
import ru.zerogravity.gtdify.model.Section
import ru.zerogravity.gtdify.model.Section_
import ru.zerogravity.gtdify.model.local.ObjectBox
import java.util.*

object CardDao {
    private val cardBox = ObjectBox.store.boxFor(Card::class.java)

    fun updateName(cardId: Long, name: String): Card {
        val card = cardBox[cardId]
        card.name = name
        cardBox.put(card)
        return card
    }

    fun updatePriority(cardId: Long, priority: Card.CardPriority): Card {
        val card = cardBox[cardId]
        card.priority = priority
        cardBox.put(card)
        return card
    }

    fun updateUnformattedText(cardId: Long, unformattedText: String?): Card {
        val card = cardBox[cardId]
        card.unformattedText = unformattedText
        cardBox.put(card)
        return card
    }

    fun updateDateEnd(cardId: Long, dateEnd: Date?): Card {
        val card = cardBox[cardId]
        card.dateEnd = dateEnd
        cardBox.put(card)
        return card
    }

    fun toggleCompleteState(cardId: Long): Card {
        val card = cardBox[cardId]
        card.isComplete = !card.isComplete
        cardBox.put(card)
        return card
    }

    fun updateSection(cardId: Long, section: Section): Card {
        val card = cardBox[cardId]
        card.section.target = section
        cardBox.put(card)
        return card
    }

    fun getCards(sectionId: Long): Query<Card> {
        return cardBox.query().run {
            equal(Card_.sectionId, sectionId)
            order(Card_.priority)
        }.build()
    }

    fun createCard(card: Card): Long {
        return cardBox.put(card)
    }

    fun deleteCard(cardId: Long) {
        cardBox.remove(cardId)
    }
}