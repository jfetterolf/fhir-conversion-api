package com.edi.smooky.service;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.xml.transform.stream.StreamSource;

import org.json.JSONObject;
import org.json.XML;
import org.smooks.Smooks;
import org.smooks.api.ExecutionContext;
import org.smooks.api.SmooksException;
import org.smooks.io.payload.StringResult;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;

@Service
public interface EdiService {

    public static String transformEdiObject(String messageIn) throws IOException, SAXException, SmooksException {

        // System.out.println("Input EDI Message: ");
        // System.out.println(messageIn);

        // The transform outputs to XML
        // String messageOutXml = EdiController.runSmooksTransform(messageIn.getBytes());
        String messageOutXml = EdiService.runSmooksTransform(messageIn.getBytes());

        String messageOutJson = convert(messageOutXml.toString());

        String jsonFilePath = "src/main/output/smooksOutput.json";

        deleteFile(jsonFilePath);
        writeJson(jsonFilePath, messageOutJson);

        // System.out.println("Output JSON Message: ");
        // System.out.println(messageOutJson);

        // System.out.println("**********************************************************************");

        // System.out.println("FHIR Transformation Result: ");
        try {
            String wstlTransformation = FatJarService.executeFatJar();
            return wstlTransformation.toString();
        } catch (Exception exception) {
            return exception.toString();
        } 
    
    }

    public static String runSmooksTransform(byte[] messageIn) throws IOException, SAXException, SmooksException {
        // Instantiate Smooks with the config file...

        
        Smooks smooks = new Smooks("/home/jon/smooky/src/main/resources/smooks/smooks-config-837.xml");
        try {
             // Create an exec context - no profiles....
            ExecutionContext executionContext = smooks.createExecutionContext();

            StringResult result = new StringResult();

            // Filter the input message to the outputWriter, using the execution context...
            smooks.filterSource(executionContext, new StreamSource(new ByteArrayInputStream(messageIn)), result);         
            
            return result.getResult();

        } finally {
            smooks.close();
        }
    }

    public static String convert(String xml) throws IOException {

        // Convert XML to JSON
        JSONObject json = XML.toJSONObject(xml);

        // Return Output
        return json.toString(4);
    }

    public static void deleteFile(String filePath) {
        File fileToDelete = new File(filePath);

        if (fileToDelete.exists()) {
            fileToDelete.delete();
        }
    }

    public static void writeJson(String filePath, String jsonString) {
        try {
            Path path = Paths.get(filePath);
            Files.write(path, jsonString.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
