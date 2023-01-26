package kr.hs.entrydsm.exit.domain.company.persistence

import kr.hs.entrydsm.exit.global.entity.BaseUUIDEntity
import java.util.*
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Table

@Entity
@Table(name = "tbl_company")
class Company(

    id: UUID? = null,

    @Column(columnDefinition = "VARCHAR(30)", nullable = false)
    val name: String,

    @Column(columnDefinition = "VARCHAR(40)", nullable = false)
    val email: String,

    @Column(columnDefinition = "VARCHAR(60)", nullable = false)
    val password: String,

    @Column(columnDefinition = "VARCHAR(10)", nullable = false)
    val managerName: String,

    @Column(columnDefinition = "VARCHAR(11)", nullable = false)
    val managerNumber: String,

    @Column(columnDefinition = "VARCHAR(255)", nullable = false)
    val location: String

) : BaseUUIDEntity(id)