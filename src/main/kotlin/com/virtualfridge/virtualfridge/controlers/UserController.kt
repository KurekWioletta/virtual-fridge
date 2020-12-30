package com.virtualfridge.virtualfridge.controlers

import com.virtualfridge.virtualfridge.database.reporitories.UserRepository
import com.virtualfridge.virtualfridge.errorHandling.ApiException
import com.virtualfridge.virtualfridge.models.UserResponse
import com.virtualfridge.virtualfridge.utils.hash
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class UserController() {

    @Autowired
    private lateinit var userRepository: UserRepository

    @PostMapping("/user/login")
    fun login(
            @RequestParam("email") email: String,
            @RequestParam("password") password: String
    ): ResponseEntity<UserResponse> {
        val user = userRepository.findUserByEmail(email) ?: throw ApiException("Email or password are invalid")

        if (user.googleId != null) {
            throw ApiException("This user is connected to google account, log in via google sign in")
        }
        if (!user.password.equals(password.hash())) {
            throw ApiException("Email or password are invalid")
        }

        return ResponseEntity.ok(UserResponse(
                user.id.toString(),
                user.email,
                user.firstName,
                user.lastName ?: "",
                user.family?.familyName,
                accountConfirmed = true
        ))
    }

    @PostMapping("/user/notifications")
    fun notifications(
            @RequestParam("userId") userId: String,
            @RequestParam("messagingToken") messagingToken: String?
    ): ResponseEntity<String> {
        val user = userRepository.findById(Integer.parseInt(userId)).get()
        user.notificationToken = messagingToken
        userRepository.save(user)

        return ResponseEntity.ok("Notification token submitted successfully")
    }
}