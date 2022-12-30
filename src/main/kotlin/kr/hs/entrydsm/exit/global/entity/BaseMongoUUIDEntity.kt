package kr.hs.entrydsm.exit.global.entity

import com.fasterxml.uuid.Generators
import org.springframework.data.annotation.Id
import java.util.*

open class BaseMongoUUIDEntity {

    @Id
    open val id: UUID = Generators.timeBasedGenerator().generate()

}