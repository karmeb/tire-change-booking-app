package com.karmeb.app.service;

import com.karmeb.app.config.WorkshopConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class BookingServiceFactory {
    private final WorkshopConfig workshopConfig;
    private final Map<String, BookingService> bookingServicesMap = new HashMap<>();

    @Autowired
    public BookingServiceFactory(List<BookingService> bookingServicesList,
            WorkshopConfig workshopConfig) {
        this.workshopConfig = workshopConfig;

        for (BookingService service: bookingServicesList) {
            bookingServicesMap.put(service.getClass().getSimpleName(), service);
        }
    }


    public BookingService getBookingService(String workshopName, String vehicleType) {
        WorkshopConfig.Workshop requestedWorkshop = workshopConfig.getEnabledWorkshops()
                .stream()
                .filter(workshopConfig -> workshopFilterCriteria(workshopConfig, workshopName, vehicleType))
                .findFirst().orElse(null);

        if (requestedWorkshop == null) {
            return null;
        }

        String serviceClassName = requestedWorkshop.getServiceClass();

        return bookingServicesMap.get(serviceClassName);
    }

    private boolean workshopFilterCriteria(WorkshopConfig.Workshop workshop, String name, String vehicleType) {
        boolean isMatchingName = name == null || workshop.getName().equalsIgnoreCase(name);
        boolean isMatchingVehicleType = vehicleType == null || workshop.getVehicleTypes().contains(vehicleType);
        return isMatchingName && isMatchingVehicleType;
    }

}
