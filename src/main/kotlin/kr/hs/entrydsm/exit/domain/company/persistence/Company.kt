package kr.hs.entrydsm.exit.domain.company.persistence

import org.hibernate.validator.constraints.Length

import kr.hs.entrydsm.exit.global.entity.BaseUUIDEntity
import org.jetbrains.annotations.NotNull
import javax.persistence.Entity
import javax.persistence.Table

@Entity
@Table(name = "tbl_company")
class Company(

    id: UUID? = null,
    val name: String,

    @field: NotNull
    @Length(min = 5)
    val email: String,

    @field: NotNull
    @Length(min = 8, max = 60)
    val password: String,

    @field: NotNull
    val managerName: String,

    @field: NotNull
    @Length(max = 11)
    val managerNumber: String,

    @field: NotNull
    val location: String

) : BaseUUIDEntity(id)