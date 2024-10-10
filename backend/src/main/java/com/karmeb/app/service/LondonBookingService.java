package com.karmeb.app.service;

import static org.springframework.http.MediaType.TEXT_XML;

import com.karmeb.app.model.BookingDetails;
import com.karmeb.app.model.BookingRequest;
import com.karmeb.app.model.BookingTimeItem;
import com.karmeb.app.model.BookingTimeXmlWrapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class LondonBookingService extends AbstractBookingService {
    public LondonBookingService() {
        super();
        configureXmlRestClient();
    }

    @Override
    public List<BookingDetails> fetchAvailableTimes(String from, String to) {
        LocalDate untilDate = LocalDate.parse(to).plusDays(1);
        Map<String,String> queryParams = new HashMap<>();
        queryParams.put("from", from);
        queryParams.put("until", untilDate.toString());

        String url = buildApiURLWithWQueryParams(workshop.getApi() + "/tire-change-times/available", queryParams);

        ResponseEntity<BookingTimeXmlWrapper> result = restClient.get()
                .uri(url)
                .retrieve()
                .toEntity(BookingTimeXmlWrapper.class);

        BookingTimeXmlWrapper responseBody = result.getBody();

        if ( responseBody == null || responseBody.getAvailableTimes().isEmpty()) {
            return new ArrayList<>();
        }

        List<BookingTimeItem> fetchedTimes = responseBody.getAvailableTimes();
        LOGGER.info("fetched times from London: status {}", result.getStatusCode());

        return addWorkshopDetails(fetchedTimes);
    }

    @Override
    public void handleBooking(String id, String contactInfo) {
        BookingRequest bookingRequest = new BookingRequest(contactInfo);
        ResponseEntity<Void> response = restClient.put()
                .uri(workshop.getApi() + "/tire-change-times/{id}/booking", id)
                .contentType(TEXT_XML)
                .body(bookingRequest)
                .retrieve()
                .toBodilessEntity();
    }


}
