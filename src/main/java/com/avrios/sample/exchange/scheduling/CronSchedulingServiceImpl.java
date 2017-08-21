package com.avrios.sample.exchange.scheduling;

import com.avrios.sample.exchange.service.InMemoryDataService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class CronSchedulingServiceImpl implements CronSchedulingService {

    private final InMemoryDataService inMemoryDataService;

    @Override
    @Scheduled(cron = "${ecb.update-rates.cron}", zone = "CET")
    public void scheduleUpdateConversionRates() {
        log.info("Running scheduled task for updating conversion rates.");
        try {
            inMemoryDataService.reloadData();
        } catch (Exception e) {
            log.error("Exception during reloading ecb data. Exception was: {}", e);
        }
    }

}
