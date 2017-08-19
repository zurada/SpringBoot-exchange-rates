package com.avrios.sample.exchange.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    INCORRECT_REQUEST_BODY(1000),
    DATE_WRONG_FORMAT(1001);
    private final int code;

    public static class Constants {
        public final static String DATE_WRONG_FORMAT_MSG = "Valid format is yyyy-MM-dd";
    }
}
