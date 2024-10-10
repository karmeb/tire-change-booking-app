package com.karmeb.app.config;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.List;
import java.util.regex.Pattern;

@SpringBootTest
class WorkshopConfigPropertiesIT {

    private static final String URL_REGEX = "^(https?)://[a-zA-Z0-9.-]+(:[0-9]+)?(/.*)?$";

    @Autowired
    private WorkshopConfigProperties workshopConfigProperties;

    @Test
    void testWorkshopConfigPropertiesAreFetched() {
        List<WorkshopConfigProperties.Workshop> workshops = workshopConfigProperties.getWorkshops();
        assertFalse(workshops.isEmpty(), "Workshops should not be empty");

        workshops.forEach(workshop -> {
            assertNotNull(workshop.getName(), "Name should not be null");
            assertFalse(workshop.getName().isEmpty(), "Name should not be empty");
            assertFalse(workshop.getVehicleTypes().isEmpty(), "Vehicle types should not be empty");
            assertNotNull(workshop.getAddress(), "Address should not be null");
            assertFalse(workshop.getAddress().isEmpty(), "Address should not be empty");
            assertNotNull(workshop.getServiceClass(), "Service class should not be null");
            assertFalse(workshop.getServiceClass().isEmpty(), "Service class should not be empty");
            assertFalse(workshop.getApi().isEmpty(), "API endpoint should not be empty");
            assertNotNull(workshop.getApi(), "API endpoint should not be null");
            assertTrue(Pattern.matches(URL_REGEX, workshop.getApi()), "API endpoint must be a valid URL");
        });

    }

}