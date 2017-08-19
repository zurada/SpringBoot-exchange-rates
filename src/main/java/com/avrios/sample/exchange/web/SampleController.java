package com.avrios.sample.exchange.web;

import com.avrios.sample.exchange.dto.SampleDto;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
public class SampleController {

    @ResponseBody
    @GetMapping("/api/hello/{name}")
    public ResponseEntity<SampleDto> helloWorld(@PathVariable String name) {
        log.info("Logging for name: {}", name);
        return ResponseEntity.ok(SampleDto.builder().name(name).build());
    }
}
