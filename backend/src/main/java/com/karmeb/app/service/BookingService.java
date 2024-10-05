package com.karmeb.app.service;

import com.karmeb.app.model.BookingTimeItem;

import java.time.LocalDateTime;
import java.util.List;

public interface BookingService {
    List<BookingTimeItem> getAvailableTimes(LocalDateTime from, LocalDateTime to);
    void bookTime(String identifier, String contactInfo);
}
