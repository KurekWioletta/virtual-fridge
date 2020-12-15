package com.virtualfridge.virtualfridge

import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import org.hibernate.internal.util.ConfigHelper.getResourceAsStream
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication


@SpringBootApplication
class VirtualFridgeApplication

fun main(args: Array<String>) {
    SpringApplication.run(VirtualFridgeApplication::class.java, *args)
    initializeFirebaseApp()
}

fun initializeFirebaseApp() {
    val serviceAccount = getResourceAsStream("virtualfridge-f724b-firebase-adminsdk-c669n-673a4ffbf7.json")
    val options = FirebaseOptions.builder()
            .setCredentials(GoogleCredentials.fromStream(serviceAccount))
            .setDatabaseUrl("https://virtualfridge-f724b-default-rtdb.europe-west1.firebasedatabase.app/")
            .build()

    FirebaseApp.initializeApp(options)
}