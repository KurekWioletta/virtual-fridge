package com.virtualfridge.virtualfridge.database.reporitories

import com.virtualfridge.virtualfridge.database.entities.Invitation
import com.virtualfridge.virtualfridge.database.entities.User
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param

interface InvitationRepository : CrudRepository<Invitation?, Int?> {

    @Query("SELECT i FROM Invitation i WHERE i.user = :user")
    fun findInvitationsForUser(@Param("user") family: User): List<Invitation>?

}