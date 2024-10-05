package com.karmeb.app.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class BookingTimeItem {
    private String id; // ID or UUID depending on the workshop
    private LocalDateTime time;
    private boolean available;

}
