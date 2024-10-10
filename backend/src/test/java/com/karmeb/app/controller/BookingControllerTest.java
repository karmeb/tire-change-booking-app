package com.karmeb.app.controller;

import com.karmeb.app.config.WorkshopConfigProperties;
import com.karmeb.app.model.BookingDetails;
import com.karmeb.app.model.BookingRequestDto;
import com.karmeb.app.service.BookingService;
import com.karmeb.app.service.BookingServiceFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class BookingControllerTest {

    @Mock
    private BookingServiceFactory serviceFactory;

    @Mock
    private WorkshopConfigProperties workshopConfigProperties;

    @InjectMocks
    private BookingController bookingController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void getAvailableTimesReturnsAvailableTimesFromAllWorkshopsOrderedAsc() {
        // Arrange
        BookingDetails bookingDetails1 = BookingDetails.builder()
                .id("1")
                .time(LocalDateTime.parse("2024-10-10T10:00:00"))
                .build();
        BookingDetails bookingDetails2 = BookingDetails.builder()
                .id("2")
                .time(LocalDateTime.parse("2024-10-09T10:00:00"))
                .build();

        WorkshopConfigProperties.Workshop mockWorkshop1 = mock(WorkshopConfigProperties.Workshop.class);
        WorkshopConfigProperties.Workshop mockWorkshop2 = mock(WorkshopConfigProperties.Workshop.class);

        BookingService mockService1 = mock(BookingService.class);
        BookingService mockService2 = mock(BookingService.class);
        when(serviceFactory.filterWorkshops(null, null)).thenReturn(List.of(mockWorkshop1,mockWorkshop2));
        when(serviceFactory.getBookingService(any())).thenReturn(mockService1).thenReturn(mockService2);
        when(mockService1.getAvailableTimes(anyString(), anyString())).thenReturn(List.of(bookingDetails1));
        when(mockService2.getAvailableTimes(anyString(), anyString())).thenReturn(List.of(bookingDetails2));

        // Act
        List<BookingDetails> result = bookingController.getAvailableTimes("from", "to", null, null);

        // Assert
        assertEquals(2, result.size());
        assertEquals("2", result.get(0).getId());
    }

    @Test
    public void getAvailableTimesReturnsListEvenIfOneServiceFails() {
        // Arrange
        BookingDetails bookingDetails1 = BookingDetails.builder()
                .id("1")
                .time(LocalDateTime.parse("2024-10-10T10:00:00"))
                .build();

        WorkshopConfigProperties.Workshop mockWorkshop1 = mock(WorkshopConfigProperties.Workshop.class);
        WorkshopConfigProperties.Workshop mockWorkshop2 = mock(WorkshopConfigProperties.Workshop.class);

        BookingService mockService1 = mock(BookingService.class);
        BookingService mockService2 = mock(BookingService.class);
        when(serviceFactory.filterWorkshops(null, null)).thenReturn(List.of(mockWorkshop1,mockWorkshop2));
        when(serviceFactory.getBookingService(any())).thenReturn(mockService1).thenReturn(mockService2);
        doThrow(new RuntimeException("Error while fetching times")).when(mockService1).bookTime(anyString(), anyString());
        when(mockService2.getAvailableTimes(anyString(), anyString())).thenReturn(List.of(bookingDetails1));

        // Act
        List<BookingDetails> result = bookingController.getAvailableTimes("from", "to", null, null);

        // Assert
        assertEquals(1, result.size());
        assertEquals("1", result.get(0).getId());
    }

    @Test
    public void bookTimeReturnsStatusOKOnSuccessfulBooking() {
        // Arrange
        BookingRequestDto bookingRequestDto = new BookingRequestDto("1", "Test Workshop","contactme");
        WorkshopConfigProperties.Workshop mockWorkshop = mock(WorkshopConfigProperties.Workshop.class);
        when(workshopConfigProperties.getEnabledWorkshopByName(anyString())).thenReturn(mockWorkshop);
        BookingService mockService = mock(BookingService.class);
        when(serviceFactory.getBookingService(mockWorkshop)).thenReturn(mockService);

        // Act
        ResponseEntity<String> response = bookingController.bookTime(bookingRequestDto);

        // Assert
        assertEquals(ResponseEntity.ok("OK"), response);
    }

    @Test
    public void bookTimeReturnsInternalServerErrorOnFailedBooking() {
        // Arrange
        BookingRequestDto bookingRequestDto = new BookingRequestDto("1", "Test Workshop","contactme");
        WorkshopConfigProperties.Workshop mockWorkshop = mock(WorkshopConfigProperties.Workshop.class);
        when(workshopConfigProperties.getEnabledWorkshopByName(anyString())).thenReturn(mockWorkshop);
        BookingService mockService = mock(BookingService.class);
        when(serviceFactory.getBookingService(mockWorkshop)).thenReturn(mockService);
        doThrow(new RuntimeException("Error while booking")).when(mockService).bookTime(anyString(), anyString());

        // Act
        ResponseEntity<String> response = bookingController.bookTime(bookingRequestDto);

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void getAvailableWorkshopsReturnsAvailableWorkshopsList() {
        // Arrange
        WorkshopConfigProperties.Workshop mockWorkshop1 = mock(WorkshopConfigProperties.Workshop.class);
        when(mockWorkshop1.getName()).thenReturn("Test Workshop1");
        WorkshopConfigProperties.Workshop mockWorkshop2 = mock(WorkshopConfigProperties.Workshop.class);
        when(mockWorkshop2.getName()).thenReturn("Test Workshop2");
        when(workshopConfigProperties.getEnabledWorkshops()).thenReturn(List.of(mockWorkshop1, mockWorkshop2));

        // Act
        List<String> result = bookingController.getAvailableWorkshops();

        // Assert
        assertEquals(2, result.size());
        assertTrue(result.contains("Test Workshop1"));
        assertTrue(result.contains("Test Workshop2"));
    }

    @Test
    public void getSupportedVehicleTypesReturnAllSupportedVehicleTypes() {
        // Arrange
        WorkshopConfigProperties.Workshop mockWorkshop1 = mock(WorkshopConfigProperties.Workshop.class);
        WorkshopConfigProperties.Workshop mockWorkshop2 = mock(WorkshopConfigProperties.Workshop.class);
        when(mockWorkshop1.getVehicleTypes()).thenReturn(List.of("Car", "Truck"));
        when(mockWorkshop2.getVehicleTypes()).thenReturn(List.of("Car"));
        when(workshopConfigProperties.getEnabledWorkshops()).thenReturn(List.of(mockWorkshop1, mockWorkshop2));

        // Act
        List<String> result = bookingController.getSupportedVehicleTypes();

        // Assert
        assertEquals(2, result.size());
        assertTrue(result.contains("Car"));
        assertTrue(result.contains("Truck"));
    }
}