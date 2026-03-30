package org.example.eventticketingsystem.repository;

import org.apache.ibatis.annotations.*;
import org.example.eventticketingsystem.model.entity.Attendee;
import org.example.eventticketingsystem.model.request.AttendeeRequest;
import org.example.eventticketingsystem.model.request.AttendeeRequestUpdate;

import java.util.List;

@Mapper
public interface AttendeeRepository {

    @Results(id = "attendeeMapper", value = {
            @Result(property="attendeeId", column = "attendee_id", id=true),
            @Result(property = "attendeeName", column = "attendee_name")
    })
    @Select("""
        SELECT * FROM attendees OFFSET #{offset} LIMIT #{size};
    """)
    List<Attendee> getAllAttendee(Long offset, Long size);

    @ResultMap("attendeeMapper")
    @Select("""
         SELECT * FROM attendees WHERE attendee_id = #{id};
     """)
    Attendee getAttendeeById(Long id);

    @ResultMap("attendeeMapper")
    @Select("""
        SELECT * FROM attendees WHERE attendee_name = #{req.attendeeName};
    """)
    Attendee validateNameAttendee(@Param("req") AttendeeRequest request);

    @ResultMap("attendeeMapper")
    @Select("""
        SELECT * FROM attendees WHERE attendee_name = #{req.attendeeName};
    """)
    Attendee validateNameAttendeeUpdate(@Param("req") AttendeeRequestUpdate request);

    @ResultMap("attendeeMapper")
    @Select("""
        SELECT * FROM attendees WHERE attendee_name = #{req.email};
    """)
    Attendee validateEmailAttendee(@Param("req") AttendeeRequest request);

    @ResultMap("attendeeMapper")
    @Select("""
        INSERT INTO attendees VALUES (default, #{req.attendeeName}, #{req.email}) RETURNING *;
    """)
    Attendee createAttendee(@Param("req") AttendeeRequest request);

    @ResultMap("attendeeMapper")
    @Select("""
        UPDATE attendees SET attendee_name = #{req.attendeeName} WHERE attendee_id = #{id} RETURNING *;
    """)
    Attendee updateAttendeeById(@Param("id") Long id, @Param("req") AttendeeRequestUpdate request);

    @ResultMap("attendeeMapper")
    @Select("""
        DELETE FROM ONLY attendees WHERE attendee_id = #{id};
    """)
    void deleteAttendee(Long id);
}
