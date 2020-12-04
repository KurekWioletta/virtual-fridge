package com.virtualfridge.virtualfridge.controlers

import com.virtualfridge.virtualfridge.database.reporitories.UserRepository
import com.virtualfridge.virtualfridge.models.UserResponse
import com.virtualfridge.virtualfridge.services.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class UserController(val userService: UserService) {

    @Autowired
    private lateinit var userRepository: UserRepository

    @PostMapping("/user/login")
    fun login(
            @RequestParam("email") email: String,
            @RequestParam("password") password: String
    ): ResponseEntity<UserResponse> {
        return ResponseEntity.ok(UserResponse("123", "mockedEmail", "mockedFirstName", "mockedLastName", true))
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