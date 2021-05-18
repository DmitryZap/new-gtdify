package ru.zerogravity.gtdify.model

import io.objectbox.annotation.Convert
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.converter.PropertyConverter
import io.objectbox.relation.ToOne
import ru.zerogravity.gtdify.model.exception.EmptyNameException
import java.util.*


@Entity
class Card(
    @Id var id: Long = 0,
    name: String,
    @Convert(
        converter = CardPriority.Converter::class,
        dbType = Int::class
    ) var priority: CardPriority = CardPriority.NO,
    var unformattedText: String? = null,
    var dateEnd: Date? = null,
    var isComplete: Boolean = false
) {
    var name: String = name
        set(value) {
            if (value.isEmpty()) {
                throw EmptyNameException("Name must not be empty")
            } else {
                field = value
            }
        }

    lateinit var section: ToOne<Section>
//
//    companion object {
//
//    }

    enum class CardPriority(val id: Int) {
        NO(0), LOW(1), MIDDLE(2), HIGH(3);

        class Converter : PropertyConverter<CardPriority?, Int?> {
            override fun convertToEntityProperty(databaseValue: Int?): CardPriority? {
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

            override fun convertToDatabaseValue(entityProperty: CardPriority?): Int? {
                return entityProperty?.id
            }
        }
    }

    class Builder {
        private lateinit var name: String
        private var priority = CardPriority.NO
        private var unformattedText: String? = null
        private var dateEnd: Date? = null
        private var isComplete = false

        fun name(name: String) = apply { this.name = name }

        fun priority(priority: CardPriority) = apply { this.priority = priority }

        fun unformattedText(unformattedText: String?) =
            apply { this.unformattedText = unformattedText }

        fun dateEnd(dateEnd: Date?) = apply { this.dateEnd = dateEnd }

        fun isComplete(isComplete: Boolean) = apply { this.isComplete = isComplete }

        fun build() = Card(
            name = name,
            priority = priority,
            unformattedText = unformattedText,
            dateEnd = dateEnd,
            isComplete = isComplete
        )
    }
}
