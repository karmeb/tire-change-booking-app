package com.karmeb.app.controller;

import com.karmeb.app.model.BookingTimeItem;
import com.karmeb.app.service.BookingService;
import com.karmeb.app.service.BookingServiceFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/booking")
public class BookingController {
    BookingServiceFactory serviceFactory;

    @GetMapping("/available-times")
    public List<BookingTimeItem> getAvailableTimes(
            @RequestParam LocalDateTime from,
            @RequestParam LocalDateTime to,
            @RequestParam List<String> workshop,
            @RequestParam List<String> vehicleType) {
        List<BookingTimeItem> allAvailableTimes = new ArrayList<>();

        try {
            for (String shop: workshop){
                for (String type: vehicleType) {
                    BookingService service = serviceFactory.getBookingService(shop, type);
                    if (service != null) {
                       allAvailableTimes.addAll(service.getAvailableTimes(from, to));
                    }
                }
            }
            return allAvailableTimes;
        } catch (Exception e) {
            return null;
        }
    }

    @PostMapping("/book-time")
    public ResponseEntity<String> bookTime(
            @RequestParam String workshop,
            @RequestParam String id,
            @RequestParam String contactInfo) {
        try {
            BookingService service = serviceFactory.getBookingService(workshop, null);
            service.bookTime(id, contactInfo);
            return ResponseEntity.ok("OK");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error " + e.getMessage());
        }
    }
}
