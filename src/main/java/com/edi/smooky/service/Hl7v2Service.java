package com.edi.smooky.service;

import java.io.IOException;
import org.json.JSONObject;
import io.github.linuxforhealth.hl7.HL7ToFHIRConverter;

public interface Hl7v2Service {

    public static String transformHl7V2Object(String hl7Message) throws IOException{

        // Instantiate converter and convert input hl7Message
        HL7ToFHIRConverter ftv = new HL7ToFHIRConverter();
        String output = ftv.convert(hl7Message);
    
        // using JSONObject to get formatted output
        JSONObject json = new JSONObject(output);

        return json.toString(4);
    }

}
