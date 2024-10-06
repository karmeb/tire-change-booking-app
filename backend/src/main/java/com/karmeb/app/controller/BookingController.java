package com.karmeb.app.controller;

import com.karmeb.app.config.WorkshopConfigProperties;
import com.karmeb.app.model.BookingTimeItem;
import com.karmeb.app.service.BookingService;
import com.karmeb.app.service.BookingServiceFactory;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
@RequiredArgsConstructor
@RequestMapping("/api/booking")
public class BookingController {
    private static final Logger LOGGER = LoggerFactory.getLogger(BookingController.class);
    private final BookingServiceFactory serviceFactory;
    private final WorkshopConfigProperties workshopConfigProperties;

    @GetMapping("/available-times")
    public List<BookingTimeItem> getAvailableTimes(
            @RequestParam String from,
            @RequestParam String to,
            @RequestParam(defaultValue = "Manchester, London") List<String> workshop,
            @RequestParam(defaultValue = "Car,Truck") List<String> vehicleType) {

        LOGGER.info("Incoming get available times request, from: {}, to: {}, workshops: {}, vehicleType: {} ",
                from, to, workshop, vehicleType);

        List<BookingTimeItem> allAvailableTimes = new ArrayList<>();

        List<WorkshopConfigProperties.Workshop> filteredWorkshops = serviceFactory.filterWorkshops(workshop, vehicleType);

        for (WorkshopConfigProperties.Workshop shop : filteredWorkshops) {
            try {
                BookingService service = serviceFactory.getBookingService(shop);
                LOGGER.debug("service fetched: {}", service);
                if (service != null) {
                    allAvailableTimes.addAll(service.getAvailableTimes(from, to));
                }
            } catch (Exception e) {
                LOGGER.error("Unable to get available times from shop {}, error: {}", shop.getName(), e.getMessage());
            }
        }

        return allAvailableTimes;
    }

    @PostMapping("/book-time")
    public ResponseEntity<String> bookTime(
            @RequestParam String workshop,
            @RequestParam String id,
            @RequestParam String contactInfo) {

        LOGGER.info("Incoming book time request, workshop: {}, id: {}, contactInfo: {} ",
                workshop, id, contactInfo);
        try {
            BookingService service = serviceFactory.getBookingService(
                    workshopConfigProperties.getEnabledWorkshopByName(workshop));
            service.bookTime(id, contactInfo);
            LOGGER.info("Successfully booked time, workshop: {}, id: {}, contactInfo: {} ",
                    workshop, id, contactInfo);
            return ResponseEntity.ok("OK");
        } catch (Exception e) {
            LOGGER.error("Failed to book time, workshop: {}, id: {}, contactInfo: {}, error: {} ",
                    workshop, id, contactInfo, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error " + e.getMessage());
        }
    }
}
