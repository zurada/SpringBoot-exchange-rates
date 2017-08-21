package com.avrios.sample.exchange.xml;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlType
@XmlAccessorType(XmlAccessType.NONE)
@Data
public class Sender {

    @XmlElement
    private String name;

    public Sender() {
        this(null);
    }

    public Sender(String name) {
        this.name = name;
    }

}
