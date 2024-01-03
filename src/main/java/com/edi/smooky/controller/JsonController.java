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

import com.edi.smooky.service.FatJarService;
import com.edi.smooky.service.JsonService;

import io.micrometer.prometheus.PrometheusMeterRegistry;;

@CrossOrigin
@RequestMapping("api")
@RestController
public class JsonController {
    
    // Metrics monitoring
    private final PrometheusMeterRegistry jsonRegistry;
    public JsonController(PrometheusMeterRegistry jsonRegistry) {
        this.jsonRegistry = jsonRegistry;
    };
    
    @GetMapping("/json")
    public String helloJson() {

        // Collecting metric: api_json_get
        jsonRegistry.counter("api.json.get").increment();

        return "200 OK";
    }

    @PostMapping("/json")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public String transformJSON(@RequestBody String jsonMessage) throws IOException {

        // Collecting metric: api_json_post
        jsonRegistry.counter("api.json.post").increment();

        return JsonService.transformJsonObject(jsonMessage);
    }


    // this PostMapping runs a fat JAR that looks for the .wstl configuration and input json file and returns the output JSON.
    @PostMapping("/pb")
    public String useFatJAR() throws IOException, InterruptedException {

        // Collecting metric: api_pb_post
        jsonRegistry.counter("api.pb.post").increment();

        return FatJarService.executeFatJar();
    }
    
}
