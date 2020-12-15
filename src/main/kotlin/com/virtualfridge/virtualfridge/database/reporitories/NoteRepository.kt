package com.virtualfridge.virtualfridge.database.reporitories

import com.virtualfridge.virtualfridge.database.entities.Note
import com.virtualfridge.virtualfridge.database.entities.User
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param

interface NoteRepository : CrudRepository<Note?, Int?> {

    @Query("SELECT n FROM Note n WHERE n.member = :member")
    fun findNotesForUser(@Param("member") member: User): List<Note>?

}