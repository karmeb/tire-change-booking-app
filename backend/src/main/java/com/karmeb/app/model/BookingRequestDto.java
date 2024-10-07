package com.karmeb.app.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class BookingRequestDto {
    @NonNull
    private String id;
    @NonNull
    private String workshopName;
    @NonNull
    private String contactInfo;
}
