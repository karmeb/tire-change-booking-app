package com.karmeb.app.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@ToString
@Builder
public class BookingDetails {
    private String id;
    private String workshopName;
    private String workshopAddress;
    private List<String> vehicleTypes;
    private LocalDateTime time;
}
