package com.virtualfridge.virtualfridge.database.reporitories

import com.virtualfridge.virtualfridge.database.entities.Family
import org.springframework.data.repository.CrudRepository

interface FamilyRepository : CrudRepository<Family?, Int?> {

//    @Query("SELECT f FROM Family f INNER JOIN User ON  WHERE u.email = :email")
//    fun findUserFamily(@Param("userId") userId: Int): User?

}