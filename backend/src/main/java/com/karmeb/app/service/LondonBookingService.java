package com.karmeb.app.service;

import com.karmeb.app.model.BookingTimeItem;

import java.time.LocalDateTime;
import java.util.List;


public class LondonBookingService extends AbstractBookingService {

    @Override
    public List<BookingTimeItem> fetchAvailableTimes(LocalDateTime from, LocalDateTime to) {
        String getTimesRequestUrl = workshop.getApi() + "/available";
        // TODO: Send request with from, until parameters. endpoint /tire-change-times/available
        // TODO: Parse the XML response and map to BookingTime objects
        return null;
    }

    @Override
    public void handleBooking(String uuid, String contactInfo) {
        // TODO: Create an XML request body send it to /tire-change-times/{uuid}/booking
    }
}
