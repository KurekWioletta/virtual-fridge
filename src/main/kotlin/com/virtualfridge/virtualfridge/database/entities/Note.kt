package com.virtualfridge.virtualfridge.database.entities

import javax.persistence.*

@Entity
data class Note(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Int? = null,

        @Column(nullable = false)
        var note: String,

        @ManyToOne
        @JoinColumn(name = "author_id", nullable = false)
        var author: User,

        @ManyToOne
        @JoinColumn(name = "member_id", nullable = false)
        var member: User
)