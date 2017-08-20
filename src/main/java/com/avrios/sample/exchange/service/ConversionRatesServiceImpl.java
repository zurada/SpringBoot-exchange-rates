package com.avrios.sample.exchange.service;

import com.avrios.sample.exchange.dto.ConversionRateInDto;
import com.avrios.sample.exchange.dto.ConversionRateOutDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@Slf4j
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ConversionRatesServiceImpl implements ConversionRatesService {

    private InMemoryDataService inMemoryDataService;

    @Override
    public ConversionRateOutDto getConversionRate(ConversionRateInDto conversionRateInDto) {
        return ConversionRateOutDto
                .builder()
                .actualRate(BigDecimal.ONE)
                .targetCurrency(conversionRateInDto.getTargetCurrency())
                .dateOfOperation(conversionRateInDto.getDateOfConversion())
                .build();
    }

    @Override
    //TODO put cron exp into config file
    @Scheduled(cron = "0/10 * * ? * *", zone = "CET")
    public void scheduleUpdateConversionRates() {
        log.info("Cron!");
        try {
            inMemoryDataService.reloadData();
        } catch (Exception e) {
            log.error("Exception during reloading ecb data. Exception was: {}", e);
        }
        //TODO to delete -
        inMemoryDataService.getConversionRateModels().forEach(conversionRateModel -> {
            log.info("Conver: {}", conversionRateModel);
        });
    }

}
