package ru.zerogravity.gtdify.model.repository

import io.objectbox.exception.DbException
import io.objectbox.query.Query
import ru.zerogravity.gtdify.R
import ru.zerogravity.gtdify.model.Card
import ru.zerogravity.gtdify.model.Project
import ru.zerogravity.gtdify.model.Section
import ru.zerogravity.gtdify.model.local.dao.CardDao
import ru.zerogravity.gtdify.model.local.dao.ProjectDao
import ru.zerogravity.gtdify.model.local.dao.SectionDao
import ru.zerogravity.gtdify.model.repository.response.Result

object ProjectRepository {
    // TODO move to user
    fun getProjects(): Result<Query<Project>> {
        return try {
            Result.Success(ProjectDao.getProjects())
        } catch (e: DbException) {
            Result.Error(e, R.string.ub_exception)
        }
    }

    fun getSections(project: Project): Result<Query<Section>> {
        return try {
            Result.Success(SectionDao.getSections(project.id))
        } catch (e: DbException) {
            Result.Error(e, R.string.ub_exception)
        }
    }

    fun addCard(project: Project, card: Card): Result<Boolean> {
        val defaultSection =
            project.sections.filter { it.type == Section.SectionType.DEFAULT }.toList()[0]
        try {
            SectionDao.addCard(defaultSection.id, card)
        } catch (e: DbException) {
            return Result.Error(e, R.string.ub_exception)
        }
        return Result.Success(true)
    }
}