package com.edi.smooky.service;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.List;

import org.json.JSONObject;
import org.json.XML;

import com.imsweb.x12.Loop;
import com.imsweb.x12.reader.X12Reader;
import com.imsweb.x12.reader.X12Reader.FileType;

public interface X12ToJsonService {

    public static void main(String[] args) throws IOException {
        String conversion = transformX12Object(ediMessage);
        System.out.println(conversion);
    }

    public static String transformX12Object(String x12Message) throws IOException {
        String xml = imsWebParse2Xml(x12Message);
        String json = convertXMLToJson(xml);
        return json;
    }

    public static String imsWebParse2Xml(String ediMessage) {
		try {

            X12Reader reader = new X12Reader(FileType.ANSI837_5010_X222, new StringReader(ediMessage));

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

    public String ediMessage = """
        ISA*00*          *01*SECRET    *ZZ*SUBMITTERS.ID  *ZZ*RECEIVERS.ID   *030101*1253*U*00501*000000905*1*T*:~
        GS*HC*SENDER CODE*RECEIVER CODE*19991231*0802*1*X*005010X222A1~
        ST*837*987654*005010X222A1~
        BHT*0019*00*0123*19960918*0932*CH~
        NM1*41*2*ANGELASZEK MEDICAL*****46*999999999~
        PER*IC*DAVID ANGELASZEK*TE*3016809770*EX*123~
        NM1*40*2*HEALTH RECEIVER*****46*111222333~
        HL*1**20*1~
        NM1*85*2*MEDICAL GROUP*****XX*1234567890~
        N3*3901 CALVERTON BLVD~
        N4*CALVERTON*MD*20705~
        REF*EI*123456789~
        PER*IC*JANE JONES*TE*3022893453~
        PER*IC*JANE JANES*TE*3012833053*EX*201~
        NM1*87*2~
        N3*227 LASTNER LANE~
        N4*GREENBELT*MD*20770~
        HL*2*1*22*1~
        SBR*P**SUBSCRIBER GROUP******CI~
        NM1*IL*1*DOE*JOHN*T**JR*MI*123456~
        NM1*PR*2*HEALTH INSURANCE COMPANY*****PI*11122333~
        CLM*A37YH556*500***11:B:1*Y*A*Y*I*P~
        HI*BK*8901*BF*87200*BF:5559~
        LX*1~
        SV1*HC:99211:25*12.25*UN*1*11**1:2:3**Y~
        DTP*472*RD8*20050314-20050325~
        HL*1**20*1~
        NM1*85*2*MEDICAL GROUP*****XX*1234567890~
        N3*3901 CALVERTON BLVD~
        N4*CALVERTON*MD*20705~
        REF*EI*123456789~
        PER*IC*JANE JONES*TE*3022893453~
        PER*IC*JANE JANES*TE*3012833053*EX*201~
        HL*2*1*22*1~
        SBR*P**SUBSCRIBER GROUP******CI~
        NM1*IL*1*DOE*JOHN*T**JR*MI*123456~
        NM1*PR*2*HEALTH INSURANCE COMPANY*****PI*11122333~
        CLM*A37YH556*500***11:B:1*Y*A*Y*I*P~
        HI*BK*8901*BF*87200*BF:5559~
        LX*1~
        SV1*HC:99211:25*12.25*UN*1*11**1:2:3**Y~
        DTP*472*RD8*20050314-20050325~
        SE*25*987654~
        GE*1*1~
        IEA*1*000000905~
        
            """;
}
