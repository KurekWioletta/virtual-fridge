package com.virtualfridge.virtualfridge.database.reporitories

import com.virtualfridge.virtualfridge.database.entities.Family
import org.springframework.data.repository.CrudRepository

interface FamilyRepository : CrudRepository<Family?, Int?> {

}