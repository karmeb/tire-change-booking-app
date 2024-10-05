package com.karmeb.app.service;

import com.karmeb.app.config.WorkshopConfig;
import com.karmeb.app.model.BookingTimeItem;

import java.time.LocalDateTime;
import java.util.List;

public abstract class  AbstractBookingService implements BookingService {
    protected WorkshopConfig.Workshop workshop;

    public void setWorkshopConfig(WorkshopConfig.Workshop workshop) {
        this.workshop = workshop;
    }


    @Override
    public List<BookingTimeItem> getAvailableTimes(LocalDateTime from, LocalDateTime to) {
        return fetchAvailableTimes(from, to);
    }

    protected abstract List<BookingTimeItem> fetchAvailableTimes(LocalDateTime from, LocalDateTime to);


    @Override
    public void bookTime(String identifier, String contactInfo) {
        try {
            handleBooking(identifier, contactInfo);
        } catch (Exception e) {
            throw new RuntimeException( "Error while booking: " + e.getMessage());
        }
    }

    protected abstract void handleBooking(String identifier, String contactInfo);

}
