package com.virtualfridge.virtualfridge.controlers

import com.virtualfridge.virtualfridge.database.reporitories.UserRepository
import com.virtualfridge.virtualfridge.models.FamilyMemberResponse
import com.virtualfridge.virtualfridge.models.InvitationResponse
import com.virtualfridge.virtualfridge.services.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import kotlin.random.Random

@RestController
class FamilyController(val userService: UserService) {

    @Autowired
    private lateinit var userRepository: UserRepository

    @GetMapping("/family/invitations/{userId}")
    fun invitations(
            @PathVariable("userId") userId: String
    ): ResponseEntity<List<InvitationResponse>> {
        val randomValue = Random.nextInt(5)
        val list = mutableListOf<InvitationResponse>()
        for (item: Int in 0..randomValue) {
            list += InvitationResponse("123", "mockedName$item")
        }
        return ResponseEntity.ok(list)
    }

    @GetMapping("/family/members/{userId}")
    fun familyMembers(
            @PathVariable("userId") userId: String
    ): ResponseEntity<List<FamilyMemberResponse>> {
        val randomValue = Random.nextInt(5)
        val list = mutableListOf<FamilyMemberResponse>()
        for (item: Int in 0..randomValue) {
            list += FamilyMemberResponse("123", "mockedName$item", "mockedLastName$item")
        }
        return ResponseEntity.ok(list)
    }

    @PutMapping("/family/invitations/accept")
    fun acceptInvitation(
            @RequestParam("invitationId") invitationId: String
    ): ResponseEntity<String> {
        return ResponseEntity.ok("MockedName")
    }

    @PutMapping("/family/leave")
    fun leaveFamily(
            @RequestParam("userId") userId: String
    ): ResponseEntity<String> {
        return ResponseEntity.ok("Family left successfully")
    }

    @PostMapping("/family/create")
    fun createFamily(
            @RequestParam("userId") userId: String,
            @RequestParam("familyName") familyName: String
    ): ResponseEntity<String> {
        return ResponseEntity.ok("MockedName")
    }

    @PostMapping("/family/invite")
    fun inviteMember(
            @RequestParam("userId") userId: String,
            @RequestParam("memberEmail") memberEmail: String
    ): ResponseEntity<String> {
        return ResponseEntity.ok("Family member invited")
    }

    @DeleteMapping("/family/invitations/decline")
    fun declineInvitation(
            @RequestParam("invitationId") invitationId: String
    ): ResponseEntity<String> {
        return ResponseEntity.ok("Invitation declined")
    }
}