package com.utils.file.xml;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBIntrospector;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.stream.XMLInputFactory;
import java.io.*;

import static javax.xml.stream.XMLInputFactory.IS_SUPPORTING_EXTERNAL_ENTITIES;
import static javax.xml.stream.XMLInputFactory.SUPPORT_DTD;

/**
 * Created by oksdud on 09.12.2016.
 */
public class XMLUtility {
    private static final XMLInputFactory inputFactory = XMLInputFactory.newInstance();

    static {
        inputFactory.setProperty(SUPPORT_DTD, false);
        inputFactory.setProperty(IS_SUPPORTING_EXTERNAL_ENTITIES, false);
    }
    public static void toXML(String nameXMLFile, Class<?> clazz, Object objData) {
        File file = new File(nameXMLFile);
        try {
        if(!file.exists()) file.createNewFile();
        try(final Reader sr = new FileReader(file)) {

            JAXBContext jaxbContext = JAXBContext.newInstance(clazz);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            jaxbMarshaller.marshal(objData, file);
        } catch (Exception e) {
            e.printStackTrace();
        }

        }catch (IOException ex){
            ex.printStackTrace();
        }

    }

    public static Object fromXMLString(String xmlString, Class<?> clazz) {
        try (final Reader sr = new StringReader(xmlString)) {

            JAXBContext jaxbContext = JAXBContext.newInstance(clazz);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            return JAXBIntrospector.getValue(jaxbUnmarshaller.unmarshal(inputFactory.createXMLStreamReader(sr),clazz));

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public static Object fromXMLFile(String nameXMLFile, Class<?> clazz) {
        File file = new File(nameXMLFile);
        try(final Reader sr = new FileReader(file)) {

            JAXBContext jaxbContext = JAXBContext.newInstance(clazz);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            return JAXBIntrospector.getValue(jaxbUnmarshaller.unmarshal(inputFactory.createXMLStreamReader(sr),clazz));

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public static String getFieldFromXml(String xmlString){
        String value= "";
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {

            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(new InputSource( new StringReader( xmlString ) ));
            Element root = doc.getDocumentElement();
            NodeList nodel = root.getChildNodes();
            for (int a = 0; a < nodel.getLength(); a++) {
                String data = "";
                Node node = nodel.item(a);
                if(node instanceof Element) {
                    data = ((Element)node).getAttribute("name");
                    if(data.equals("date")){
                        value= ((Element)node).getAttribute("value");
                    }
                    System.out.println(data);
                }
            }

        }catch(Exception ex){
//            log.error("Exception ", ex );
        }

        return value;
    }

}
