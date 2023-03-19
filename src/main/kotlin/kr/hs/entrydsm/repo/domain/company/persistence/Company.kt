package kr.hs.entrydsm.repo.domain.company.persistence

import java.util.UUID
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Table
import kr.hs.entrydsm.repo.global.entity.BaseUUIDEntity

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

    @Column(columnDefinition = "VARCHAR(255)", nullable = false)
    val location: String,

    @Column(columnDefinition = "TINYINT(1)", nullable = false)
    val isInitialized: Boolean = false,

    // 보류
    @Column(columnDefinition = "VARCHAR(10)", nullable = false)
    val managerName: String,

    @Column(columnDefinition = "VARCHAR(11)", nullable = false)
    val managerNumber: String,

) : BaseUUIDEntity(id) {

    fun initialize() = copy(isInitialized = true)

    fun changePassword(
        password: String
    ) = copy(password = password)

    fun copy(
        name: String = this.name,
        email: String = this.email,
        password: String = this.password,
        location: String = this.location,
        isInitialized: Boolean = this.isInitialized,
        managerName: String = this.managerName,
        managerNumber: String = this.managerNumber,
    ) = Company(
        id = this.id,
        name = name,
        email = email,
        password = password,
        location = location,
        isInitialized = isInitialized,
        managerName = managerName,
        managerNumber = managerNumber
    )
}