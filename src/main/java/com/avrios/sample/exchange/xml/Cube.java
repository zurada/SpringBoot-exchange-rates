package com.avrios.sample.exchange.xml;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.Date;
import java.util.List;

@XmlType(namespace = "http://www.ecb.int/vocabulary/2002-08-01/eurofxref")
@XmlAccessorType(XmlAccessType.NONE)
public class Cube {

    private Date time;
    private String currency;
    private String rate;
    private List<Cube> cubes;

    public Cube() {
        this(null, null, null, null);
    }

    ;

    public Cube(Date time) {
        this(time, null, null, null);
    }

    public Cube(String currency, String rate) {
        this(null, currency, rate, null);
    }

    public Cube(Date time, String currency, String rate) {
        this(time, currency, rate, null);
    }

    public Cube(Date time, String currency, String rate, List<Cube> cubes) {
        this.time = time;
        this.currency = currency;
        this.rate = rate;
        this.cubes = cubes;
    }

    @XmlAttribute
    @XmlJavaTypeAdapter(CubeTimeAdapter.class)
    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    @XmlAttribute
    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    @XmlAttribute
    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    @XmlElement(name = "Cube")
    public List<Cube> getCubes() {
        return cubes;
    }

    public void setCubes(List<Cube> cubes) {
        this.cubes = cubes;
    }

}
