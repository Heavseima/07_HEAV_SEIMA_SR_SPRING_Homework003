CREATE TABLE IF NOT EXISTS venues
(
    venue_id SERIAL PRIMARY KEY,
    venue_name VARCHAR(100) NOT NULL,
    location VARCHAR(100) NOT NULL
);

CREATE TABLE IF NOT EXISTS attendees
(
    attendee_id SERIAL PRIMARY KEY,
    attendee_name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL
);

CREATE TABLE IF NOT EXISTS events
(
    event_id SERIAL PRIMARY KEY,
    event_name VARCHAR(100) NOT NULL,
    even_date DATE DEFAULT CURRENT_DATE,
    venue_id INT NOT NULL,
    FOREIGN KEY (venue_id) REFERENCES venues(venue_id) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS event_attendee
(
    attendee_id INT NOT NULL,
    event_id INT NOT NULL,
    PRIMARY KEY (attendee_id, event_id),
    FOREIGN KEY (attendee_id) REFERENCES attendees(attendee_id) ON UPDATE CASCADE ON DELETE CASCADE,
    FOREIGN KEY (event_id) REFERENCES events(event_id) ON UPDATE CASCADE ON DELETE CASCADE
)





