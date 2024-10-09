package com.karmeb.app.controller;

import com.karmeb.app.config.WorkshopConfigProperties;
import com.karmeb.app.model.BookingRequestDto;
import com.karmeb.app.service.BookingService;
import com.karmeb.app.service.BookingServiceFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

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
    public void testBookTime() {
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
        verify(mockService, times(1)).bookTime("1", "contactme");
    }

    @Test
    public void testGetAvailableWorkshops() {
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
    public void testGetSupportedVehicleTypes() {
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