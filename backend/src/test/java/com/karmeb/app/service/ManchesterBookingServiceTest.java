package com.karmeb.app.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import com.karmeb.app.config.WorkshopConfigProperties;
import com.karmeb.app.model.BookingDetails;
import com.karmeb.app.model.BookingTimeItem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestClient;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class ManchesterBookingServiceTest {

    @InjectMocks
    private ManchesterBookingService manchesterBookingService;

    @Mock
    private WorkshopConfigProperties.Workshop workshop;

    private List<BookingTimeItem> bookingTimeItems;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        BookingTimeItem bookingTimeItem1 = new BookingTimeItem();
        bookingTimeItem1.setTime(LocalDateTime.parse("2024-10-09T10:00:00"));
        bookingTimeItem1.setId("1");
        bookingTimeItem1.setAvailable(true);

        BookingTimeItem bookingTimeItem2 = new BookingTimeItem();
        bookingTimeItem2.setTime(LocalDateTime.parse("2024-10-10T10:00:00"));
        bookingTimeItem2.setId("2");
        bookingTimeItem2.setAvailable(true);

        BookingTimeItem bookingTimeItem3 = new BookingTimeItem();
        bookingTimeItem3.setTime(LocalDateTime.parse("2024-10-10T10:00:00"));
        bookingTimeItem3.setId("3");
        bookingTimeItem3.setAvailable(false);

        BookingTimeItem bookingTimeItem4 = new BookingTimeItem();
        bookingTimeItem4.setTime(LocalDateTime.parse("2024-10-11T10:00:00"));
        bookingTimeItem4.setId("4");
        bookingTimeItem4.setAvailable(true);

        bookingTimeItems = List.of(bookingTimeItem1, bookingTimeItem2, bookingTimeItem3, bookingTimeItem4);
    }

    @Test
    void addWorkshopDetailsAddsDetailsToAllTimes() {
        // Arrange

        when(workshop.getName()).thenReturn("Test Workshop");
        when(workshop.getAddress()).thenReturn("123 Test St");
        when(workshop.getVehicleTypes()).thenReturn(List.of("Car", "Truck"));

        // Act
        List<BookingDetails> result = manchesterBookingService.addWorkshopDetails(bookingTimeItems);

        // Assert
        assertEquals(4, result.size());
        assertEquals("1", result.get(0).getId());
        assertEquals(workshop.getName(), result.get(0).getWorkshopName());
        assertEquals(workshop.getAddress(), result.get(2).getWorkshopAddress());
        assertEquals(workshop.getVehicleTypes(), result.get(1).getVehicleTypes());

    }

    @Test
    void addWorkshopDetailsReturnsEmptyListWhenTimesIsNull() {
        // Act
        List<BookingDetails> result = manchesterBookingService.addWorkshopDetails(null);

        // Assert
        assertTrue(result.isEmpty());
    }

    @Test
    void addWorkshopDetailsReturnsEmptyListWhenTimesListIsEmpty() {
        // Act
        List<BookingDetails> result = manchesterBookingService.addWorkshopDetails(new ArrayList<>());

        // Assert
        assertTrue(result.isEmpty());
    }

    @Test
    void filterAvailableTimesBeforeDateKeepsDatesBeforeOrEqualToToDate() {
        List<BookingTimeItem> result1 = manchesterBookingService.filterAvailableTimesBeforeDate(bookingTimeItems, LocalDateTime.parse("2024-10-10T00:00:00"));
        assertEquals(2, result1.size());
    }

    @Test
    void filterAvailableTimesBeforeDateReturnsNullWhenNullList() {
        List<BookingTimeItem> result= manchesterBookingService.filterAvailableTimesBeforeDate(null, LocalDateTime.parse("2024-10-10T00:00:00"));
        assertNull(result);
    }

    @Test
    void filterAvailableTimesBeforeDateReturnsUnprocessedListWhenListEmpty() {
        ArrayList<BookingTimeItem> emptyList = new ArrayList<>();
        List<BookingTimeItem> result = manchesterBookingService.filterAvailableTimesBeforeDate(emptyList, LocalDateTime.parse("2024-10-10T00:00:00"));
        assertEquals(emptyList, result);
    }
}
