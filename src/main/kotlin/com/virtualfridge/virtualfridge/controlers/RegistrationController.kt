package com.virtualfridge.virtualfridge.controlers

import com.virtualfridge.virtualfridge.models.UserResponse
import com.virtualfridge.virtualfridge.services.RegistrationService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class RegistrationController(val registrationService: RegistrationService) {

    @PostMapping("/registration/register")
    fun register(
            @RequestParam("email") email: String,
            @RequestParam("password") password: String,
            @RequestParam("first_name") firstName: String,
            @RequestParam("last_name") lastName: String
    ): ResponseEntity<UserResponse> {
        return ResponseEntity.ok(UserResponse("123", "mockedEmail", "mockedFirstName", "mockedLastName", true))
    }

    @PostMapping("/registration/register_with_google")
    fun registerWithGoogle(
            @RequestParam("email") email: String,
            @RequestParam("google_id") googleId: String,
            @RequestParam("first_name") firstName: String,
            @RequestParam("last_name") lastName: String
    ): ResponseEntity<UserResponse> {
        return ResponseEntity.ok(UserResponse("123", "mockedEmail", "mockedFirstName", "mockedLastName", true))
    }

}