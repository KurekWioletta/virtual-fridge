package com.virtualfridge.virtualfridge.database.entities

import javax.persistence.*

@Entity
data class Family(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Int,

        @Column(nullable = false)
        var familyName: String,

        @Column(nullable = true)
        @OneToMany(cascade = [CascadeType.ALL])
        val familyMembers: List<User>? = null
)