package com.example.exit.domain.teacher.persistence.repository

import com.example.exit.domain.teacher.persistence.Teacher
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface TeacherRepository : CrudRepository<Teacher, UUID> {

    fun findByAccountId(accountId: String): Teacher

}