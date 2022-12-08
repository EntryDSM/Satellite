package com.example.exit.domain.company.persistence

import org.hibernate.validator.constraints.Length

import com.example.exit.global.entity.BaseUUIDEntity
import org.jetbrains.annotations.NotNull
import javax.persistence.Entity
import javax.persistence.Table

@Entity
@Table(name = "tbl_company")
class Company(

    @field: NotNull
    val name: String,

    @field: NotNull
    @Length(min = 5)
    val email: String,

    @field: NotNull
    @Length(max = 11)
    val phoneNumber: String,

    @field: NotNull
    @Length(min = 8, max = 60)
    val password: String

) : BaseUUIDEntity()