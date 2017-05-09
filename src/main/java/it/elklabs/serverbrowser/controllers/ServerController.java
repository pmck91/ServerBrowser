package it.elklabs.serverbrowser.controllers;


import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.support.ConnectionSource;
import it.elklabs.serverbrowser.Order;
import it.elklabs.serverbrowser.Views.ServerCountView;
import it.elklabs.serverbrowser.Views.ServerInput;
import it.elklabs.serverbrowser.Views.ServerList;
import it.elklabs.serverbrowser.Views.ServerShow;
import it.elklabs.serverbrowser.models.Server;
import it.elklabs.serverbrowser.models.Servers;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * This class manages the business logic for the Server Model object. It give the ability to
 * located a server, edit a server, remove a sever, and page though a list of results. It
 * also allows for filtering by ascending and descending on cols.
 */
public class ServerController {

    private final long limit = 10;

    private Dao<Server, String> serveDAO;
    private int page;
    private Order orderBy;
    private String orderByCol;
    private long totalCount;

    /**
     * @param connectionSource a connection source object for the desired DB
     */
    public ServerController(ConnectionSource connectionSource) {
        this.page = 0;
        this.orderBy = Order.ASSENDING;
        this.orderByCol = Server.ID_FIELD;
        try {
            this.serveDAO = DaoManager.createDao(connectionSource, Server.class);

            this.totalCount = this.getCount();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Handles the current page being incremented if more results are available to be paged
     *
     * @return the new page number
     */
    public int nextPage() {
        long count = this.totalCount;
        if ((page + 1) * limit <= count) {
            this.page++;
        }
        return this.page;
    }

    /**
     * Handles the current page being decremented if current page is greater than the first page
     *
     * @return the new page number
     */
    public int previousPage() {
        if (this.page - 1 >= 0) {
            this.page--;
        }
        return this.page;
    }

    /**
     * gets the total number of servers in the database
     *
     * @return the getCount
     */
    public long getCount() {
        try {
            QueryBuilder<Server, String> statementBuilder = serveDAO.queryBuilder();
            statementBuilder.setCountOf(true);
            PreparedQuery<Server> query = statementBuilder.prepare();
            return serveDAO.countOf(query);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return 0L;
        }
    }

    /**
     * The count end point to display count
     */
    public void count() {
        ServerCountView.show(this);
    }

    /**
     * Sets the filtering options for page method to use.
     *
     * @param column The column we want to filter on
     * @param order  The order (ascending, descending)
     */
    public void setFilter(String column, Order order) {
        this.orderByCol = column;
        this.orderBy = order;
    }

    /**
     * Adds a server to the database
     */
    public void saveServer() {
        String[] inputs = new ServerInput().getInput();
        Server server = new Server(inputs[0], inputs[1]);
        try {
            serveDAO.createIfNotExists(server);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Saves all servers in a servers object list
     *
     * @param servers a model containing multiple servers
     */
    public void saveServers(Servers servers) {
        for (Server server : servers.getServerList()) {
            try {
                serveDAO.create(server);
            } catch (SQLException e) {
                System.err.println(e.getMessage());
            }
        }

    }

    /**
     * find a server by id
     *
     * @param id the servers DB id
     * @return the found server or null
     */
    public Server findServer(int id) {
        try {
            QueryBuilder<Server, String> statementBuilder = serveDAO.queryBuilder();
            PreparedQuery<Server> query = statementBuilder.where().idEq(Integer.toString(id)).prepare();
            return serveDAO.queryForFirst(query);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return null;
        }
    }

    /**
     * Gets a paged list of servers (search or list), we need the limit and the page number
     *
     * @param page the requested page
     * @param col  the col to search on (can be null)
     * @param like the search term (can be null)
     */
    public void page(int page, String col, String like) {
        long offset = (page * this.limit);
        this.page = page;
        ArrayList<Server> servers = new ArrayList<>();
        try {
            QueryBuilder<Server, String> statementBuilder = serveDAO.queryBuilder();

            PreparedQuery<Server> query;
            if (like != null && col != null) {
                query = statementBuilder.limit(this.limit).offset(offset).orderBy(this.orderByCol, this.orderBy.getValue()).where().like(col, "%" + like + "%").prepare();
                statementBuilder.setCountOf(true);
                this.totalCount = serveDAO.queryBuilder().where().like(col, "%" + like + "%").countOf();
            } else {
                query = statementBuilder.limit(this.limit).offset(offset).orderBy(this.orderByCol, this.orderBy.getValue()).prepare();
                statementBuilder.setCountOf(true);
                this.totalCount = this.getCount();
            }
            servers = (ArrayList<Server>) serveDAO.query(query);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        ServerList.show(servers, this.totalCount, this);
    }

    /**
     * view a single server by id
     *
     * @param id the server to views id
     */
    public void view(int id) {
        Server toView = this.findServer(id);
        ServerShow.show(toView, this);
    }

    /**
     * edits a server
     *
     * @param id the server to edits id
     */
    public void edit(int id) {
        try {
            String[] newValues = new ServerInput().getInput();
            Server toEdit = this.findServer(id);
            toEdit.setName(newValues[0]).setDescription(newValues[1]);
            serveDAO.update(toEdit);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Deletes a server based on id
     *
     * @param id the server to delete
     */
    public void delete(int id) {
        try {
            Server toRemove = this.findServer(id);
            serveDAO.delete(toRemove);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * returns the current page
     *
     * @return the current page
     */
    public int currentPage() {
        return this.page;
    }

    /**
     * Gets the limit
     *
     * @return the limit
     */
    public long getLimit() {
        return this.limit;
    }

    public String getOrderByCol(){
        return this.orderByCol;
    }

    public Order getOrderBy(){
        return this.orderBy;
    }

}
