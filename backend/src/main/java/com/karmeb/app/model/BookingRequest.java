package com.karmeb.app.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
@JacksonXmlRootElement(localName = "london.tireChangeBookingRequest")
public class BookingRequest {

    @JacksonXmlProperty(localName = "contactInformation")
    private String contactInformation;
}