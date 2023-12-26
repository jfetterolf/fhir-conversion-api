package com.edi.smooky.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

// smooky stuff
import org.smooks.api.SmooksException;
import org.xml.sax.SAXException;
import java.io.IOException;

// service stuff
import com.edi.smooky.service.EdiService;

// micrometer metrics
import io.micrometer.prometheus.PrometheusMeterRegistry;

@CrossOrigin
@RequestMapping("api")
@RestController
public class EdiController {

    private final PrometheusMeterRegistry ediRegistry;
    public EdiController(PrometheusMeterRegistry ediRegistry) {
        this.ediRegistry = ediRegistry;
    };

    @GetMapping("/edi")
    public String HelloWorld() {

        // Collecting metric: api_edi_get
        ediRegistry.counter("api.edi.get").increment();

        return "I exist and am ready to take in an EDI message!";
    }

    @PostMapping("/edi")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public String registerEdiObject(@RequestBody String messageIn) throws IOException, SAXException, SmooksException {

        // Collecting metric: api_edi_post
        ediRegistry.counter("api.edi.post").increment();;

        return EdiService.transformEdiObject(messageIn);
            
    }

}
