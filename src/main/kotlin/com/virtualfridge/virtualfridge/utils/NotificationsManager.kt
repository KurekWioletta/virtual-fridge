package com.virtualfridge.virtualfridge.utils

import com.google.firebase.messaging.*


class NotificationsManager {

    companion object {
        fun sendNotification(title: String, description: String, registrationToken: String?) {
            if (!registrationToken.isNullOrEmpty()) {
                val message = Message.builder()
                        .setNotification(Notification.builder()
                                .setTitle(title)
                                .setBody(description)
                                .build())
                        .setAndroidConfig(AndroidConfig.builder()
                                .setTtl((3600 * 1000).toLong())
                                .setNotification(AndroidNotification.builder()
                                        .setIcon("stock_ticker_update")
                                        .setColor("#f45342")
                                        .build())
                                .build())
                        .setApnsConfig(ApnsConfig.builder()
                                .setAps(Aps.builder()
                                        .setBadge(42)
                                        .build())
                                .build())
                        .setToken(registrationToken)
                        .build()

                val response = FirebaseMessaging.getInstance().send(message)

                println("Successfully sent notification: $response")
            } else {
                println("Couldn't send notification for empty token")
            }
        }
    }
}