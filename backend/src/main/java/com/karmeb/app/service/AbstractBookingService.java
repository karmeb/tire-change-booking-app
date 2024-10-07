package com.karmeb.app.service;

import com.karmeb.app.config.WorkshopConfigProperties;
import com.karmeb.app.model.BookingDetails;
import com.karmeb.app.model.BookingTimeItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.xml.Jaxb2RootElementHttpMessageConverter;
import org.springframework.web.client.RestClient;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public abstract class  AbstractBookingService implements BookingService {
    protected static final Logger LOGGER = LoggerFactory.getLogger(AbstractBookingService.class);
    protected WorkshopConfigProperties.Workshop workshop;
    protected RestClient restClient;

    public void setWorkshop(WorkshopConfigProperties.Workshop workshop) {
        this.workshop = workshop;
    }

    @Override
    public List<BookingDetails> getAvailableTimes(String from, String to) {
        return fetchAvailableTimes(from, to);
    }

    protected abstract List<BookingDetails> fetchAvailableTimes(String from, String to);


    @Override
    public void bookTime(String identifier, String contactInfo) {
        try {
            handleBooking(identifier, contactInfo);
        } catch (Exception e) {
            throw new RuntimeException( "Error while booking: " + e.getMessage());
        }
    }

    protected abstract void handleBooking(String identifier, String contactInfo);

    protected void configureJsonRestClient() {
        this.restClient = RestClient.builder()
                .requestFactory(new HttpComponentsClientHttpRequestFactory())
                .messageConverters(converters -> converters.add(new MappingJackson2HttpMessageConverter()))
                .build();
    }

    protected void configureXmlRestClient() {
        this.restClient = RestClient.builder()
                .requestFactory(new HttpComponentsClientHttpRequestFactory())
                .messageConverters(converters -> converters.add(new Jaxb2RootElementHttpMessageConverter()))
                .build();
    }

    protected List<BookingTimeItem> filterAvailableTimesBeforeDate(List<BookingTimeItem> rawResponseList, LocalDateTime to) {
        if (rawResponseList == null || rawResponseList.isEmpty()) {
            return rawResponseList;
        }

        List<BookingTimeItem> filteredTimes = rawResponseList.stream()
                .filter(timeItem -> timeItem.isAvailable() && timeItem.getTime().isBefore(to))
                .toList();
        LOGGER.info("Filtered list of times: {}", filteredTimes);
        return  filteredTimes;
    }

    protected List<BookingDetails> addWorkshopDetails(List<BookingTimeItem> timeItems){
        List<BookingDetails> bookingDetailsList = new ArrayList<>();

        if (timeItems == null || timeItems.isEmpty()) {
            return bookingDetailsList;
        }

        for (BookingTimeItem timeItem: timeItems){
            BookingDetails bookingDetails = BookingDetails.builder()
                    .id(timeItem.getId())
                    .workshopName(workshop.getName())
                    .workshopAddress(workshop.getAddress())
                    .vehicleTypes(workshop.getVehicleTypes())
                    .time(timeItem.getTime())
                    .build();
            bookingDetailsList.add(bookingDetails);
        }
        LOGGER.info("Booking times list with workshop details added: {}", bookingDetailsList);
        return bookingDetailsList;
    }

}
