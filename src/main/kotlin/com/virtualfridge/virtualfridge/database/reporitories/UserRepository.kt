package com.virtualfridge.virtualfridge.database.reporitories

import com.virtualfridge.virtualfridge.database.entities.User
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param

interface UserRepository : CrudRepository<User?, Int?> {

    @Query("SELECT u FROM User u WHERE u.email = :email")
    fun findUserByEmail(@Param("email") email: String): User?

}