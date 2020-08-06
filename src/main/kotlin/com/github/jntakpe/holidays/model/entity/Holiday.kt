package com.github.jntakpe.holidays.model.entity

import com.github.jershell.kbson.ObjectIdSerializer
import com.github.jntakpe.holidays.shared.Identifiable
import com.github.jntakpe.holidays.shared.Identifiable.Companion.DB_ID
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.bson.types.ObjectId
import org.litote.kmongo.Data
import org.litote.kmongo.serialization.LocalDateSerializer
import java.time.LocalDate

@Data
@Serializable
data class Holiday(
    val userId: String,
    val country: String,
    val holidays: List<@Serializable(LocalDateSerializer::class) LocalDate> = emptyList(),
    @SerialName(DB_ID) @Serializable(ObjectIdSerializer::class) override val id: ObjectId = ObjectId()
) : Identifiable
