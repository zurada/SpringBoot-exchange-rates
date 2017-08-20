package com.avrios.sample.exchange.service;

import com.avrios.sample.exchange.ApplicationTest;
import com.avrios.sample.exchange.exception.CustomException;
import junit.framework.TestCase;
import lombok.extern.slf4j.Slf4j;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApplicationTest.class)
@Slf4j
public class InMemoryDataServiceTest extends TestCase {


    @Autowired
    private InMemoryDataServiceImpl inMemoryDataService;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void givenCorrectAddress_whenBeanIsReady_thenReturnNotEmptyListOfConversionRates(){
        assertTrue(inMemoryDataService.getConversionRateModels().size() > 0);
    }

    @Test
    public void givenIncorrectAddress_whenGettingEcbData_thenReturnException(){
        inMemoryDataService.ECB_REFERENCE_RATES_URL = "http://wrong-address.test.test.test.ch";
        thrown.expect(CustomException.class);
        inMemoryDataService.initConversionRatesData();
        inMemoryDataService.ECB_REFERENCE_RATES_URL = "http://www.ecb.europa.eu/stats/eurofxref/eurofxref-hist-90d.xml";
    }

}
