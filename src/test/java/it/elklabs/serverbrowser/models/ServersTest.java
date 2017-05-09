package it.elklabs.serverbrowser.models;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Created by petermckinney on 09/05/2017.
 */
public class ServersTest {
    Server server1;
    Server server2;
    Server server3;
    Servers servers;

    @Before
    public void setUp() throws Exception {
        server1 = new Server("test1", "description1");
        server2 = new Server("test2", "description2");
        server3 = new Server("test3", "description3");

        ArrayList<Server> serverList = new ArrayList<>();
        serverList.add(server1);
        serverList.add(server2);
        serverList.add(server3);

        servers = new Servers(serverList);
    }

    @After
    public void tearDown() throws Exception {
        servers = null;
    }

    @Test
    public void getServerList() throws Exception {
        int size = servers.getServerList().size();
        Assert.assertEquals(3, size);
    }

    @Test
    public void setServerList() throws Exception {
        server1 = new Server("new test1", "new description1");
        server2 = new Server("new test2", "new description2");

        ArrayList<Server> serverList = new ArrayList<>();
        serverList.add(server1);
        serverList.add(server2);

        servers.setServerList(serverList);

        int size = servers.getServerList().size();
        Assert.assertEquals(2, size);
    }

    @Test
    public void addServer() throws Exception {
        Server server4 = new Server("new test4", "new description4");
        servers.addServer(server4);

        int size = servers.getServerList().size();
        Assert.assertEquals(4, size);
    }

    @Test
    public void count() throws Exception {
        int size = servers.count();
        Assert.assertEquals(3, size);
    }

}