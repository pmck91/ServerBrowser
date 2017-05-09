package it.elklabs.serverbrowser.controllers;

import it.elklabs.serverbrowser.models.Servers;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;


public class XMLController {

    /**
     * Static xml parsing method to parse a server xml file
     *
     * @param filePath file path of the xml file
     * @return a servers model
     */
    public static Servers parse(String filePath) {

        Servers servers = new Servers();

        JAXBContext jaxbContext;
        try {
            jaxbContext = JAXBContext.newInstance(Servers.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            servers = (Servers) jaxbUnmarshaller.unmarshal(new File(filePath));

        } catch (JAXBException e) {
            System.err.println("Failed to load xml: " + e.getMessage());
        }

        return servers;
    }
}
