package com.avrios.sample.exchange.service;

import com.avrios.sample.exchange.dto.ConversionRateInDto;
import com.avrios.sample.exchange.dto.ConversionRateOutDto;
import com.avrios.sample.exchange.exception.CustomException;
import com.avrios.sample.exchange.exception.ErrorCode;
import com.avrios.sample.exchange.model.ConversionRateModel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ConversionRatesServiceImpl implements ConversionRatesService {

    private final InMemoryDataService inMemoryDataService;

    @Override
    public ConversionRateOutDto getConversionRate(ConversionRateInDto conversionRateInDto) {
        List<ConversionRateModel> allData = inMemoryDataService.getConversionRateModels();

        checkIfEcgDataIsLoadedSuccessfully(allData);

        List<ConversionRateModel> onlyTargetCurrencies = allData
                .stream()
                .filter((rateModel) -> filterByTargetCurrency(rateModel, conversionRateInDto))
                .collect(Collectors.toList());

        checkIfContainsRequestedCurrency(onlyTargetCurrencies);

        Optional<ConversionRateModel> resultRateModelWithTargetCurrencyAndDate = onlyTargetCurrencies
                .stream()
                .filter(rateModel -> filterByDate(rateModel, conversionRateInDto))
                .findFirst();
        ConversionRateModel result = resultRateModelWithTargetCurrencyAndDate
                .orElseGet(() -> getFirstConversionRateModelBeforeRequestedDate(onlyTargetCurrencies, conversionRateInDto));

        return ConversionRateOutDto
                .builder()
                .rate(result.getRate())
                .targetCurrency(result.getCurrency().getCurrencyCode())
                .date(result.getDate())
                .build();
    }

    /**
     * Filters by date
     *
     * @param rateModel           rate model from collection
     * @param conversionRateInDto requested conversion rate dto with desired date
     * @return true when dates are equal
     */
    private boolean filterByDate(ConversionRateModel rateModel, ConversionRateInDto conversionRateInDto) {
        return rateModel.getDate().isEqual(conversionRateInDto.getDateOfConversion());
    }

    /**
     * Filters by currency name
     *
     * @param rateModel           rate model from collection
     * @param conversionRateInDto requested conversion rate dto with desired currency
     * @return true when currencies are equal
     */
    private boolean filterByTargetCurrency(ConversionRateModel rateModel, ConversionRateInDto conversionRateInDto) {
        return rateModel
                .getCurrency()
                .getCurrencyCode()
                .equals(conversionRateInDto.getTargetCurrency());
    }

    /**
     * Checks if there is data loaded from ECB
     *
     * @param allData should contain all ECB data for 90 days with reference rates
     */
    private void checkIfEcgDataIsLoadedSuccessfully(List<ConversionRateModel> allData) {
        if (allData.isEmpty()) {
            throw new CustomException(ErrorCode.INTERNAL_ERROR_SERVER_HAS_NO_DATA);
        }
    }

    /**
     * Checks if contains requested currency
     *
     * @param onlyTargetCurrencies list of filtered results with desired currency
     * @throws throws runtimeException when no such currency is found
     */
    private void checkIfContainsRequestedCurrency(List<ConversionRateModel> onlyTargetCurrencies) {
        if (onlyTargetCurrencies.isEmpty()) {
            throw new CustomException(ErrorCode.NO_SUCH_CURRENCY);
        }
    }

    /**
     * When there is no entry for particular day the method checks what is actual rate with date before the requested date
     *
     * @param onlyTargetCurrencies list of filtered results with desired currency
     * @param conversionRateInDto  requested conversion rate dto with desired currency
     * @return returns first older conversion rate with date before the requested date
     * @throws throws runtimeException when date is too old and there is no older entry to return
     */
    private ConversionRateModel getFirstConversionRateModelBeforeRequestedDate(List<ConversionRateModel> onlyTargetCurrencies, ConversionRateInDto conversionRateInDto) {
        return onlyTargetCurrencies
                .stream()
                .sorted()
                .filter(conversionRateModel ->
                        conversionRateModel.getDate().isBefore(conversionRateInDto.getDateOfConversion()))
                .findFirst()
                .orElseThrow(() -> new CustomException(ErrorCode.NO_SUCH_DATE));
    }
}
