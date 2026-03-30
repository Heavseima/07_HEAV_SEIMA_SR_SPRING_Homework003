package org.example.eventticketingsystem.repository;

import org.apache.ibatis.annotations.*;
import org.example.eventticketingsystem.model.entity.Venue;
import org.example.eventticketingsystem.model.request.VenueRequest;

import java.util.List;

@Mapper
public interface VenueRepository {

    @Results(id = "venueMapper", value = {
            @Result(property = "venueId", column = "venue_id", id = true),
            @Result(property = "venueName", column = "venue_name")
    })
    @Select("""
        SELECT * FROM venues OFFSET #{offset} LIMIT #{size};
    """)
    List<Venue> getAllVenue(@Param("offset") Long offset, @Param("size") Long size);

    @ResultMap("venueMapper")
    @Select("""
        SELECT * FROM venues WHERE venue_id = #{id};
    """)
    Venue getVenueById(Long id);

    @ResultMap("venueMapper")
    @Select("""
        INSERT INTO venues VALUES (default, #{req.venueName}, #{req.location}) RETURNING *;
    """)
    Venue createVenue(@Param("req") VenueRequest request);

    @ResultMap("venueMapper")
    @Select("""
        SELECT * FROM venues WHERE venue_name = #{req.venueName};
    """)
    Venue validateVenue(@Param("req") VenueRequest request);

    @ResultMap("venueMapper")
    @Select("""
       UPDATE venues SET venue_name = #{req.venueName}, location = #{req.location} WHERE venue_id = #{id} RETURNING *;
    """)
    Venue updateVenue(@Param("req") VenueRequest request, @Param("id") Long id);

    @ResultMap("venueMapper")
    @Delete("""
        DELETE FROM venues WHERE venue_id = #{id};
    """)
    void deleteVenue(Long id);
}
