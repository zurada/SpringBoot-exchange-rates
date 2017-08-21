package com.avrios.sample.exchange.web;

import com.avrios.sample.exchange.ApplicationTest;
import com.avrios.sample.exchange.exception.ErrorCode;
import com.avrios.sample.exchange.model.ConversionRateModel;
import com.avrios.sample.exchange.service.InMemoryDataService;
import com.avrios.sample.exchange.service.InMemoryDataServiceImpl;
import junit.framework.TestCase;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Currency;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApplicationTest.class)
@Slf4j
public class ConversionRatesControllerTest extends TestCase {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private InMemoryDataService inMemoryDataService;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .build();
    }

    @Test
    public void givenValidDto_whenGetOneRate_thenReturnOK() throws Exception {
        inMemoryDataService.getConversionRateModels().clear();
        inMemoryDataService.getConversionRateModels().add(
                ConversionRateModel
                        .builder()
                .currency(Currency.getInstance("USD"))
                .date(LocalDate.now())
                .rate("1.5")
                .build()
        );
        String today = LocalDate.now().format(FORMATTER);
        String conversionRateInDto = "{\n" +
                "\"targetCurrency\": \"USD\",\n" +
                "\"dateOfConversion\" : \""+today+"\"\n" +
                "}";

        log.info(mockMvc.perform(
                post("/api/rates/get/one")
                        .content(conversionRateInDto)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn().getResponse().getContentAsString());
    }

    @Test
    public void givenWrongCurrency_whenGetOneRate_thenReturnError() throws Exception {
        inMemoryDataService.getConversionRateModels().clear();
        inMemoryDataService.getConversionRateModels().add(
                ConversionRateModel
                        .builder()
                        .currency(Currency.getInstance("USD"))
                        .date(LocalDate.now())
                        .rate("1.5")
                        .build()
        );
        String today = LocalDate.now().format(FORMATTER);
        String conversionRateInDto = "{\n" +
                "\"targetCurrency\": \"TESTNOTEXISTING\",\n" +
                "\"dateOfConversion\" : \""+today+"\"\n" +
                "}";

        log.info(mockMvc.perform(
                post("/api/rates/get/one")
                        .content(conversionRateInDto)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorCode", is(ErrorCode.NO_SUCH_CURRENCY.getCode())))
                .andReturn().getResponse().getContentAsString());
    }

    @Test
    public void givenNoDataOnServerSide_whenGetOneRate_thenReturnError() throws Exception {
        inMemoryDataService.getConversionRateModels().clear();
        String today = LocalDate.now().format(FORMATTER);
        String conversionRateInDto = "{\n" +
                "\"targetCurrency\": \"USD\",\n" +
                "\"dateOfConversion\" : \""+today+"\"\n" +
                "}";

        log.info(mockMvc.perform(
                post("/api/rates/get/one")
                        .content(conversionRateInDto)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorCode", is(ErrorCode.INTERNAL_ERROR_SERVER_HAS_NO_DATA.getCode())))
                .andReturn().getResponse().getContentAsString());
    }

    @Test
    public void givenNotExistingDate_whenGetOneRate_thenReturnOneRateBeforeRequestedDate() throws Exception {
        inMemoryDataService.getConversionRateModels().clear();
        LocalDate yesterday = LocalDate.now().minusDays(1);
        inMemoryDataService.getConversionRateModels().add(
                ConversionRateModel
                        .builder()
                        .currency(Currency.getInstance("USD"))
                        .date(yesterday)
                        .rate("1.5")
                        .build()
        );
        inMemoryDataService.getConversionRateModels().add(
                ConversionRateModel
                        .builder()
                        .currency(Currency.getInstance("USD"))
                        .date(yesterday.minusDays(1))
                        .rate("1.4")
                        .build()
        );
        inMemoryDataService.getConversionRateModels().add(
                ConversionRateModel
                        .builder()
                        .currency(Currency.getInstance("USD"))
                        .date(yesterday.minusDays(2))
                        .rate("1.3")
                        .build()
        );
        String notExistingToday = LocalDate.now().format(FORMATTER);
        String conversionRateInDto = "{\n" +
                "\"targetCurrency\": \"USD\",\n" +
                "\"dateOfConversion\" : \""+notExistingToday+"\"\n" +
                "}";

        log.info(mockMvc.perform(
                post("/api/rates/get/one")
                        .content(conversionRateInDto)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.dateOfOperation").value(yesterday.toString()))
                .andReturn().getResponse().getContentAsString());
    }

    @Test
    public void givenTooOldDate_whenGetOneRate_thenReturnError() throws Exception {
        inMemoryDataService.getConversionRateModels().clear();
        LocalDate tomorrow = LocalDate.now().plusDays(1);
        inMemoryDataService.getConversionRateModels().add(
                ConversionRateModel
                        .builder()
                        .currency(Currency.getInstance("USD"))
                        .date(tomorrow)
                        .rate("1.5")
                        .build()
        );
        String notExistingToday = LocalDate.now().format(FORMATTER);
        String conversionRateInDto = "{\n" +
                "\"targetCurrency\": \"USD\",\n" +
                "\"dateOfConversion\" : \""+notExistingToday+"\"\n" +
                "}";

        log.info(mockMvc.perform(
                post("/api/rates/get/one")
                        .content(conversionRateInDto)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorCode", is(ErrorCode.NO_SUCH_DATE.getCode())))
                .andReturn().getResponse().getContentAsString());
    }

    @Test
    public void givenWrongDateFormat_whenGetOneRate_thenReturnError() throws Exception {

        String conversionRateInDto = "{\n" +
                "\"targetCurrency\": \"EUR\",\n" +
                "\"dateOfConversion\" : \"2002.02.02\"\n" +
                "}";

        log.info(mockMvc.perform(
                post("/api/rates/get/one")
                        .content(conversionRateInDto)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorCode", is(ErrorCode.DATE_WRONG_FORMAT.getCode())))
                .andReturn().getResponse().getContentAsString());


        String conversionRateInDto2 = "{\n" +
                "\"targetCurrency\": \"EUR\",\n" +
                "\"dateOfConversion\" : \"02-02-2002\"\n" +
                "}";

        log.info(mockMvc.perform(
                post("/api/rates/get/one")
                        .content(conversionRateInDto2)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorCode", is(ErrorCode.DATE_WRONG_FORMAT.getCode())))
                .andReturn().getResponse().getContentAsString());
    }

    @Test
    public void givenInvalidRequestBody_whenGetOneRate_thenReturnError() throws Exception {

        String conversionRateInDto = "{\n" +
                "\"targetCurrency\": \"EUR\"\n" +
                "}";

        log.info(mockMvc.perform(
                post("/api/rates/get/one")
                        .content(conversionRateInDto)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorCode", is(ErrorCode.INCORRECT_REQUEST_BODY.getCode())))
                .andReturn().getResponse().getContentAsString());


        String conversionRateInDto2 = "{\n" +
                "\"dateOfConversion\" : \"2002-02-02\"\n" +
                "}";

        log.info(mockMvc.perform(
                post("/api/rates/get/one")
                        .content(conversionRateInDto2)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorCode", is(ErrorCode.INCORRECT_REQUEST_BODY.getCode())))
                .andReturn().getResponse().getContentAsString());

        String conversionRateInDto3 = "{\n" +
                "\"targetCurrency\": \"\",\n" +
                "\"dateOfConversion\" : \"2002-02-02\"\n" +
                "}";

        log.info(mockMvc.perform(
                post("/api/rates/get/one")
                        .content(conversionRateInDto3)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorCode", is(ErrorCode.INCORRECT_REQUEST_BODY.getCode())))
                .andReturn().getResponse().getContentAsString());

        String conversionRateInDto4 = "{\n" +
                "\"targetCurrency\": \"EUR\",\n" +
                "\"dateOfConversion\" : \"\"\n" +
                "}";

        log.info(mockMvc.perform(
                post("/api/rates/get/one")
                        .content(conversionRateInDto4)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorCode", is(ErrorCode.INCORRECT_REQUEST_BODY.getCode())))
                .andReturn().getResponse().getContentAsString());
    }
}
