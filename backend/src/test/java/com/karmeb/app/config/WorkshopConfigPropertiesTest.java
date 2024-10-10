package com.karmeb.app.config;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.List;

public class WorkshopConfigPropertiesTest {

    @Mock
    private WorkshopConfigProperties workshopConfigProperties;

    @BeforeEach
    void setUp() {
        workshopConfigProperties = new WorkshopConfigProperties();

        WorkshopConfigProperties.Workshop workshop1 = new WorkshopConfigProperties.Workshop();
        workshop1.setName("Enabled workshop");
        workshop1.setEnabled(true);

        WorkshopConfigProperties.Workshop workshop2 = new WorkshopConfigProperties.Workshop();
        workshop2.setName("Inactive workshop");
        workshop2.setEnabled(false);

        workshopConfigProperties.setWorkshops(List.of(workshop1, workshop2));;
    }

    @Test
    void getEnabledWorkshopByNameReturnsEnabledWorkshopName() {

        WorkshopConfigProperties.Workshop enabledWorkshop = workshopConfigProperties.getEnabledWorkshopByName("Enabled workshop");
        assertNotNull(enabledWorkshop);
        assertTrue(enabledWorkshop.isEnabled());

    }

    @Test
    void getEnabledWorkshopByNameDoesNotReturnInactiveWorkshopName() {

        WorkshopConfigProperties.Workshop enabledWorkshop = workshopConfigProperties.getEnabledWorkshopByName("Inactive workshop");
        assertNull(enabledWorkshop);

    }

    @Test
    void getEnabledWorkshopsReturnsEnabledWorkshops() {

        List<WorkshopConfigProperties.Workshop> enabledWorkshops = workshopConfigProperties.getEnabledWorkshops();
        assertEquals(1, enabledWorkshops.size());
        assertTrue(enabledWorkshops.get(0).isEnabled());
        assertEquals("Enabled workshop", enabledWorkshops.get(0).getName());

    }

}
