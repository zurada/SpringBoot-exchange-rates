# Avrios Foreign Exchange Rate Service #

### What is this repository for? ###

Sample project for backend developers in recruitment process for Avrios company.
This is a simple exchange rate service exchanging EUR to any other currency provided by the European Central Bank
using up-to-date exchange rates. 

### How do I get set up? ###

There are two approaches: 

1. Intellij or Eclipse IDE: Open project as Maven project and start main class com.avrios.sample.exchange.Application
2. Use maven and than run the service
   * Run in project dir: $ mvn clean install 
   * go to target dir and run: $ java -jar exchange-<VERSION>.jar

### How to use the service? ###

1. run application and go to http://localhost:8080/swagger-ui.html
2. try api/rates/get/one endpoint via swagger or postman:

Example request:
POST api/rates/get/one
{
  "dateOfConversion": "2017-08-21",
  "targetCurrency": "USD"
}
Date format is yyyy-MM-dd

Example response:
{
  "targetCurrency": "USD",
  "rate": "1.1761",
  "date": "2017-08-21"
}

Error codes (http status 400):
    INCORRECT_REQUEST_BODY(1000) - when sending wrong JSON
    DATE_WRONG_FORMAT(1001) - when using wrong date pattern
    ERROR_LOADING_ECB_DATA(1002) - when there is connection or unmarshalling problem with ECB
    NO_SUCH_DATE(1003) - when using too old date (older than 90 days)
    INTERNAL_ERROR_SERVER_HAS_NO_DATA(1004) - when server has problem and has no data about reference rates in memory (fatal error)
    NO_SUCH_CURRENCY(1005) - when using wrong currency

Error example response:
{
  "errorCode": 1005,
  "details": "Currency code is invalid"
}



### Who do I talk to? ###

Written by Adam Zurada: adam.zurada@gmail.com, +48 889 225 530