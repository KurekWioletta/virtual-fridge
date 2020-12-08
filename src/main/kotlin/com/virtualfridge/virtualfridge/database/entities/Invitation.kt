package com.virtualfridge.virtualfridge.database.entities

import javax.persistence.*

@Entity
data class Invitation(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Int? = null,

        @ManyToOne
        @JoinColumn(name = "family_id", nullable = false)
        var family: Family? = null,

        @ManyToOne
        @JoinColumn(name = "user_id", nullable = false)
        var user: User? = null
)