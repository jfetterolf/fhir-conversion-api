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

        // Smooks transformation going from input x12 to XML output
        String messageOutXml = EdiService.runSmooksTransform(messageIn.getBytes());

        // Convering the resulting XML to a JSON output
        String messageOutJson = convert(messageOutXml.toString());

        // Where the JSON needs to be saved - this is the file that the FatJarService looks to use
        String jsonFilePath = "src/main/output/smooksOutput.json";

        // Clear the file if it exists and write in its place
        deleteFile(jsonFilePath);
        writeJson(jsonFilePath, messageOutJson);

        // Execute FatJarService that runs the WSTL transformation
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
