package com.github.jntakpe.holidays.shared

import com.github.jntakpe.holidays.shared.Identifiable
import org.litote.kmongo.serialization.IdController
import kotlin.reflect.KClass
import kotlin.reflect.KProperty1
import kotlin.reflect.full.isSubclassOf

class IdentifiableIdController : IdController {

    override fun findIdProperty(type: KClass<*>): KProperty1<*, *>? {
        return if (type.isSubclassOf(Identifiable::class)) Identifiable::id
        else throw IllegalStateException("$type should be an instance of ${Identifiable::class.simpleName}")
    }

    override fun <T, R> getIdValue(idProperty: KProperty1<T, R>, instance: T): R? {
        return idProperty.get(instance)
    }

    override fun <T, R> setIdValue(idProperty: KProperty1<T, R>, instance: T) {
        //id property is read only hence it does not makes sense to set the property
    }
}
