package com.karmeb.app.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;

public class UtilitiesTest {
    @Test
    public void testConvertStringToLocalDateTime() {
        String dateString = "2023-10-05";
        LocalDateTime expectedDateTime = LocalDateTime.of(2023, 10, 5, 0, 0);
        LocalDateTime actualDateTime = Utilities.convertStringToLocalDateTime(dateString);
        assertEquals(expectedDateTime, actualDateTime);
    }
}