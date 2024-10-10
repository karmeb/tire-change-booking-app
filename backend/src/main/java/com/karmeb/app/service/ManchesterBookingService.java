package com.karmeb.app.service;

import static com.karmeb.app.util.Utilities.convertStringToLocalDateTime;
import static org.springframework.http.MediaType.APPLICATION_JSON;

import com.karmeb.app.model.BookingDetails;
import com.karmeb.app.model.BookingRequest;
import com.karmeb.app.model.BookingTimeItem;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ManchesterBookingService extends AbstractBookingService {
    public ManchesterBookingService() {
        super();
        configureJsonRestClient();
    }

    @Override
    protected List<BookingDetails> fetchAvailableTimes(String from, String to) {
        Map<String,String> queryParams = new HashMap<>();
        queryParams.put("from", from);

        String url = buildApiURLWithWQueryParams(workshop.getApi() + "/tire-change-times", queryParams);

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
