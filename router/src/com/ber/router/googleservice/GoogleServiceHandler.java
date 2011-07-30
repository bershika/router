/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ber.router.googleservice;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.jdom.Document;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

/**
 *
 * @author anna
 */
public class GoogleServiceHandler {

    final String ADDRESS = "http://maps.googleapis.com/maps/api/";
    final String DIRECTIONS = "directions";
    final String XML = "xml";
    final char SLASH = '/';
    final char GET_PARAM_START = '?';
    final char AND = '&';
    final char EQ = '=';
    final String ORI = "origin";
    final String DEST = "destination";
    final String CONTENT_TYPE = "text/";
    final String FILE_NAME = "DirectionsResponse.xml";
    final String SENSOR = "&sensor=false";
    final String UNITS = "&units=imperial";
    final float METERS_IN_MILE = 1609.344F;
    final String KEY = "&key=ABQIAAAAgx8hTp__FqoELKq8_PRQFBQmR2Mb3ziOrVjSITWYWI8PHE-SdhSSxs_DzW02CKPLLMxteHoFinlwbQ";

    public int getDirectionsService(String origin, String dest) 
            throws MalformedURLException, IOException,
            JDOMException, Exception {
        String requestUrl = ADDRESS + DIRECTIONS + SLASH
                + XML + GET_PARAM_START + ORI + EQ + origin
                + AND + DEST + EQ + dest + SENSOR + UNITS;
        
        URL url = new URL(requestUrl);
//        URLConnection uc = url.openConnection();
//
//
//
//        String contentType = uc.getContentType();
//        int contentLength = uc.getContentLength();
//        if (contentType.startsWith(CONTENT_TYPE) || contentLength == -1) {
//            throw new IOException("This is not a binary file.");
//        }
        SAXBuilder builder = new SAXBuilder();  // parameters control validation, etc
        Document doc = builder.build(url);
        if(!doc.getRootElement().getChildText("status").equalsIgnoreCase("ok")){
            throw new Exception("Google response for " + origin + " - "
                    + dest + " :" + doc.getRootElement().getChildText("status"));
        }

//        HttpServletResponse res= FacesContext.getCurrentInstance().getExternalContext().getResponse();
//		ServletOutputStream out = res.getOutputStream();
//        XMLOutputter outputter = new XMLOutputter();
//        outputter.output(doc, out);

        String distanceText = doc.getRootElement().getChild("route").
                getChild("leg").getChild("distance").getChild("value").getText();

//        InputStream raw = uc.getInputStream();
//        InputStream in = new BufferedInputStream(raw);
//        byte[] data = new byte[contentLength];
//        int bytesRead = 0;
//        int offset = 0;
//        while (offset < contentLength) {
//            bytesRead = in.read(data, offset, data.length - offset);
//            if (bytesRead == -1) {
//                break;
//            }
//            offset += bytesRead;
//        }
//        in.close();
//
//        if (offset != contentLength) {
//            throw new IOException("Only read " + offset + " bytes; Expected " + contentLength + " bytes");
//        }
//
//        //String filename = u.getFile().substring(filename.lastIndexOf('/') + 1);
//        FileOutputStream out = new FileOutputStream(FILE_NAME);
//
//        out.write(data);
//        out.flush();
//        out.close();


        return convertToMiles(distanceText);
    }

    private int convertToMiles(String text) throws Exception {
        String distStr = text.trim();
        int meters = 0;
        try{
        meters = Integer.valueOf(distStr);
        }catch(Exception ex){throw ex;}
        return Math.round(meters / METERS_IN_MILE);
    }
}
