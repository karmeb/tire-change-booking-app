package com.karmeb.app.service;

import com.karmeb.app.config.WorkshopConfigProperties;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BookingServiceFactoryTest {

    @Mock
    private ManchesterBookingService manchesterBookingService;

    @Mock
    private LondonBookingService londonBookingService;


    private BookingServiceFactory bookingServiceFactory;


    private WorkshopConfigProperties workshopConfigProperties;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        WorkshopConfigProperties.Workshop workshop1 = new WorkshopConfigProperties.Workshop();
        workshop1.setServiceClass("ManchesterBookingService");
        workshop1.setName("Manchester");
        workshop1.setVehicleTypes(List.of("Car", "Truck"));
        workshop1.setEnabled(true);

        WorkshopConfigProperties.Workshop workshop2 = new WorkshopConfigProperties.Workshop();
        workshop2.setServiceClass("LondonBookingService");
        workshop2.setName("London");
        workshop2.setVehicleTypes(List.of("Car"));
        workshop2.setEnabled(true);

        workshopConfigProperties = new WorkshopConfigProperties();
        workshopConfigProperties.setWorkshops(List.of(workshop1, workshop2));

        List<BookingService> bookingServicesList = List.of(manchesterBookingService, londonBookingService);

        bookingServiceFactory = new BookingServiceFactory(bookingServicesList, workshopConfigProperties);
    }


    @Test
    public void testGetBookingService() {
        WorkshopConfigProperties.Workshop workshop = workshopConfigProperties.getWorkshops().getFirst();

        BookingService bookingService = bookingServiceFactory.getBookingService(workshop);
        assertEquals(manchesterBookingService, bookingService);
    }

    @Test
    public void testFilterWorkshops() {
        List<WorkshopConfigProperties.Workshop> filteredWorkshops1 = bookingServiceFactory.filterWorkshops(
                List.of("Manchester"), List.of("Truck"));

        assertEquals(1, filteredWorkshops1.size());
        assertEquals("Manchester", filteredWorkshops1.get(0).getName());

        List<WorkshopConfigProperties.Workshop> filteredWorkshops2 = bookingServiceFactory.filterWorkshops(
                List.of("London"), List.of("Truck"));
        assertEquals(0, filteredWorkshops2.size());

        List<WorkshopConfigProperties.Workshop> filteredWorkshops3 = bookingServiceFactory.filterWorkshops(
                null, List.of("Car"));
        assertEquals(2, filteredWorkshops3.size());
    }


}