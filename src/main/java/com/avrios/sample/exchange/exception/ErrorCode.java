package com.avrios.sample.exchange.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    INCORRECT_REQUEST_BODY(1000),
    DATE_WRONG_FORMAT(1001),
    ERROR_LOADING_ECB_DATA(1002),
    NO_SUCH_DATE(1003),
    INTERNAL_ERROR_SERVER_HAS_NO_DATA(1004),
    NO_SUCH_CURRENCY(1005);
    private final int code;

    public static class Constants {
        public final static String DATE_WRONG_FORMAT_MSG = "Valid format is yyyy-MM-dd";
        public final static String NO_SUCH_DATE_MSG = "Available dates are 90 days in the past";
        public final static String INTERNAL_ERROR_SERVER_HAS_NO_DATA_MSG = "Problem with the server. Please contact an administrator";
        public final static String NO_SUCH_CURRENCY_MSG = "Currency code is invalid";
    }
}
