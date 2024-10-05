package com.karmeb.app.service;

import com.karmeb.app.model.BookingTimeItem;

import java.time.LocalDateTime;
import java.util.List;

public class ManchesterBookingService extends AbstractBookingService {

    @Override
    public List<BookingTimeItem> fetchAvailableTimes(LocalDateTime from, LocalDateTime to) {
        // TODO handle pagination and missing dates to parameter
        // TODO Parse JSON and map to BookingTimeItem objects
        // TODO return the list
        // endpoint /tire-change-times
        return null;
    }

    @Override
    public void handleBooking(String id, String contactInfo) {
        // TODO: Create JSON request body and send to /tire-change-times/{id}/booking
    }
}
