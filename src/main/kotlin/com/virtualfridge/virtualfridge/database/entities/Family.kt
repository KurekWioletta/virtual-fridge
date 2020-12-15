package com.virtualfridge.virtualfridge.database.entities

import javax.persistence.*

@Entity
data class Family(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Int? = null,

        @Column(nullable = false)
        var familyName: String
)