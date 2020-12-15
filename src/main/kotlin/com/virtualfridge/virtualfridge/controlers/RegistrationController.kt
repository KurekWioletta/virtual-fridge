package com.virtualfridge.virtualfridge.controlers

import com.virtualfridge.virtualfridge.database.entities.User
import com.virtualfridge.virtualfridge.database.reporitories.UserRepository
import com.virtualfridge.virtualfridge.errorHandling.ApiException
import com.virtualfridge.virtualfridge.models.UserResponse
import com.virtualfridge.virtualfridge.services.RegistrationService
import com.virtualfridge.virtualfridge.utils.hash
import com.virtualfridge.virtualfridge.utils.isValidEmail
import com.virtualfridge.virtualfridge.utils.isValidPassword
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class RegistrationController(val registrationService: RegistrationService) {

    @Autowired
    private lateinit var userRepository: UserRepository

    @PostMapping("/registration/register")
    fun register(
            @RequestParam("email") email: String,
            @RequestParam("password") password: String,
            @RequestParam("first_name") firstName: String,
            @RequestParam("last_name") lastName: String
    ): ResponseEntity<UserResponse> {

        if (!email.isValidEmail()) {
            throw ApiException("Email address is invalid")
        }
        if (!password.isValidPassword()) {
            throw ApiException("Password is invalid, use at least 6 characters")
        }
        if (firstName.isEmpty()) {
            throw ApiException("First name is required")
        }
        if (lastName.isEmpty()) {
            throw ApiException("Last name is required")
        }

        var user = userRepository.findUserByEmail(email)
        if (user != null) {
            throw ApiException("User with this email has already registered")
        }
        user = User(
                email = email,
                password = password.hash(),
                firstName = firstName,
                lastName = lastName
        )

        userRepository.save(user)

        return ResponseEntity.ok(UserResponse(
                user.id.toString(),
                user.email,
                user.firstName,
                user.lastName ?: "",
                accountConfirmed = false
        ))
    }

    @PostMapping("/registration/register_with_google")
    fun registerWithGoogle(
            @RequestParam("email") email: String,
            @RequestParam("google_id") googleId: String,
            @RequestParam("first_name") firstName: String,
            @RequestParam("last_name") lastName: String
    ): ResponseEntity<UserResponse> {

        if (googleId.isEmpty()) {
            throw ApiException("")
        }
        if (!email.isValidEmail()) {
            throw ApiException("Email address is invalid")
        }
        if (firstName.isEmpty()) {
            throw ApiException("First name is required")
        }

        var user = userRepository.findUserByEmail(email)

        if (user == null) {
            user = User(
                    email = email,
                    googleId = googleId,
                    firstName = firstName,
                    lastName = lastName
            )
            userRepository.save(user)
        } else if (user.googleId == null) {
            throw ApiException("User with this email has already registered")
        }

        return ResponseEntity.ok(UserResponse(
                user.id.toString(),
                user.email,
                user.firstName,
                user.lastName ?: "",
                accountConfirmed = true
        ))
    }

}