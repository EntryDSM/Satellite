package com.example.exit.global.entity

import com.fasterxml.uuid.Generators
import java.util.UUID
import javax.persistence.Column
import javax.persistence.Id
import javax.persistence.MappedSuperclass

@MappedSuperclass
open class BaseUUIDEntity {

    @Id
    @Column(columnDefinition = "BINARY(16)", nullable = false)
    open val id: UUID = Generators.timeBasedGenerator().generate()

}