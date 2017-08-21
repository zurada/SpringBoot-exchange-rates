package com.avrios.sample.exchange.service;

import com.avrios.sample.exchange.dto.ConversionRateInDto;
import com.avrios.sample.exchange.dto.ConversionRateOutDto;
import com.avrios.sample.exchange.exception.CustomException;
import com.avrios.sample.exchange.exception.ErrorCode;
import com.avrios.sample.exchange.model.ConversionRateModel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ConversionRatesServiceImpl implements ConversionRatesService {

    private InMemoryDataService inMemoryDataService;

    @Override
    public ConversionRateOutDto getConversionRate(ConversionRateInDto conversionRateInDto) {
        List<ConversionRateModel> allData = inMemoryDataService.getConversionRateModels();

        isEcbDataLoadedSuccessfuly(allData);

        List<ConversionRateModel> onlyTargetCurrencies = allData
                .stream()
                .filter(conversionRateModel -> conversionRateModel
                        .getCurrency()
                        .getCurrencyCode()
                        .equals(conversionRateInDto.getTargetCurrency()))
                .collect(Collectors.toList());

        containsRequestedCurrency(onlyTargetCurrencies);

        Optional<ConversionRateModel> resultRateModelWithTargetCurrencyAndDate = onlyTargetCurrencies
                .stream()
                .filter(conversionRateModel ->
                        conversionRateModel.getDate().isEqual(conversionRateInDto.getDateOfConversion()))
                .findFirst();
        ConversionRateModel result = resultRateModelWithTargetCurrencyAndDate
                .orElseGet(() -> getFirstConversionRateModelBeforeRequestedDate(onlyTargetCurrencies, conversionRateInDto));

        return ConversionRateOutDto
                .builder()
                .actualRate(result.getRate())
                .targetCurrency(result.getCurrency().getCurrencyCode())
                .dateOfOperation(result.getDate())
                .build();
    }


    @Override
    //TODO put cron exp into config file
    @Scheduled(cron = "0/10 * * ? * *", zone = "CET") //TODO change cron
    public void scheduleUpdateConversionRates() {
        log.info("Running scheduled task for updating conversion rates.");
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

    private void isEcbDataLoadedSuccessfuly(List<ConversionRateModel> allData) {
        if (allData.isEmpty()) {
            throw new CustomException(ErrorCode.INTERNAL_ERROR_SERVER_HAS_NO_DATA, ErrorCode.Constants.INTERNAL_ERROR_SERVER_HAS_NO_DATA_MSG); // no data on server
        }
    }


    private void containsRequestedCurrency(List<ConversionRateModel> onlyTargetCurrencies) {
        if (onlyTargetCurrencies.isEmpty()) {
            throw new CustomException(ErrorCode.NO_SUCH_CURRENCY, ErrorCode.Constants.NO_SUCH_CURRENCY_MSG);
        }
    }

    private ConversionRateModel getFirstConversionRateModelBeforeRequestedDate(List<ConversionRateModel> onlyTargetCurrencies, ConversionRateInDto conversionRateInDto) {
        return onlyTargetCurrencies
                .stream()
                .sorted()
                .filter(conversionRateModel ->
                        conversionRateModel.getDate().isBefore(conversionRateInDto.getDateOfConversion()))
                .findFirst()
                .orElseThrow(() -> new CustomException(ErrorCode.NO_SUCH_DATE, ErrorCode.Constants.NO_SUCH_DATE_MSG));
    }

}
