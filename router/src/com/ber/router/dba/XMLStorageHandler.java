/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ber.router.dba;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Iterator;
import java.util.List;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

import com.ber.router.googleservice.GoogleServiceHandler;

/**
 *
 * @author anna
 */
public class XMLStorageHandler {

    final String PATH = "RoutesStorage.xml";
    File file;
    Document doc;
    //FileWriter writer;
    XMLOutputter outputter;
    GoogleServiceHandler google;
    static int googleCounter;
    

    public XMLStorageHandler() {
        try {
            googleCounter = 0;
            google = new GoogleServiceHandler();
            SAXBuilder builder = new SAXBuilder();
            file = new File(PATH);

            if (file.exists() & file.canRead() & file.canWrite()) {
                doc = builder.build(file);
            } else {

                file.createNewFile();
                createNewXMLStorage();
            }
        } catch (Exception ex) {
            try {
                file.delete();
                file.createNewFile();
                createNewXMLStorage();
            } catch (Exception ex1) {
                System.out.println(
                        "Unable to create a storage, exiting now at "
                        + file.getAbsolutePath());
                System.exit(-1);
            }
        }
    }

    public Object[][] getHubs() throws JDOMException, IOException {
        List<Object> hubsElm = doc.getRootElement().getChildren("hub");
        Double a1 = null;
        Double b1 = null;
        Double a2 = null;
        Double b2 = null;
        Integer rate = null;
        Object[][] hubs = new Object[hubsElm.size()][8];
        Iterator it = hubsElm.iterator();
        int i = 0;
        while (it.hasNext()) {
            Element hubElm = (Element) it.next();
            String a1Str = hubElm.getChild("formula").getChildText("a1");
            String b1Str = hubElm.getChild("formula").getChildText("b1");
            String a2Str = hubElm.getChild("formula").getChildText("a2");
            String b2Str = hubElm.getChild("formula").getChildText("b2");
            String rateStr = hubElm.getChildText("rate");
            if (a1Str != null) {
                a1 = Double.parseDouble(a1Str);
            }
            if (b1Str != null) {
                b1 = Double.parseDouble(b1Str);
            }
            if (a2Str != null) {
                a2 = Double.parseDouble(a2Str);
            }
            if (b2Str != null) {
                b2 = Double.parseDouble(b2Str);
            }
            if (rateStr != null) {
                rate = Integer.parseInt(rateStr);
            }
            Double inter = (b2 - b1) / (a1 - a2);
            Object[] hub = {hubElm.getChild("name").getText(), true,
                ((rate == null) ? "" : rate),
                ((a1 == null) ? "" : a1),
                ((b1 == null) ? "" : b1),
                ((a2 == null) ? "" : a2),
                ((b2 == null) ? "" : b2),
                ((inter == null) ? "" : inter)};
            hubs[i++] = hub;

        }

        return hubs;
    }

   
    public void createNewXMLStorage() throws IOException {
        Element root = new Element("root");
        doc = new Document(root);
        writeToXMLStorage();

    }

    public void writeToXMLStorage() throws IOException {
//        file.delete();
//        file.createNewFile();
        //writer = new FileWriter(file);
        outputter = new XMLOutputter(Format.getPrettyFormat());
//        outputter.output(doc, writer);
//        writer.flush();
//        writer.close();
    }

    public void addHub(String name, int r, double ca1, double cb1,
            double ca2, double cb2) throws IOException, Exception {
        if (name.isEmpty() || r <= 0) {
            throw new Exception("Not a valid hub info :" + name + " " + r);
        }
        Element hub = getHubByName(name);
        if (hub != null) {
            updateHub(name, r, ca1, cb1, ca2, cb2);
            return;
        }
        hub = new Element("hub");
        Element hubName = new Element("name");
        Element hubFormula = new Element("formula");
        Element rate = new Element("rate");
        Element a1 = new Element("a1");
        Element b1 = new Element("b1");
        Element a2 = new Element("a2");
        Element b2 = new Element("b2");
        rate.setText(String.valueOf(r));
        a1.setText(String.valueOf(ca1));
        b1.setText(String.valueOf(cb1));
        a2.setText(String.valueOf(ca2));
        b2.setText(String.valueOf(cb2));
        hubName.setText(name);
        hubFormula.addContent(a1);
        hubFormula.addContent(b1);
        hubFormula.addContent(a2);
        hubFormula.addContent(b2);
        hub.setContent(hubName);
        hub.addContent(hubFormula);
        hub.addContent(rate);
        doc.getRootElement().addContent(hub);
        writeToXMLStorage();


    }

    public Integer addDest(Element hub, String destName)
            throws MalformedURLException, IOException,
            JDOMException, Exception {
//        Element root = doc.getRootElement();
//        List<Element> hubList = root.getChildren("hub");
//        Iterator it = hubList.iterator();
//        Element hub = getHubByName(hubName);
        String hubName = hub.getChildText("name");
        if (hub == null) {
            throw new Exception("Adding destination "
                    + destName + ": hub " + hubName + " does not exist");
        }
        int dist = 0;
        try {
            dist = google.getDirectionsService(hubName, destName);
            googleCounter++;
        } catch (Exception ex) {
            throw ex;
        }
        Element toElm = new Element("to");
        Element destElm = new Element("dest");
        destElm.setText(destName);


        Element distElm = new Element("dist");
        distElm.setText(String.valueOf(dist));
        toElm.addContent(destElm);
        toElm.addContent(distElm);
        hub.addContent(toElm);
        //toList.add(toElm);

        //root.addContent(toList);
       
        outputter = new XMLOutputter(Format.getPrettyFormat());
        System.out.println("Adding " + destName + " to " + hubName);
        outputter.output(doc, System.out);
        writeToXMLStorage();
        return dist;

    }
    /**/

    public Integer getDistance(String hub, String dest) throws Exception {
        Element hubElm = getHubByName(hub);
        if (hubElm == null) {
            throw new Exception("Getting distance : hub " + hub + " does not exist;");
        }
        Integer dist = null;
        Element destElm = this.getDestByName(hubElm, dest);
        if (destElm == null) {
            try {
                dist = addDest(hubElm, dest);
            } catch (Exception ex) {
                throw ex;
            }
            return dist;
        } else {
            String distStr = destElm.getChildText("dist");
            try {

                dist = Integer.parseInt(distStr);
                return dist;

            } catch (Exception ex) {
                throw new Exception("Getting distance: for "
                        + hub + " " + dest + ": cannot parse distance");
            }
        }
    }

    public Element getHubByName(String name) {
        List<Element> hubs = doc.getRootElement().getChildren("hub");
        Iterator it = hubs.iterator();
        while (it.hasNext()) {
            Element hub = (Element) it.next();
     
            if (hub.getChildText("name").equalsIgnoreCase(name)) {
                
                return hub;
            }
        }
        return null;
    }

    public Element getDestByName(Element hub, String dest) {
        List<Element> destList = hub.getChildren("to");
        Iterator it = destList.iterator();
        while (it.hasNext()) {
            Element to = (Element) it.next();

            Element destElm = to.getChild("dest");
            if (destElm.getText().equalsIgnoreCase(dest)) {
                return to;
            }

        }
        return null;
    }

    public void removeHub(Object[] hub) throws Exception {
        String name = "";
        if (hub[0] instanceof String) {
            name = (String) hub[0];
        } else {
            throw new Exception("Deleting hub: invalid hub");
        }
        System.out.println();
        Element hunElm = this.getHubByName(name);
        doc.getRootElement().removeContent(hunElm);
    }
    
    /*
     * update existing hub, name remanin unchanged
     */
    public void updateHub(
            String name, int r, double ca1, double cb1,
            double ca2, double cb2) throws IOException, Exception {
        if (name.isEmpty() || r <= 0) {
            throw new Exception("Not a valid hub info :" + name + " " + r);
        }
        Element hub = getHubByName(name);
        if (hub == null) {
            throw new Exception("Updating hub " + name + ", hub does not exist");
        }
        hub = new Element("hub");
        hub.getChild("rate").setText(String.valueOf(r));
        hub.getChild("formula").getChild("a1").setText(String.valueOf(ca1));
        hub.getChild("formula").getChild("b1").setText(String.valueOf(cb1));
        hub.getChild("formula").getChild("a2").setText(String.valueOf(ca2));
        hub.getChild("formula").getChild("b2").setText(String.valueOf(cb2));

        writeToXMLStorage();
    }

    public static int getGoogleCounter() {
        return googleCounter;
    }
    
    
}
