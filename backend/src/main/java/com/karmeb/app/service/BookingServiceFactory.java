package com.karmeb.app.service;

import com.karmeb.app.config.WorkshopConfigProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class BookingServiceFactory {
    private static final Logger LOGGER = LoggerFactory.getLogger(BookingServiceFactory.class);
    private final WorkshopConfigProperties workshopConfigProperties;
    private final Map<String, BookingService> bookingServicesMap = new HashMap<>();

    @Autowired
    public BookingServiceFactory(List<BookingService> bookingServicesList,
            WorkshopConfigProperties workshopConfigProperties) {
        this.workshopConfigProperties = workshopConfigProperties;

        for (BookingService service: bookingServicesList) {
            bookingServicesMap.put(service.getClass().getSimpleName(), service);
        }
    }

    public List<WorkshopConfigProperties.Workshop> filterWorkshops(List<String> names, List<String> vehicleTypes) {
        return workshopConfigProperties.getEnabledWorkshops().stream()
                .filter(workshopConfig -> workshopFilterCriteria(workshopConfig, names, vehicleTypes))
                .collect(Collectors.toList());

    }

    public BookingService getBookingService(WorkshopConfigProperties.Workshop workshop) {

        String serviceClassName = workshop.getServiceClass();
        BookingService bookingService = bookingServicesMap.get(serviceClassName);

        if (bookingService instanceof AbstractBookingService) {
            ((AbstractBookingService) bookingService).setWorkshop(workshop);
        }

        return bookingService;
    }

    private boolean workshopFilterCriteria(WorkshopConfigProperties.Workshop workshop, List<String> names, List<String> vehicleTypes) {
        boolean isMatchingName = names == null || names.isEmpty() || names.contains(workshop.getName());
        boolean isMatchingVehicleType = vehicleTypes == null ||vehicleTypes.isEmpty() || workshop.getVehicleTypes().stream().anyMatch(vehicleTypes::contains);
        return isMatchingName && isMatchingVehicleType;
    }

}
