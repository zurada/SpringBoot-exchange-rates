package com.avrios.sample.exchange.model;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;
import java.util.Currency;
import java.util.Date;

@Builder
@Data
@EqualsAndHashCode
public class ConversionRateModel implements Comparable<ConversionRateModel> {
    private LocalDate date;
    private String rate;
    private Currency currency;

    @Override
    public int compareTo(ConversionRateModel o) {
        return o.getDate().compareTo(this.getDate());
    }
}
