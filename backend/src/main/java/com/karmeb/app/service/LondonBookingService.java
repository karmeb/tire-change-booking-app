package com.karmeb.app.service;

import static org.springframework.http.MediaType.TEXT_XML;

import com.karmeb.app.model.BookingDetails;
import com.karmeb.app.model.BookingRequest;
import com.karmeb.app.model.BookingTimeItem;
import com.karmeb.app.model.BookingTimeXmlWrapper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;

@Component
public class LondonBookingService extends AbstractBookingService {
    public LondonBookingService() {
        super();
        configureXmlRestClient();
    }

    @Override
    public List<BookingDetails> fetchAvailableTimes(String from, String to) {
        String url = UriComponentsBuilder.fromHttpUrl(workshop.getApi() + "/tire-change-times/available")
                .queryParam("from", from)
                .queryParam("until", to)
                .toUriString();

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(List.of((MediaType.TEXT_XML)));
        HttpEntity<Void> entity = new HttpEntity<>(headers);

        ResponseEntity<BookingTimeXmlWrapper> result = restClient.get()
                .uri(url)
                .headers(httpHeaders -> headers.setAccept(List.of(MediaType.APPLICATION_XML)))
                .retrieve()
                .toEntity(BookingTimeXmlWrapper.class);

        BookingTimeXmlWrapper responseBody = result.getBody();

        if ( responseBody == null || responseBody.getAvailableTimes().isEmpty()) {
            return new ArrayList<>();
        }

        List<BookingTimeItem> fetchedTimes = responseBody.getAvailableTimes();
        LOGGER.info("fetched times from London: {}, status {}", fetchedTimes, result.getStatusCode());

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
