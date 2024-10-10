package com.karmeb.app.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.karmeb.app.config.WorkshopConfigProperties;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashMap;
import java.util.Map;

public class LondonBookingServiceTest {
    @InjectMocks
    private LondonBookingService londonBookingService;

    @Mock
    private WorkshopConfigProperties.Workshop workshop;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testBuildApiURLWithWQueryParamsReturnsUrlWithMultipleQueryParams() {
        String expectedUrl = "https://api.example.com/request?from=123&until=456";

        Map<String,String> queryParams = new HashMap<>();
        queryParams.put("from", "123");
        queryParams.put("until", "456");
        String actualUrl = londonBookingService.buildApiURLWithWQueryParams("https://api.example.com/request", queryParams);

        assertEquals(expectedUrl, actualUrl);
    }

    @Test
    void testBuildApiURLWithWQueryParamsReturnsUrlWithOneQueryParam() {
        String expectedUrl = "https://api.example.com/request?from=123";

        Map<String,String> queryParams = new HashMap<>();
        queryParams.put("from", "123");
        String actualUrl = londonBookingService.buildApiURLWithWQueryParams("https://api.example.com/request", queryParams);

        assertEquals(expectedUrl, actualUrl);
    }

    @Test
    void testBuildApiURLWithWQueryParamsReturnsEmptyStringWithNullApi() {
        String expectedUrl = "";

        Map<String,String> queryParams = new HashMap<>();
        queryParams.put("from", "123");

        String actualUrl = londonBookingService.buildApiURLWithWQueryParams(null, queryParams);

        assertEquals(expectedUrl, actualUrl);
    }

    @Test
    void testBuildApiURLWithWQueryParamsReturnsEmptyStringWithEmptyApi() {
        String expectedUrl = "";

        Map<String,String> queryParams = new HashMap<>();
        queryParams.put("from", "123");

        String actualUrl = londonBookingService.buildApiURLWithWQueryParams("", queryParams);

        assertEquals(expectedUrl, actualUrl);
    }


}
