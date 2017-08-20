package com.avrios.sample.exchange.xml;

import javax.xml.bind.annotation.*;

@XmlRootElement(name = "Envelope", namespace = "http://www.gesmes.org/xml/2002-08-01")
@XmlType(propOrder = {"subject", "sender", "cube"})
@XmlAccessorType(XmlAccessType.NONE)
public class Envelope {

    private String subject;
    private Sender sender;
    private Cube cube;

    public Envelope() {
        this(null, null, null);
    }

    public Envelope(String subject, Sender sender, Cube cube) {
        this.subject = subject;
        this.sender = sender;
        this.cube = cube;
    }

    @XmlElement
    public String getSubject() {
        return this.subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    @XmlElement(name = "Sender")
    public Sender getSender() {
        return sender;
    }

    public void setSender(Sender sender) {
        this.sender = sender;
    }

    @XmlElement(name = "Cube", namespace = "http://www.ecb.int/vocabulary/2002-08-01/eurofxref")
    public Cube getCube() {
        return cube;
    }

    public void setCube(Cube cube) {
        this.cube = cube;
    }

}
