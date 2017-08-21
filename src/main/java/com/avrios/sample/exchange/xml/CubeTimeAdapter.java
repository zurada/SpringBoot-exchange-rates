package com.avrios.sample.exchange.xml;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class CubeTimeAdapter extends XmlAdapter<String, LocalDate> {

    private static final String DATE_FORMAT = "yyyy-MM-dd";
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");


    @Override
    public String marshal(LocalDate time) throws Exception {
        if (time != null) {
            return time.format(FORMATTER);
        }
        return null;
    }

    @Override
    public LocalDate unmarshal(String time) throws Exception {
        if (time != null) {
            return LocalDate.parse(time, FORMATTER);
        }
        return null;
    }

}
