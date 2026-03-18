package com.eazybytes.jensenstore.controller;

import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/api/v1/logging")
public class LoggingController {
    @GetMapping
    public ResponseEntity<String> testLogging() {
        // 2. 使用不同級別記錄訊息
        log.trace("TRACE: This is a very detailed trace log.");
        log.debug("DEBUG: This is a debug message.");
        log.info("INFO: This is an informational message.");
        log.warn("WARN: This is a warning!");
        log.error("ERROR: An error occurred!");

        return ResponseEntity.ok().body("Logging tested successfully");
    }
}
