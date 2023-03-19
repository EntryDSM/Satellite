package kr.hs.entrydsm.repo.global.entity

import com.fasterxml.uuid.Generators
import org.springframework.data.annotation.Id
import java.util.*

open class BaseMongoUUIDEntity(
    id: UUID?
) {
    @Id
    open val id: UUID = id ?: Generators.timeBasedGenerator().generate()
}