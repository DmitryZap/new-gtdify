package ru.zerogravity.gtdify.model

import io.objectbox.annotation.Backlink
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.annotation.Unique
import io.objectbox.relation.ToMany
import ru.zerogravity.gtdify.model.exception.EmptyNameException
import ru.zerogravity.gtdify.model.exception.TryToUpdateDefaultInstanceException

@Entity
class Project(
    @Id var id: Long = 0,
    name: String,
    var isDefault: Boolean = false
) : CardsViewable {
    @Unique
    var name: String = name
        set(value) {
            when {
                isDefault -> {
                    throw TryToUpdateDefaultInstanceException("Project must be not default")
                }
                value.isEmpty() -> {
                    throw EmptyNameException("Name must not be empty")
                }
                else -> {
                    field = value
                }
            }
        }

    @Backlink(to = "project")
    lateinit var sections: ToMany<Section>

    init {
        // TODO check vulnerabilities
        sections.add(Section(type = Section.SectionType.OVERDUE))
        sections.add(Section(type = Section.SectionType.DEFAULT))
        sections.add(Section(type = Section.SectionType.COMPLETE))
    }

    override fun getInnerCards(): List<Card>? {
        val cards = ArrayList<Card>()
        sections.forEach { section ->
            cards.addAll(
                section.getInnerCards() ?: ArrayList()
            )
        }
        return if (cards.isEmpty()) null else cards
    }
}
