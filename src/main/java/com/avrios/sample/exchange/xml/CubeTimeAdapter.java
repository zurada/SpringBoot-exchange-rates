package com.avrios.sample.exchange.xml;

import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.jni.Local;
import org.springframework.format.annotation.DateTimeFormat;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Slf4j
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
            return  LocalDate.parse(time,FORMATTER);
        }
        return null;
    }

}
