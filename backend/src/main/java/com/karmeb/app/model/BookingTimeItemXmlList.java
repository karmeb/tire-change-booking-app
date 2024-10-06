package com.karmeb.app.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "tireChangeTimesResponse")
@AllArgsConstructor
@Getter
@Setter
public class BookingTimeItemXmlList {
    @XmlElement(name = "availableTime")
    private List<BookingTimeItem> items;

}