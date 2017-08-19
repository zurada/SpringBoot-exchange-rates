package com.avrios.sample.exchange.service;

import com.avrios.sample.exchange.dto.ConversionRateInDto;
import com.avrios.sample.exchange.dto.ConversionRateOutDto;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class ConversionRatesServiceImpl implements ConversionRatesService {
    @Override
    public ConversionRateOutDto getConversionRate(ConversionRateInDto conversionRateInDto) {
        return ConversionRateOutDto
                .builder()
                .actualRate(BigDecimal.ONE)
                .targetCurrency(conversionRateInDto.getTargetCurrency())
                .dateOfOperation(conversionRateInDto.getDateOfConversion())
                .build();
    }
}
