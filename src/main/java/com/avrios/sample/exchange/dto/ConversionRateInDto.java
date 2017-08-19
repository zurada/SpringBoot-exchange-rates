package com.avrios.sample.exchange.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
public class ConversionRateInDto {
    @NotEmpty
    private String targetCurrency;

    @NotNull
    //TODO universal patterns
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateOfConversion;
}
