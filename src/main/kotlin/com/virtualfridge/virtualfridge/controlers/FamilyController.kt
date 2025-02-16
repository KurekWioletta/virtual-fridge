package com.virtualfridge.virtualfridge.controlers

import com.virtualfridge.virtualfridge.database.entities.Family
import com.virtualfridge.virtualfridge.database.entities.Invitation
import com.virtualfridge.virtualfridge.database.reporitories.FamilyRepository
import com.virtualfridge.virtualfridge.database.reporitories.InvitationRepository
import com.virtualfridge.virtualfridge.database.reporitories.UserRepository
import com.virtualfridge.virtualfridge.errorHandling.ApiException
import com.virtualfridge.virtualfridge.models.FamilyMemberResponse
import com.virtualfridge.virtualfridge.models.InvitationResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
class FamilyController() {

    @Autowired
    private lateinit var userRepository: UserRepository
    @Autowired
    private lateinit var familyRepository: FamilyRepository
    @Autowired
    private lateinit var invitationRepository: InvitationRepository

    @GetMapping("/family/invitations/{userId}")
    fun invitations(
            @PathVariable("userId") userId: String
    ): ResponseEntity<List<InvitationResponse>> {
        val invitations = userRepository.findById(Integer.parseInt(userId)).get()
                .let { invitationRepository.findInvitationsForUser(it) }
                .orEmpty()
                .map { invitation ->
                    InvitationResponse(invitation.id.toString(), invitation.family!!.familyName)
                }

        return ResponseEntity.ok(invitations)
    }

    @GetMapping("/family/members/{userId}")
    fun familyMembers(
            @PathVariable("userId") userId: String
    ): ResponseEntity<List<FamilyMemberResponse>> {
        val user = userRepository.findById(Integer.parseInt(userId)).get()

        val familyMembers = user.family
                ?.let { userRepository.findUsersFromFamily(it) }
                // filter out "myself" to be able to add it at the beginning of the list
                ?.filter { it -> it.id.toString() != userId }
                .orEmpty()
                .map { FamilyMemberResponse(it.id.toString(), it.firstName, it.lastName ?: "") }

        val myself = listOf(FamilyMemberResponse(user.id.toString(), "Me", ""))

        return ResponseEntity.ok(myself + familyMembers)
    }

    @PutMapping("/family/invitations/accept")
    fun acceptInvitation(
            @RequestParam("invitationId") invitationId: String
    ): String {
        val invitation = invitationRepository.findById(Integer.parseInt(invitationId)).get()
        val user = invitation.user!!
        user.family = invitation.family!!

        invitationRepository.delete(invitation)

        return user.family!!.familyName
    }

    @PutMapping("/family/leave")
    fun leaveFamily(
            @RequestParam("userId") userId: String
    ): ResponseEntity<String> {
        val user = userRepository.findById(Integer.parseInt(userId)).get()
        user.family = null
        userRepository.save(user)

        return ResponseEntity.ok("Removed from family")
    }

    @PostMapping("/family/create")
    fun createFamily(
            @RequestParam("userId") userId: String,
            @RequestParam("familyName") familyName: String
    ): String {
        if (familyName.isEmpty()) {
            throw ApiException("Family name is required")
        }

        val family = Family(familyName = familyName)
        familyRepository.save(family)

        val user = userRepository.findById(Integer.parseInt(userId)).get()
        user.family = family
        userRepository.save(user)

        return family.familyName
    }

    @PostMapping("/family/invite")
    fun inviteMember(
            @RequestParam("userId") userId: String,
            @RequestParam("memberEmail") memberEmail: String
    ): ResponseEntity<String> {
        val member = userRepository.findUserByEmail(memberEmail)
                ?: throw ApiException("User with this email address was not found")

        if (userRepository.findById(Integer.parseInt(userId)).get().family == null) {
            throw ApiException("You are not in any family")
        }

        val invitation = Invitation(
                family = userRepository.findById(Integer.parseInt(userId)).get().family,
                user = member
        )
        invitationRepository.save(invitation)

        // TODO: show notification to invited user

        return ResponseEntity.ok("Family member invited")
    }

    @DeleteMapping("/family/invitations/decline")
    fun declineInvitation(
            @RequestParam("invitationId") invitationId: String
    ): ResponseEntity<String> {
        val invitation = invitationRepository.findById(Integer.parseInt(invitationId)).get()
        invitationRepository.delete(invitation)

        return ResponseEntity.ok("Invitation declined")
    }

}