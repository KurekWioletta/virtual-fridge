package com.virtualfridge.virtualfridge.controlers

import com.virtualfridge.virtualfridge.errors.ApiException
import com.virtualfridge.virtualfridge.models.EventResponse
import com.virtualfridge.virtualfridge.services.UserService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.LocalDate
import kotlin.random.Random

@RestController
class EventsController(val userService: UserService) {

    @PostMapping("events/create")
    fun createEvent(
            @RequestParam("userId") userId: String,
            @RequestParam("title") title: String,
            @RequestParam("description") description: String?,
            @RequestParam("place") place: String?,
            @RequestParam("startDate") startDate: String,
            @RequestParam("endDate") endDate: String
    ): ResponseEntity<String> {
        return ResponseEntity.ok("Event created")
    }

    @PutMapping("events/edit")
    fun editEvent(
            @RequestParam("eventId") eventId: String,
            @RequestParam("title") title: String,
            @RequestParam("text") text: String,
            @RequestParam("place") place: String,
            @RequestParam("startDate") startDate: String,
            @RequestParam("endDate") endDate: String
    ): ResponseEntity<String> {
        return ResponseEntity.ok("Event edited")
    }

    @Throws(ApiException::class)
    @GetMapping("events/{userId}")
    fun events(
            @PathVariable("userId") userId: String
    ): ResponseEntity<List<EventResponse>> {
        val randomValue = Random.nextInt(5)
        val list = mutableListOf<EventResponse>()
        for (item: Int in 0..randomValue) {
            list += EventResponse(
                    "123",
                    "title$item",
                    "description$item",
                    "place$item",
                    LocalDate.now(),
                    LocalDate.of(1234, 2, 4)
            )
        }
        throw ApiException()
        return ResponseEntity.ok(list)
    }

    @DeleteMapping("events/delete/{eventId}")
    fun deleteEvent(
            @PathVariable("eventId") eventId: String
    ): ResponseEntity<String> {
        return ResponseEntity.ok("Event deleted")
    }
}