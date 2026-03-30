package org.example.eventticketingsystem.repository;

import org.apache.ibatis.annotations.*;
import org.example.eventticketingsystem.model.entity.Attendee;

import java.util.List;

@Mapper
public interface EventAttendeeRepository {

    @Results(id = "eventAttendeeMapper", value = {
            @Result(property = "attendeeId", column = "attendee_id"),
            @Result(property = "attendeeName", column = "attendee_name")
    })
    @Select("""
        SELECT * FROM event_attendee ea INNER JOIN attendees a ON ea.attendee_id = a.attendee_id where ea.event_id = #{id};
    """)
    List<Attendee> getAttendeeList(Long id);

    @ResultMap("eventAttendeeMapper")
    @Insert("""
        INSERT INTO event_attendee VALUES (#{attendeeId}, #{eventId});
    """)
    void insertEventAttendee(@Param("attendeeId") Long attendeeId, @Param("eventId") Long eventId);

    @ResultMap("eventAttendeeMapper")
    @Delete("DELETE FROM event_attendee WHERE event_id = #{eventId}")
    void deleteByEventId(Long eventId);
}
