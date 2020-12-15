package com.virtualfridge.virtualfridge.database.entities

import java.time.LocalDate
import javax.persistence.*

@Entity
data class Event(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Int? = null,

        @Column(nullable = false)
        var title: String,

        @Column(nullable = true)
        var description: String? = null,

        @Column(nullable = true)
        var place: String? = null,

        @Column(nullable = false)
        var startDate: LocalDate,

        @Column(nullable = false)
        var endDate: LocalDate,

        @ManyToOne
        @JoinColumn(name = "user_id", nullable = false)
        var user: User
)