package com.virtualfridge.virtualfridge.controlers

import com.virtualfridge.virtualfridge.database.entities.Note
import com.virtualfridge.virtualfridge.database.reporitories.NoteRepository
import com.virtualfridge.virtualfridge.database.reporitories.UserRepository
import com.virtualfridge.virtualfridge.errorHandling.ApiException
import com.virtualfridge.virtualfridge.models.NoteResponse
import com.virtualfridge.virtualfridge.utils.NotificationsManager
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
class NotesController() {

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
        val author = userRepository.findById(Integer.parseInt(userId)).get()
        val member = userRepository.findById(Integer.parseInt(familyMemberId)).get()

        if (note.isEmpty()) {
            throw ApiException("Note is required")
        }

        noteRepository.save(Note(
                note = note,
                author = author,
                member = member
        ))

        if (userId != familyMemberId) {
            NotificationsManager.sendNotification(
                    title = "New note received",
                    description = "You received a note from ${author.firstName} ${author.lastName}: ${note.substring(0, 15)}...",
                    registrationToken = member.notificationToken
            )
        }

        return ResponseEntity.ok("Note created")
    }

    @GetMapping("notes/{userId}")
    fun notes(
            @PathVariable("userId") userId: String
    ): ResponseEntity<List<NoteResponse>> {
        val user = userRepository.findById(Integer.parseInt(userId)).get()

        val familyMembers = user.family
                ?.let { userRepository.findUsersFromFamily(it) }
                ?.filter { it -> it.id.toString() != userId }
                .orEmpty().toMutableList()

        familyMembers += user

        val notes = familyMembers.flatMap { member ->
            noteRepository.findNotesForUser(member)
                    ?.map {
                        val addressFirstName = if (member.id == Integer.parseInt(userId)) null else member.firstName
                        val addressLastName = if (member.id == Integer.parseInt(userId)) null else member.lastName
                        val authorFirstName = if (it.author.id == Integer.parseInt(userId)) null else it.author.firstName
                        val authorLastName = if (it.author.id == Integer.parseInt(userId)) null else it.author.lastName
                        NoteResponse(it.id.toString(), it.note, addressFirstName, addressLastName, authorFirstName, authorLastName)
                    }
                    .orEmpty()
        }

        return ResponseEntity.ok(notes)
    }

    @DeleteMapping("notes/delete/{noteId}")
    fun deleteNote(
            @PathVariable("noteId") noteId: String
    ): ResponseEntity<String> {
        val note = noteRepository.findById(Integer.parseInt(noteId)).get()
        noteRepository.delete(note)

        if (note.author.id != note.member.id) {
            NotificationsManager.sendNotification(
                    title = "Note removed",
                    description = "Note was removed by ${note.member.firstName} ${note.member.lastName}: ${note.note.substring(0, 15)}...",
                    registrationToken = note.author.notificationToken
            )
        }
        return ResponseEntity.ok("Note deleted")
    }

}