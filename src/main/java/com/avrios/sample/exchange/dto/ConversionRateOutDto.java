package com.avrios.sample.exchange.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
public class ConversionRateOutDto {
    private String targetCurrency;
    private BigDecimal actualRate;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateOfOperation;
}
