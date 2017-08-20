package com.avrios.sample.exchange.service;


import com.avrios.sample.exchange.model.ConversionRateModel;

import java.util.List;

public interface InMemoryDataService {

    /**
     * called in post-construct on application startup to load initial rates data
     */
    void initConversionRatesData();

    /**
     * @return list of all conversion rates consiting of date, rate and currency
     */
    List<ConversionRateModel> getConversionRateModels();

    /**
     * reloads data by calling ECB rest api and updating the conversion rates list
     *
     * @throws Exception when problems with connection or unmarshalling the content
     */
    void reloadData() throws Exception;
}
