package ru.zerogravity.gtdify.model.local.dao

import io.objectbox.query.Query
import ru.zerogravity.gtdify.model.Card
import ru.zerogravity.gtdify.model.Card_
import ru.zerogravity.gtdify.model.Section
import ru.zerogravity.gtdify.model.Section_
import ru.zerogravity.gtdify.model.local.ObjectBox

object SectionDao {
    private val sectionBox = ObjectBox.store.boxFor(Section::class.java)

    fun updateName(sectionId: Long, name: String) {
        val section = sectionBox[sectionId]
        section.name = name
        sectionBox.put(section)
    }

    fun createSection(section: Section): Section {
        return sectionBox[sectionBox.put(section)]
    }

    fun getSections(projectId: Long): Query<Section> {
        return sectionBox.query().run {
            equal(Section_.projectId, projectId)
        }.build()
    }

    fun addCard(sectionId: Long, card: Card) {
        val section = sectionBox[sectionId]
        val newCardId = CardDao.createCard(card)
        CardDao.updateSection(newCardId, section)
    }
}