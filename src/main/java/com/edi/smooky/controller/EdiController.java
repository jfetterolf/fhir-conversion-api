package com.edi.smooky.controller;

import java.io.IOException;

import org.smooks.api.SmooksException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.xml.sax.SAXException;

import com.edi.smooky.service.EdiService;
import com.edi.smooky.service.X12ToJsonService;

import io.micrometer.prometheus.PrometheusMeterRegistry;

@CrossOrigin
@RequestMapping("api")
@RestController
public class EdiController {

    // Metrics monitoring
    private final PrometheusMeterRegistry ediRegistry;
    public EdiController(PrometheusMeterRegistry ediRegistry) {
        this.ediRegistry = ediRegistry;
    };

    @GetMapping("/edi")
    public String helloEdi() {

        // Collecting metric: api_edi_get
        ediRegistry.counter("api.edi.get").increment();

        return "200 OK";
    }

    @PostMapping("/edi")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public String transformEdi(@RequestBody String messageIn) throws IOException, SAXException, SmooksException {

        // Collecting metric: api_edi_post
        ediRegistry.counter("api.edi.post").increment();

        return EdiService.transformEdiObject(messageIn);
            
    }

    @GetMapping("/x12")
    public String helloX12() {

        // Collecting metric: api_edi_get
        ediRegistry.counter("api.x12.get").increment();

        return "200 OK";
    }

    @PostMapping("/x12")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public String transformX12(@RequestBody String messageIn) throws IOException {

        // Collecting metric: api_edi_post
        ediRegistry.counter("api.x12.post").increment();

        return X12ToJsonService.transformX12837Object(messageIn);
            
    }

    @GetMapping("/x12271")
    public String helloX12271() {

        // Collecting metric: api_edi_get
        ediRegistry.counter("api.x12271.get").increment();

        return "200 OK";
    }

    @PostMapping("/x12271")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public String transformX12271(@RequestBody String messageIn) throws IOException {

        // Collecting metric: api_edi_post
        ediRegistry.counter("api.x12271.post").increment();

        return X12ToJsonService.transformX12271Object(messageIn);
            
    }

    @GetMapping("/x12270")
    public String helloX12270() {

        // Collecting metric: api_edi_get
        ediRegistry.counter("api.x12270.get").increment();

        return "200 OK";
    }

    @PostMapping("/x12270")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public String transformX1227(@RequestBody String messageIn) throws IOException {

        // Collecting metric: api_edi_post
        ediRegistry.counter("api.x12270.post").increment();

        return X12ToJsonService.transformX12271Object(messageIn);
            
    }



}
