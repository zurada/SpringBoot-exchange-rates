package com.avrios.sample.exchange.scheduling;


public interface CronSchedulingService {

    /**
     * Scheduled update of conversion rates from ECB
     */
    void scheduleUpdateConversionRates();
}
