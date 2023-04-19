package kr.hs.entrydsm.satellite.common.entity

import org.springframework.data.annotation.Id
import org.springframework.data.domain.Persistable
import java.util.*

abstract class BaseUUIDEntity(
    @field:Id
    @get:JvmName("getIdentifier")
    open var id: UUID = defaultUUID
): Persistable<UUID> {

    override fun getId() = id

    override fun isNew() =
        (id == defaultUUID).apply { if (this) id = UUID.randomUUID() }

    companion object {
        private val defaultUUID = UUID(0,0)
    }
}