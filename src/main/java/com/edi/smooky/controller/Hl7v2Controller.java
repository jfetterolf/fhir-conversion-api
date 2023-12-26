package com.edi.smooky.controller;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.edi.smooky.service.Hl7v2Service;

// import io.github.linuxforhealth.hl7.HL7ToFHIRConverter;

// micrometer metrics
import io.micrometer.prometheus.PrometheusMeterRegistry;

@CrossOrigin
@RequestMapping("api")
@RestController
public class Hl7v2Controller {

    private final PrometheusMeterRegistry hl7Registry;
    public Hl7v2Controller(PrometheusMeterRegistry hl7Registry) {
        this.hl7Registry = hl7Registry;
    };
    
    @GetMapping("/hl7")
    public String HelloWorld() {
        
        // Collecting metric: api_hl7_get
        hl7Registry.counter("api.hl7.get").increment();

        return "I exist and am ready to take in a HL7 message!";
    }

    @PostMapping("/hl7")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public String registerHl7String(@RequestBody String hl7Message) throws IOException {

        // Collecting metric: api_hl7_post
        hl7Registry.counter("api.hl7.post").increment();

        return Hl7v2Service.transformHl7V2Object(hl7Message);
    }
    
}
