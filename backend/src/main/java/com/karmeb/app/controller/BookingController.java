package com.karmeb.app.controller;

import com.karmeb.app.config.WorkshopConfigProperties;
import com.karmeb.app.model.BookingDetails;
import com.karmeb.app.model.BookingRequestDto;
import com.karmeb.app.service.BookingService;
import com.karmeb.app.service.BookingServiceFactory;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
@RequestMapping("/api/booking")
public class BookingController {
    private static final Logger LOGGER = LoggerFactory.getLogger(BookingController.class);
    private final BookingServiceFactory serviceFactory;
    private final WorkshopConfigProperties workshopConfigProperties;

    @GetMapping("/available-times")
    public List<BookingDetails> getAvailableTimes(
            @RequestParam String from,
            @RequestParam String to,
            @RequestParam(required = false) List<String> workshops,
            @RequestParam(required = false) List<String> vehicleTypes) {

        LOGGER.info("Incoming get available times request, from: {}, to: {}, workshops: {}, vehicleType: {} ",
                from, to, workshops, vehicleTypes);

        List<BookingDetails> allAvailableTimes = new ArrayList<>();

        List<WorkshopConfigProperties.Workshop> filteredWorkshops = serviceFactory.filterWorkshops(workshops, vehicleTypes);

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
    public ResponseEntity<String> bookTime(@RequestBody BookingRequestDto bookingRequestDto) {

        LOGGER.info("Incoming book time request, request body: {} ", bookingRequestDto);
        try {
            BookingService service = serviceFactory.getBookingService(
                    workshopConfigProperties.getEnabledWorkshopByName(bookingRequestDto.getWorkshopName()));
            service.bookTime(bookingRequestDto.getId(), bookingRequestDto.getContactInfo());
            LOGGER.info("Successfully booked time, request details {}", bookingRequestDto);
            return ResponseEntity.ok("OK");
        } catch (Exception e) {
            LOGGER.error("Failed to book time, request details: {}, error: {}", bookingRequestDto, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error " + e.getMessage());
        }
    }

    @GetMapping("/available-workshops")
    public List<String> getAvailableWorkshops() {
        LOGGER.info("Incoming get available workshop details ");

        return workshopConfigProperties.getEnabledWorkshops().stream()
                .map(WorkshopConfigProperties.Workshop::getName)
                .toList();

    }

    @GetMapping("/vehicle-types")
    public List<String> getSupportedVehicleTypes() {
        LOGGER.info("Incoming get supported vehicle types details ");
        return workshopConfigProperties.getEnabledWorkshops().stream()
                .flatMap(workshop -> workshop.getVehicleTypes().stream())
                .distinct()
                .toList();
    }
}
