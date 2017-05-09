package it.elklabs.serverbrowser.models;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;


@XmlRootElement(name = "servers")
@XmlAccessorType(XmlAccessType.FIELD)
public class Servers {

    @XmlElement(name = "server")
    private ArrayList<Server> serverList;

    public Servers() {
    }

    public ArrayList<Server> getServerList() {
        return this.serverList;
    }
}
