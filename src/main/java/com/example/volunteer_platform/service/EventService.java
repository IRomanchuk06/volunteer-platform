package com.example.volunteer_platform.service;

import com.example.volunteer_platform.exeptions.EventAlreadyExistsException;
import com.example.volunteer_platform.model.Event;
import com.example.volunteer_platform.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

@Service
public class EventService {

    private final EventRepository eventRepository;

    @Autowired
    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public Event createEvent(String name, String description, String location, LocalDate date,
                             Optional<LocalTime> startTime, Optional<LocalTime> endTime, String customerEmail) {
        boolean eventExists = eventRepository.existsByNameAndDate(name, date);
        if (eventExists) {
            throw new EventAlreadyExistsException("Event with this name already exists on the given date.");
        }

        LocalTime resolvedStartTime = startTime.orElse(null);
        LocalTime resolvedEndTime = endTime.orElse(null);

        Event event = Event.builder().name(name).description(description).location(location).date(date).startTime(
                resolvedStartTime).endTime(resolvedEndTime).customerEmail(customerEmail).build();

        return eventRepository.save(event);
    }
}
