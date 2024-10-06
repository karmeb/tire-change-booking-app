package com.karmeb.app.service;

import com.karmeb.app.model.BookingTimeItem;
import com.karmeb.app.model.BookingTimeItemXmlList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class LondonBookingService extends AbstractBookingService {
    public LondonBookingService() {
        super();
        configureXmlRestClient();
    }

    @Override
    public List<BookingTimeItem> fetchAvailableTimes(String from, String to) {
        String url = UriComponentsBuilder.fromHttpUrl(workshop.getApi() + "/tire-change-times/available")
                .queryParam("from", from)
                .queryParam("until", to)
                .toUriString();

        ResponseEntity<BookingTimeItemXmlList> result = restClient.get()
                .uri(url)
                .retrieve()
                .toEntity(BookingTimeItemXmlList.class);
        LOGGER.info("fetched times from London: {}, status {}", result.getBody(), result.getStatusCode());

        return result.getBody() != null ? result.getBody().getItems() : new ArrayList<>();
    }

    @Override
    public void handleBooking(String uuid, String contactInfo) {
        // TODO: Create an XML request body send it to /tire-change-times/{uuid}/booking
    }


}
