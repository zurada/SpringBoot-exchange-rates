package com.avrios.sample.exchange.xml;

import lombok.Data;

import javax.xml.bind.annotation.*;

@XmlRootElement(name = "Envelope", namespace = "http://www.gesmes.org/xml/2002-08-01")
@XmlType(propOrder = {"subject", "sender", "cube"})
@XmlAccessorType(XmlAccessType.NONE)
@Data
public class Envelope {

    @XmlElement
    private String subject;
    @XmlElement(name = "Sender")
    private Sender sender;
    @XmlElement(name = "Cube", namespace = "http://www.ecb.int/vocabulary/2002-08-01/eurofxref")
    private Cube cube;

    public Envelope() {
        this(null, null, null);
    }

    public Envelope(String subject, Sender sender, Cube cube) {
        this.subject = subject;
        this.sender = sender;
        this.cube = cube;
    }
}
