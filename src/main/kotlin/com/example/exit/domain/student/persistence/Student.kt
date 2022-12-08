package com.example.exit.domain.student.persistence

import com.example.exit.global.entity.BaseUUIDEntity
import org.jetbrains.annotations.NotNull
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "tbl_student")
class Student(

    @field: NotNull
    val email: String,

    @field: NotNull
    val major: String

): BaseUUIDEntity()