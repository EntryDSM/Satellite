package com.example.exit.domain.student.presentation

import com.example.exit.domain.student.presentation.dto.request.StudentUpdateRequest
import com.example.exit.domain.student.usecase.StudentUpdateProfileUseCase
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/student")
@RestController

class StudentController(
    private val updateProfileUseCase: StudentUpdateProfileUseCase
) {
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PatchMapping("/profile")
    fun updateProfile(@RequestBody request: StudentUpdateRequest) {
        updateProfileUseCase.execute(request)
    }
}