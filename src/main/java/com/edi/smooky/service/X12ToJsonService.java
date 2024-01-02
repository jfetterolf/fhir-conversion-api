package com.edi.smooky.service;

import java.io.IOException;
import java.io.StringReader;
import java.util.List;

import org.json.JSONObject;
import org.json.XML;

import com.imsweb.x12.Loop;
import com.imsweb.x12.reader.X12Reader;
import com.imsweb.x12.reader.X12Reader.FileType;

public interface X12ToJsonService {

    // JF - TODO: will need to set up/refactor for ther types of x12 (837, 834, 271)

    public static String transformX12837Object(String x12Message) throws IOException {
        String xml = imsWebParse837ToXml(x12Message, FileType.ANSI837_5010_X222);
        String json = convertXMLToJson(xml);
        return json;
    }

    public static String transformX12271Object(String x12Message) throws IOException {
        String xml = imsWebParse837ToXml(x12Message, FileType.ANSI271_4010_X092);
        String json = convertXMLToJson(xml);
        return json;
    }

    public static String transformX12270Object(String x12Message) throws IOException {
        String xml = imsWebParse837ToXml(x12Message, FileType.ANSI270_4010_X092);
        String json = convertXMLToJson(xml);
        return json;
    }

    public static String imsWebParse837ToXml(String ediMessage, FileType fileType) {
		try {

            X12Reader reader = new X12Reader(fileType, new StringReader(ediMessage));

			List<Loop> loops = reader.getLoops();
			Loop loop = loops.get(0);
			String xmlLoop = loop.toXML();

			return xmlLoop;

		} catch (IOException e) {

            return "Bad! Error Occured: " + e.toString();
        }
	}
    
    public static String convertXMLToJson(String xml) throws IOException {

        JSONObject json = XML.toJSONObject(xml);

        return json.toString(4);
    }

}
