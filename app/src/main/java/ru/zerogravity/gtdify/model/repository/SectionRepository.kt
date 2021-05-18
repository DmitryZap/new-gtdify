package ru.zerogravity.gtdify.model.repository

import io.objectbox.exception.DbException
import io.objectbox.query.Query
import ru.zerogravity.gtdify.R
import ru.zerogravity.gtdify.model.repository.response.Result
import ru.zerogravity.gtdify.model.Card
import ru.zerogravity.gtdify.model.Section
import ru.zerogravity.gtdify.model.local.dao.CardDao
import ru.zerogravity.gtdify.model.local.dao.SectionDao

object SectionRepository {
    fun getCards(section: Section): Result<Query<Card>> {
        return try {
            Result.Success(CardDao.getCards(section.id))
        } catch (e: DbException) {
            Result.Error(e, R.string.ub_exception)
        }
    }

    fun addCard(section: Section, card: Card): Result<Boolean> {
        try {
            SectionDao.addCard(section.id, card)
        } catch (e: DbException) {
            return Result.Error(e, R.string.ub_exception)
        }
        return Result.Success(true)
    }
}