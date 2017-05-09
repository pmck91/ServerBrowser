package it.elklabs.serverbrowser.models;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;


/**
 * This model handles a list of server models
 * It is needed for the XML parser to understand multiple servers
 */
@XmlRootElement(name = "servers")
@XmlAccessorType(XmlAccessType.FIELD)
public class Servers {

    @XmlElement(name = "server")
    private ArrayList<Server> serverList;

    public Servers() {
    }

    /**
     * Get the servers stored in an ArrayList of servers
     *
     * @return the list of servers
     */
    public ArrayList<Server> getServerList() {
        return this.serverList;
    }

    /**
     * Sets the list of servers
     *
     * @param serverList a list of servers
     */
    public void setServerList(ArrayList<Server> serverList) {
        this.serverList = serverList;
    }

    /**
     * Adds a server to the server list
     *
     * @param server the server to add
     */
    public void addServer(Server server) {
        this.serverList.add(server);
    }
}
