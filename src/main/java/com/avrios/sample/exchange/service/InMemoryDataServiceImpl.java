package com.avrios.sample.exchange.service;

import com.avrios.sample.exchange.exception.CustomException;
import com.avrios.sample.exchange.exception.ErrorCode;
import com.avrios.sample.exchange.model.ConversionRateModel;
import com.avrios.sample.exchange.xml.Envelope;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.PostConstruct;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import java.net.URL;
import java.util.Currency;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Slf4j
public class InMemoryDataServiceImpl implements InMemoryDataService {

    //TODO put into configuration
    public static String ECB_REFERENCE_RATES_URL = "http://www.ecb.europa.eu/stats/eurofxref/eurofxref-hist-90d.xml";

    private List<ConversionRateModel> conversionRateModels;

    @Override
    @PostConstruct
    public void initConversionRatesData() {
        try {
            reloadData();
        } catch (Exception e) {
            log.error("Failed loading initial data. Exiting now. Exception was: {}", e);
            throw new CustomException(ErrorCode.ERROR_LOADING_ECB_DATA, e.getMessage());
        }
    }

    @Override
    public List<ConversionRateModel> getConversionRateModels() {
        return conversionRateModels;
    }

    private void setConversionRateModels(List<ConversionRateModel> conversionRateModels) {
        this.conversionRateModels = conversionRateModels;
    }

    @Override
    public void reloadData() throws Exception {
        log.info("Reloading conversion rates");
        List<ConversionRateModel> tempList = new CopyOnWriteArrayList<>();
        // define data source
        URL url = new URL(ECB_REFERENCE_RATES_URL);
        //URL url = new URL("http://www.ecb.europa.eu/stats/eurofxref/eurofxref-daily.xml");

        // parse XML
        JAXBContext context = JAXBContext.newInstance(Envelope.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        Envelope envelope = (Envelope) unmarshaller.unmarshal(url);

        // loop through data
        Currency euro = Currency.getInstance("EUR");
        envelope.getCube()
                .getCubes()
                .forEach(
                        timeCube ->
                        {
                            Date date = timeCube.getTime();
                            timeCube.getCubes().forEach(cube -> {
                                Currency currency = Currency.getInstance(cube.getCurrency());
                                String rate = cube.getRate();
                                log.debug(date.toString() + ": 1 " + euro.getSymbol() + " = " + rate + " " + currency.getSymbol());
                                tempList.add(
                                        ConversionRateModel
                                                .builder()
                                                .date(date)
                                                .rate(rate)
                                                .currency(currency)
                                                .build());
                            });
                        }
                );
        setConversionRateModels(tempList);

    }


}
