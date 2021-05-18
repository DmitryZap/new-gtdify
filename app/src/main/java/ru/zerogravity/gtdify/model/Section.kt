package ru.zerogravity.gtdify.model

import io.objectbox.annotation.*
import io.objectbox.converter.PropertyConverter
import io.objectbox.relation.ToMany
import io.objectbox.relation.ToOne
import ru.zerogravity.gtdify.model.exception.EmptyNameException
import ru.zerogravity.gtdify.model.exception.TryToUpdateDefaultInstanceException

@Entity
class Section(
    @Id var id: Long = 0,
    name: String? = null,
    @Convert(
        converter = SectionType.Converter::class,
        dbType = Int::class
    ) val type: SectionType = SectionType.NO
) : CardsViewable {
    @Unique
    var name: String? = name
        set(value) {
            if (type === SectionType.NO) {
                throw TryToUpdateDefaultInstanceException("Section must be not default or complete or overdue")
            } else if (value == null || value.isEmpty()) {
                throw EmptyNameException("Name must not be empty")
            } else {
                field = value
            }
        }

    @Backlink(to = "section")
    lateinit var cards: ToMany<Card>

    lateinit var project: ToOne<Project>

    override fun getInnerCards(): List<Card>? {
        return cards
    }

    enum class SectionType(val id: Int) {
        NO(0), DEFAULT(1), COMPLETE(2), OVERDUE(3);

        class Converter : PropertyConverter<SectionType?, Int?> {
            override fun convertToEntityProperty(databaseValue: Int?): SectionType? {
                if (databaseValue == null) {
                    return null
                }
                for (role in values()) {
                    if (role.id == databaseValue) {
                        return role
                    }
                }
                return NO
            }

            override fun convertToDatabaseValue(entityProperty: SectionType?): Int? {
                return entityProperty?.id
            }
        }
    }
}
