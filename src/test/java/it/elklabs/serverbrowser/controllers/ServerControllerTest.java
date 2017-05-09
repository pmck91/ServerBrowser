package it.elklabs.serverbrowser.controllers;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import it.elklabs.serverbrowser.models.Server;
import mockit.Injectable;
import mockit.Tested;
import mockit.Verifications;
import org.junit.Test;

import java.sql.SQLException;

/**
 * Created by petermckinney on 08/05/2017.
 */

public class ServerControllerTest {

    @Injectable
    ConnectionSource connectionSource;

    @Tested
    ServerController serverController;

    @Injectable
    private Dao<Server, String> serveDAO;

    public void setUp() {
        serverController = new ServerController(connectionSource);
    }




}
