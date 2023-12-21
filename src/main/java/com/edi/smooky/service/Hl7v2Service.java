package com.edi.smooky.service;

import java.io.IOException;

import io.github.linuxforhealth.hl7.HL7ToFHIRConverter;

public interface Hl7v2Service {

    public static String transformHl7V2Object(String hl7Message) throws IOException{

        // System.out.println("Input HL7 message: ");
        // System.out.println(hl7Message);

        HL7ToFHIRConverter ftv = new HL7ToFHIRConverter();
        String output = ftv.convert(hl7Message);
    

        // System.out.println("Output FHIR Message: ");
        // System.out.println(output);

        return output;
    }

}
