package com.avrios.sample.exchange.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    INCORRECT_REQUEST_BODY(1000, Constants.INCORRECT_REQUEST_BODY_MSG),
    DATE_WRONG_FORMAT(1001, Constants.DATE_WRONG_FORMAT_MSG),
    ERROR_LOADING_ECB_DATA(1002, Constants.ERROR_LOADING_ECB_DATA_MSG),
    NO_SUCH_DATE(1003, Constants.NO_SUCH_DATE_MSG),
    INTERNAL_ERROR_SERVER_HAS_NO_DATA(1004, Constants.INTERNAL_ERROR_SERVER_HAS_NO_DATA_MSG),
    NO_SUCH_CURRENCY(1005, Constants.NO_SUCH_CURRENCY_MSG);
    private final int code;
    private final String msg;

    public static class Constants {
        public final static String INCORRECT_REQUEST_BODY_MSG = "Invalid JSON request body";
        public final static String DATE_WRONG_FORMAT_MSG = "Valid format is yyyy-MM-dd";
        public final static String ERROR_LOADING_ECB_DATA_MSG = "Problem with connection to ECB";
        public final static String NO_SUCH_DATE_MSG = "Available dates are 90 days in the past";
        public final static String INTERNAL_ERROR_SERVER_HAS_NO_DATA_MSG = "Problem with the server. Please contact an administrator";
        public final static String NO_SUCH_CURRENCY_MSG = "Currency code is invalid";
    }
}
