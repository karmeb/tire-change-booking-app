package com.karmeb.app.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@Getter
@Setter
@ToString
@XmlRootElement(name = "bookingTimeItem")
@AllArgsConstructor
public class BookingTimeItem {

    @XmlElement(name = "uuid")
    @JsonProperty("id")
    private String id; // ID or UUID depending on the workshop

    @XmlElement
    @JsonProperty
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    @XmlJavaTypeAdapter(LocalDateTimeAdapter.class)
    private LocalDateTime time;

    @XmlElement
    @JsonProperty
    private boolean available = true;

}
