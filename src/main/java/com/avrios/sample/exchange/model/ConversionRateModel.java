package com.avrios.sample.exchange.model;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Currency;
import java.util.Date;

@Builder
@Data
@EqualsAndHashCode
public class ConversionRateModel {
    private Date date;
    private String rate;
    private Currency currency;
}
