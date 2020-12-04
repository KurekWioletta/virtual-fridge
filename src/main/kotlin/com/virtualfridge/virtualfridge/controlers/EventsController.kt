package com.virtualfridge.virtualfridge.controlers

import com.virtualfridge.virtualfridge.database.reporitories.UserRepository
import com.virtualfridge.virtualfridge.errorHandling.ApiException
import com.virtualfridge.virtualfridge.models.EventResponse
import com.virtualfridge.virtualfridge.services.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import kotlin.random.Random


@RestController
class EventsController(val userService: UserService) {

    @Autowired
    private val userRepository: UserRepository? = null

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
                    "01/12/2020",
                    "10/12/2020"
            )
        }
        return ResponseEntity.ok(list)
    }

    @DeleteMapping("events/delete/{eventId}")
    fun deleteEvent(
            @PathVariable("eventId") eventId: String
    ): ResponseEntity<String> {
        return ResponseEntity.ok("Event deleted")
    }
}