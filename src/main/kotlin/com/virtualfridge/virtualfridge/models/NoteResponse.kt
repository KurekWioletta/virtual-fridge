package com.virtualfridge.virtualfridge.models

data class NoteResponse(
        val id: String,
        val text: String,
        val authorFirstName: String,
        val authorLastName: String
)