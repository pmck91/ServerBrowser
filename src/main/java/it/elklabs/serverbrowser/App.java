package it.elklabs.serverbrowser;

import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import it.elklabs.serverbrowser.controllers.ApplicationController;
import it.elklabs.serverbrowser.models.Server;

public class App {
    public static void main(String[] args) throws Exception {

        // this uses h2 by default but change to match your database
        String databaseUrl = "jdbc:sqlite:servers.db";
        // create a connection source to our database
        ConnectionSource connectionSource =
                new JdbcConnectionSource(databaseUrl);

        TableUtils.createTableIfNotExists(connectionSource, Server.class);

        ApplicationController appController = new ApplicationController(connectionSource);

        appController.run();
    }
}
