package com.virtualfridge.virtualfridge.database.reporitories

import com.virtualfridge.virtualfridge.database.entities.Event
import com.virtualfridge.virtualfridge.database.entities.User
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param

interface EventRepository : CrudRepository<Event?, Int?> {

    @Query("SELECT e FROM Event e WHERE e.user = :user")
    fun findEventsForUser(@Param("user") user: User): List<Event>?

}