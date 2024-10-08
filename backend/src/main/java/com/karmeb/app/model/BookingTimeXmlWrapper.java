package com.karmeb.app.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.ToString;

import javax.xml.bind.annotation.XmlElement;
import java.util.List;

@ToString
public class BookingTimeXmlWrapper {

    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "availableTime")
    private List<BookingTimeItem> availableTimes;

    @XmlElement(name = "availableTime")
    public List<BookingTimeItem> getAvailableTimes() {
        return availableTimes;
    }

    public void setAvailableTimes(List<BookingTimeItem> availableTimes) {
        this.availableTimes = availableTimes;
    }
}

