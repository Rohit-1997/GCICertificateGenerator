package com.gci.certificategenerator.web.rest;

import com.gci.certificategenerator.logger.Loggers;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CertificateGeneratorController {
    private static final Logger log = Loggers.APPLICATION_LOG;
    @GetMapping("systemStatus")
    public ResponseEntity<String> healthCheck() {
        log.info("REST request for healthCheck");
        return new ResponseEntity<>("Service up", HttpStatus.OK);
    }
}
