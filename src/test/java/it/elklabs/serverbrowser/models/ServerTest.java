package it.elklabs.serverbrowser.models;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;


public class ServerTest {

    Server server;

    @Before
    public void setUp() throws Exception {
        server = new Server("test", "description");
    }

    @After
    public void tearDown() throws Exception {
        server = null;
    }

    @Test
    public void getName() throws Exception {
        Assert.assertEquals("test", server.getName());
    }

    @Test
    public void setName() throws Exception {
        String newName = "new name";
        server.setName(newName);

        Assert.assertEquals(newName, server.getName());
    }

    @Test
    public void getDescription() throws Exception {
        Assert.assertEquals("description", server.getDescription());
    }

    @Test
    public void setDescription() throws Exception {
        String newDescription = "new description";
        server.setDescription(newDescription);

        Assert.assertEquals(newDescription, server.getDescription());
    }


}