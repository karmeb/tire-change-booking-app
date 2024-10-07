package com.karmeb.app.service;

import static com.karmeb.app.util.DateConverter.convertStringToLocalDateTime;
import static org.springframework.http.MediaType.APPLICATION_JSON;

import com.karmeb.app.model.BookingDetails;
import com.karmeb.app.model.BookingRequest;
import com.karmeb.app.model.BookingTimeItem;
import com.karmeb.app.model.BookingRequestDto;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;

@Component
public class ManchesterBookingService extends AbstractBookingService {
    public ManchesterBookingService() {
        super();
        configureJsonRestClient();
    }

    @Override
    protected List<BookingDetails> fetchAvailableTimes(String from, String to) {
        String url = UriComponentsBuilder.fromHttpUrl(workshop.getApi() + "/tire-change-times")
                .queryParam("from", from)
                .toUriString();

        ResponseEntity<List<BookingTimeItem>> result = restClient.get()
                .uri(url)

                .retrieve()
                .toEntity(new ParameterizedTypeReference<>(){});

        List<BookingTimeItem> fetchedTimes = result.getBody();
        if ( fetchedTimes == null || fetchedTimes.isEmpty()) {
            return new ArrayList<>();

        }
        LOGGER.info("fetched times from Manchester: {}, status {}", fetchedTimes, result.getStatusCode());
        List<BookingTimeItem> filteredTimeItems = filterAvailableTimesBeforeDate(result.getBody(), convertStringToLocalDateTime(to));

        return addWorkshopDetails(filteredTimeItems);
    }

    @Override
    protected void handleBooking(String id, String contactInfo) {
        BookingRequest bookingRequest = new BookingRequest(contactInfo);
        ResponseEntity<Void> response = restClient.post()
                .uri(workshop.getApi() + "/tire-change-times/{id}/booking", id)
                .contentType(APPLICATION_JSON)
                .body(bookingRequest)
                .retrieve()
                .toBodilessEntity();
    }
}
