package com.virtualfridge.virtualfridge.models

data class UserResponse(
        val id: String,
        val email: String,
        val firstName: String,
        val lastName: String,
        val familyName: String? = null,
        val accountConfirmed: Boolean
)