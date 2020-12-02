package com.virtualfridge.virtualfridge.controlers

import com.virtualfridge.virtualfridge.models.NoteResponse
import com.virtualfridge.virtualfridge.services.UserService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import kotlin.random.Random

@RestController
class NotesController(val userService: UserService) {

    @PostMapping("notes/create")
    fun createNote(
            @RequestParam("userId") userId: String,
            @RequestParam("familyMemberId") familyMemberId: String,
            @RequestParam("note") note: String
    ): ResponseEntity<String> {
        return ResponseEntity.ok("Note created")
    }

    @GetMapping("notes/{userId}")
    fun notes(
            @PathVariable("userId") userId: String
    ): ResponseEntity<List<NoteResponse>> {
        val randomValue = Random.nextInt(5)
        val list = mutableListOf<NoteResponse>()
        for (item: Int in 0..randomValue) {
            list += NoteResponse("123", "mockedText$item", "mockedName$item", "mockedLastName$item")
        }
        return ResponseEntity.ok(list)
    }

    @DeleteMapping("notes/delete/{noteId}")
    fun deleteNote(
            @PathVariable("noteId") noteId: String
    ): ResponseEntity<String> {
        return ResponseEntity.ok("Note deleted")
    }

}