package com.edi.smooky.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.InterruptedIOException;

public interface FatJarService {

    public static String executeFatJar() throws IOException, InterruptedIOException {

        ProcessBuilder pb = new ProcessBuilder("java", "-jar", "./runtime-dev-SNAPSHOT-all.jar");
        Process p = pb.start();
        BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));

        StringBuilder builder = new StringBuilder();
        String line = null;
        while( (line = in.readLine()) != null) {
            builder.append(line);
            builder.append(System.getProperty("line.separator"));
        }
        String result = builder.toString();
        // System.out.println(result);

        return result;
    }
    
}
