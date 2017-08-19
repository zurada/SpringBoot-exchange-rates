package com.avrios.sample.exchange.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SampleController {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @ResponseBody
    @RequestMapping("/api/hello/{name}")
    public String helloWorld(@PathVariable String name) {
        log.debug("Logging works!");
        return "Hello " + name + "!";
    }
}
