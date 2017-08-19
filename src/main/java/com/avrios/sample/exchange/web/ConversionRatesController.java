package com.avrios.sample.exchange.web;

import com.avrios.sample.exchange.dto.ConversionRateInDto;
import com.avrios.sample.exchange.dto.ConversionRateOutDto;
import com.avrios.sample.exchange.service.ConversionRatesService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/rates")
@Slf4j
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ConversionRatesController {

    private final ConversionRatesService conversionRatesService;

    @PostMapping("/get/one")
    public ResponseEntity<ConversionRateOutDto> getConversionRate(@Valid @RequestBody ConversionRateInDto conversionRateInDto) {
        log.info("Getting conversion rate for: {}", conversionRateInDto);
        return ResponseEntity.ok(conversionRatesService.getConversionRate(conversionRateInDto));
    }
}
