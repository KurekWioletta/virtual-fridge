package com.virtualfridge.virtualfridge.database.entities

import javax.persistence.*

@Entity
data class User(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Int? = null,

        @Column(nullable = false)
        var email: String,

        @Column(nullable = false)
        var firstName: String,

        @Column(nullable = true)
        var lastName: String? = null,

        @Column(nullable = false)
        var accountConfirmed: Boolean = false,

        @Column(nullable = true)
        var password: String? = null,

        @Column(nullable = true)
        var notificationToken: String? = null,

        @Column(nullable = true)
        val googleId: String? = null,

        @ManyToOne
        @JoinColumn(name = "family_id", nullable = true)
        var family: Family? = null
)