package kr.hs.entrydsm.satellite.global.entity

import com.fasterxml.uuid.Generators
import java.util.UUID
import org.springframework.data.annotation.Id

open class BaseMongoUUIDEntity(
    id: UUID?
) {
    @Id
    open val id: UUID = id ?: Generators.timeBasedGenerator().generate()
}