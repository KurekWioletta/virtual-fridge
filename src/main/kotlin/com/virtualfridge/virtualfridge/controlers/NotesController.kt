package com.virtualfridge.virtualfridge.controlers

import com.virtualfridge.virtualfridge.database.entities.Note
import com.virtualfridge.virtualfridge.database.reporitories.NoteRepository
import com.virtualfridge.virtualfridge.database.reporitories.UserRepository
import com.virtualfridge.virtualfridge.errorHandling.ApiException
import com.virtualfridge.virtualfridge.models.NoteResponse
import com.virtualfridge.virtualfridge.services.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
class NotesController(val userService: UserService) {

    @Autowired
    private lateinit var userRepository: UserRepository
    @Autowired
    private lateinit var noteRepository: NoteRepository

    @PostMapping("notes/create")
    fun createNote(
            @RequestParam("userId") userId: String,
            @RequestParam("familyMemberId") familyMemberId: String,
            @RequestParam("note") note: String
    ): ResponseEntity<String> {
        if (note.isEmpty()) {
            throw ApiException("Note is required")
        }

        noteRepository.save(Note(
                note = note,
                author = userRepository.findById(Integer.parseInt(userId)).get(),
                member = userRepository.findById(Integer.parseInt(familyMemberId)).get()
        ))

        // TODO: send notification
        return ResponseEntity.ok("Note created")
    }

    @GetMapping("notes/{userId}")
    fun notes(
            @PathVariable("userId") userId: String
    ): ResponseEntity<List<NoteResponse>> {
        val user = userRepository.findById(Integer.parseInt(userId)).get()
        val notes = noteRepository.findNotesForUser(user)
                ?.map { NoteResponse(it.id.toString(), it.note, it.author.firstName, it.author.lastName ?: "") }
                .orEmpty()

        return ResponseEntity.ok(notes)
    }

    @DeleteMapping("notes/delete/{noteId}")
    fun deleteNote(
            @PathVariable("noteId") noteId: String
    ): ResponseEntity<String> {
        val note = noteRepository.findById(Integer.parseInt(noteId)).get()
        noteRepository.delete(note)

        // TODO: send notification
        return ResponseEntity.ok("Note deleted")
    }

}