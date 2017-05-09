package it.elklabs.serverbrowser.controllers;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import it.elklabs.serverbrowser.Order;
import it.elklabs.serverbrowser.models.Server;
import it.elklabs.serverbrowser.models.Servers;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.lang.reflect.Field;
import java.sql.SQLException;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.not;


public class ServerControllerTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    String databaseUrl;
    // create a connection source to our database
    ConnectionSource connectionSource;
    ServerController serverController;
    Dao<Server, String> serveDAO;

    @Before
    public void setUp() throws SQLException {
        databaseUrl = "jdbc:sqlite:memory:";
        connectionSource = new JdbcConnectionSource(databaseUrl);
        serverController = new ServerController(connectionSource);
        serveDAO = DaoManager.createDao(connectionSource, Server.class);
        TableUtils.dropTable(connectionSource, Server.class, true);
        TableUtils.createTable(connectionSource, Server.class);

        Servers servers = XMLController.parse("servers.xml");
        serverController.saveServers(servers);

        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    @Test
    public void getCount() {
        long count = serverController.getCount();
        Assert.assertEquals(12l, count);
    }

    @Test
    public void currentPage() {
        Assert.assertEquals(0, serverController.currentPage());
    }

    @Test
    public void nextPage() throws Exception {

        Field field = serverController.getClass().getDeclaredField("totalCount");
        field.setAccessible(true);
        field.setInt(serverController, 25);

        serverController.nextPage();
        Assert.assertEquals(1, serverController.currentPage());

        serverController.nextPage();
        Assert.assertEquals(2, serverController.currentPage());

        serverController.nextPage();
        Assert.assertEquals(2, serverController.currentPage());
    }

    @Test
    public void previousPage() throws Exception {
        Field field = serverController.getClass().getDeclaredField("page");
        field.setAccessible(true);
        field.setInt(serverController, 2);

        serverController.previousPage();
        Assert.assertEquals(1, serverController.currentPage());

        serverController.previousPage();
        Assert.assertEquals(0, serverController.currentPage());

        serverController.previousPage();
        Assert.assertEquals(0, serverController.currentPage());
    }

    @Test
    public void page() {
        serverController.page(1, null, null);
        Assert.assertThat(outContent.toString(), containsString("netflix"));
        Assert.assertThat(outContent.toString(), not(containsString("pmck")));
    }

    @Test
    public void findServer() {
        Server server = serverController.findServer(1);
        Assert.assertEquals("www.pmck.info", server.getName());
    }

    @Test
    public void delete() {
        serverController.delete(1);
        Server server = serverController.findServer(1);
        Assert.assertEquals(null, server);
    }

    @Test
    public void setFilter() {
        serverController.setFilter("id", Order.DECENDING);
        Assert.assertEquals("id", serverController.getOrderByCol());
        Assert.assertEquals(Order.DECENDING, serverController.getOrderBy());
        serverController.page(0, null, null);
        Assert.assertThat(outContent.toString(), containsString("netflix"));
        Assert.assertThat(outContent.toString(), not(containsString("pmck")));
    }

    @Test
    public void search() {
        serverController.page(0, "name", "reddit");
        Assert.assertThat(outContent.toString(), containsString("reddit"));
    }

    @After
    public void closeDB() throws IOException {
        connectionSource.close();
    }

}
