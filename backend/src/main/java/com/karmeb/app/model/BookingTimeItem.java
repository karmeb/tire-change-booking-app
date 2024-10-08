package com.karmeb.app.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class BookingTimeItem {

    @JsonProperty(value = "id")
    @JacksonXmlProperty(localName = "uuid", isAttribute = true)
    private String id;

    @JacksonXmlProperty(localName = "time", isAttribute = true)
    private LocalDateTime time;

    private boolean available = true;

}