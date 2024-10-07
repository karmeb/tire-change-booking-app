package com.karmeb.app.service;

import com.karmeb.app.model.BookingDetails;
import com.karmeb.app.model.BookingTimeItem;

import java.time.LocalDateTime;
import java.util.List;

public interface BookingService {
    List<BookingDetails> getAvailableTimes(String from, String to);
    void bookTime(String identifier, String contactInfo);
}
