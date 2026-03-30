package org.example.eventticketingsystem.repository;

import org.apache.ibatis.annotations.*;
import org.example.eventticketingsystem.model.entity.Event;
import org.example.eventticketingsystem.model.request.EventRequest;

import java.time.LocalDate;
import java.util.List;

@Mapper
public interface EventRepository {

    @Results(id = "eventMapper", value = {
            @Result(property = "eventId", column = "event_id", id = true),
            @Result(property = "eventName", column = "event_name"),
            @Result(property = "eventDate", column = "event_date"),
            @Result(property = "venue", column = "venue_id",
                    one = @One(select = "org.example.eventticketingsystem.repository.VenueRepository.getVenueById")),
            @Result(property = "attendees", column = "event_id", many = @Many(select = "org.example.eventticketingsystem.repository.EventAttendeeRepository.getAttendeeList"))
    })
    @Select("""
        SELECT * FROM events OFFSET #{offset} LIMIT #{size};
    """)
    List<Event> getAllEvent(Long offset, Long size);

    @ResultMap("eventMapper")
    @Select("""
        SELECT * FROM events WHERE event_id = #{id};
    """)
    Event getEventById(Long id);

    @ResultMap("eventMapper")
    @Select("""
        INSERT INTO events (event_name, event_date, venue_id) VALUES (#{req.eventName}, #{req.evenDate}, #{req.venueId}) RETURNING *;
    """)
    Event createEvent(@Param("req") EventRequest request);

    @ResultMap("eventMapper")
    @Select("""
        SELECT * FROM events WHERE event_name = #{req.eventName};
    """)
    Event validateEvent(@Param("req") EventRequest request);

    @ResultMap("eventMapper")
    @Select("""
       SELECT COUNT(*) FROM events WHERE event_name = #{eventName} AND event_date = #{eventDate}
    """)
    Integer countExistingEvents(String eventName, LocalDate eventDate);

    @ResultMap("eventMapper")
    @Select("""
       UPDATE events SET event_name = #{req.eventName}, event_date = #{req.evenDate}, venue_id = #{req.venueId} WHERE event_id = #{id} RETURNING *;
    """)
    Event updateEvent(@Param("req") EventRequest request, @Param("id") Long id);

    @ResultMap("eventMapper")
    @Delete("""
        DELETE FROM events WHERE event_id = #{id};
    """)
    void deleteEvent(Long id);
}

