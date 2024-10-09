package com.karmeb.app.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.karmeb.app.config.WorkshopConfigProperties;
import com.karmeb.app.model.BookingDetails;
import com.karmeb.app.model.BookingTimeItem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class AbstractBookingServiceTest {

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
    void testAddWorkshopDetails() {

        Mockito.when(workshop.getName()).thenReturn("Test Workshop");
        Mockito.when(workshop.getAddress()).thenReturn("123 Test St");
        Mockito.when(workshop.getVehicleTypes()).thenReturn(List.of("Car", "Truck"));

        List<BookingDetails> result = manchesterBookingService.addWorkshopDetails(bookingTimeItems);

        assertEquals(4, result.size());
        assertEquals("1", result.get(0).getId());
        assertEquals(workshop.getName(), result.get(0).getWorkshopName());
        assertEquals(workshop.getAddress(), result.get(2).getWorkshopAddress());
        assertEquals(workshop.getVehicleTypes(), result.get(1).getVehicleTypes());

    }

    @Test
    void TestFilterAvailableTimesBeforeDate() {
        List<BookingTimeItem> result1 = manchesterBookingService.filterAvailableTimesBeforeDate(bookingTimeItems, LocalDateTime.parse("2024-10-10T00:00:00"));
        assertEquals(2, result1.size());
    }
}
