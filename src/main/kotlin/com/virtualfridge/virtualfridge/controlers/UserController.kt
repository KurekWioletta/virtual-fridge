package com.virtualfridge.virtualfridge.controlers

import com.virtualfridge.virtualfridge.models.UserResponse
import com.virtualfridge.virtualfridge.services.UserService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class UserController(val userService: UserService) {

    @PostMapping("/user/login")
    fun login(
            @RequestParam("email") email: String,
            @RequestParam("password") password: String
    ): ResponseEntity<UserResponse> {
        return ResponseEntity.ok(UserResponse("123", "mockedEmail", "mockedFirstName", "mockedLastName", true))
    }
}