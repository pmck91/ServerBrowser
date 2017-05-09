package it.elklabs.serverbrowser.controllers;

import it.elklabs.serverbrowser.models.Servers;
import org.junit.Assert;
import org.junit.Test;

public class XMLControllerTest {

    @Test
    public void testParse() {
        Servers servers = XMLController.parse("servers.xml");
        Assert.assertEquals(12, servers.count());
    }
}
