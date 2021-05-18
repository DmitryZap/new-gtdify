package ru.zerogravity.gtdify.model.repository

import io.objectbox.exception.DbException
import ru.zerogravity.gtdify.R
import ru.zerogravity.gtdify.model.repository.response.Result
import ru.zerogravity.gtdify.model.Card
import ru.zerogravity.gtdify.model.Section
import ru.zerogravity.gtdify.model.exception.EmptyNameException
import ru.zerogravity.gtdify.model.local.dao.CardDao
import java.util.*

object CardRepository {
    fun update(
        card: Card,
        name: String = card.name,
        priority: Card.CardPriority = card.priority,
        unformattedText: String? = card.unformattedText,
        dateEnd: Date? = card.dateEnd,
        section: Section? = card.section.target
    ): Result<Card> {
        try {
            if (name != card.name) {
                CardDao.updateName(card.id, name)
            }
        } catch (e: EmptyNameException) {
            return Result.Error(e, messageId = R.string.card_empty_name_exception)
        }
        if (priority != card.priority) {
            CardDao.updatePriority(card.id, priority)
        }
        if (unformattedText != card.unformattedText) {
            CardDao.updateUnformattedText(card.id, unformattedText)
        }
        if (dateEnd != card.dateEnd) {
            CardDao.updateDateEnd(card.id, dateEnd)
        }
        if (section != card.section.target) {
            CardDao.updateSection(card.id, section!!)
        }

        return Result.Success(card)
    }

    fun toggleCompleteState(card: Card): Result<Card> {
        CardDao.toggleCompleteState(card.id)
        return Result.Success(card)
    }

    fun deleteCard(card: Card): Result<Boolean> {
        try {
            CardDao.deleteCard(card.id)
        } catch (e: DbException) {
            return Result.Error(e, R.string.ub_exception)
        }
        return Result.Success(true)
    }
}