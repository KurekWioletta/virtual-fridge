package com.virtualfridge.virtualfridge.controlers

import com.virtualfridge.virtualfridge.database.entities.Event
import com.virtualfridge.virtualfridge.database.reporitories.EventRepository
import com.virtualfridge.virtualfridge.database.reporitories.UserRepository
import com.virtualfridge.virtualfridge.errorHandling.ApiException
import com.virtualfridge.virtualfridge.models.EventResponse
import com.virtualfridge.virtualfridge.services.UserService
import com.virtualfridge.virtualfridge.utils.dateTimeFormatter
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.LocalDate


@RestController
class EventsController(val userService: UserService) {

    @Autowired
    private lateinit var userRepository: UserRepository
    @Autowired
    private lateinit var eventRepository: EventRepository

    @PostMapping("events/create")
    fun createEvent(
            @RequestParam("userId") userId: String,
            @RequestParam("title") title: String,
            @RequestParam("description") description: String?,
            @RequestParam("place") place: String?,
            @RequestParam("startDate") startDate: String,
            @RequestParam("endDate") endDate: String
    ): ResponseEntity<String> {
        if (title.isEmpty()) {
            throw ApiException("Title is required")
        }
        if (startDate.isEmpty()) {
            throw ApiException("Start date is required")
        }
        if (endDate.isEmpty()) {
            throw ApiException("End date is required")
        }

        eventRepository.save(Event(
                title = title,
                description = description,
                place = place,
                startDate = LocalDate.parse(startDate, dateTimeFormatter),
                endDate = LocalDate.parse(endDate, dateTimeFormatter),
                user = userRepository.findById(Integer.parseInt(userId)).get()
        ))

        return ResponseEntity.ok("Event created")
    }

    @GetMapping("events/{userId}")
    fun events(
            @PathVariable("userId") userId: String
    ): ResponseEntity<List<EventResponse>> {
        val user = userRepository.findById(Integer.parseInt(userId)).get()
        val events = eventRepository.findEventsForUser(user)
                ?.map {
                    EventResponse(
                            it.id.toString(),
                            title = it.title,
                            description = it.description,
                            place = it.place,
                            startDate = dateTimeFormatter.format(it.startDate),
                            endDate = dateTimeFormatter.format(it.endDate)
                    )
                }
                .orEmpty()

        return ResponseEntity.ok(events)
    }

    @DeleteMapping("events/delete/{eventId}")
    fun deleteEvent(
            @PathVariable("eventId") eventId: String
    ): ResponseEntity<String> {
        val note = eventRepository.findById(Integer.parseInt(eventId)).get()
        eventRepository.delete(note)

        return ResponseEntity.ok("Event deleted")
    }
}