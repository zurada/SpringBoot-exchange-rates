package com.avrios.sample.exchange.xml;

import lombok.Data;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@XmlType(namespace = "http://www.ecb.int/vocabulary/2002-08-01/eurofxref")
@XmlAccessorType(XmlAccessType.NONE)
@Data
public class Cube {

    @XmlAttribute
    @XmlJavaTypeAdapter(CubeTimeAdapter.class)
    private LocalDate time;
    @XmlAttribute
    private String currency;
    @XmlAttribute
    private String rate;
    @XmlElement(name = "Cube")
    private List<Cube> cubes;

    public Cube() {
        this(null, null, null, null);
    }

    public Cube(LocalDate time) {
        this(time, null, null, null);
    }

    public Cube(String currency, String rate) {
        this(null, currency, rate, null);
    }

    public Cube(LocalDate time, String currency, String rate) {
        this(time, currency, rate, null);
    }

    public Cube(LocalDate time, String currency, String rate, List<Cube> cubes) {
        this.time = time;
        this.currency = currency;
        this.rate = rate;
        this.cubes = cubes;
    }
}
