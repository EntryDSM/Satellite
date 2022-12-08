package com.example.exit.domain.teacher.persistence

import com.example.exit.global.entity.BaseUUIDEntity
import org.hibernate.validator.constraints.Length
import org.jetbrains.annotations.NotNull
import javax.persistence.Entity
import javax.persistence.Table

@Entity
@Table(name = "tbl_teacher")
class Teacher(

    @field: NotNull
    @Length(min = 5)
    val accountId: String,

    @field: NotNull
    @Length(min = 8, max = 60)
    val password: String

): BaseUUIDEntity()