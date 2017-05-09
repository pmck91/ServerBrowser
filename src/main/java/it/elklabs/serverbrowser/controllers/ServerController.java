package it.elklabs.serverbrowser.controllers;


import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.support.ConnectionSource;
import it.elklabs.serverbrowser.Order;
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

    public final long limit = 10;

    private Dao<Server, String> serveDAO;
    private int page;
    private Order orderBy;
    private String orderByCol;
    private long totalCount;

    /**
     * @param connectionSource
     */
    public ServerController(ConnectionSource connectionSource) {
        this.page = 0;
        this.orderBy = Order.ASSENDING;
        this.orderByCol = Server.ID_FIELD;
        try {
            this.serveDAO = DaoManager.createDao(connectionSource, Server.class);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public int nextPage() {
        long count = this.totalCount;
        if ((page + 1) * limit <= count) {
            this.page++;
        }
        return this.page;
    }

    public long count() {
        try {
            QueryBuilder<Server, String> statementBuilder = serveDAO.queryBuilder();
            statementBuilder.setCountOf(true);
            PreparedQuery<Server> query = statementBuilder.prepare();
            return serveDAO.countOf(query);
        } catch (SQLException e) {
            return 0L;
        }
    }

    public int previousPage() {
        if (this.page - 1 >= 0) {
            this.page--;
        }
        return this.page;
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
    public Server saveServer() {
        String[] inputs = new ServerInput().getInput();
        Server server = new Server(inputs[0], inputs[1]);
        try {
            serveDAO.createIfNotExists(server);
            return server;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public void saveServer(Servers servers) {
        for (Server server : servers.getServerList()) {
            try {
                serveDAO.create(server);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    public Server findServer(int id) {
        try {
            QueryBuilder<Server, String> statementBuilder = serveDAO.queryBuilder();
            PreparedQuery<Server> query = statementBuilder.where().idEq(Integer.toString(id)).prepare();
            return serveDAO.queryForFirst(query);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    /**
     * Gets a paged list of servers, we need the limit and the page number
     *
     * @param page the current page of results we wish to get
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
                this.totalCount = this.count();
            }
            servers = (ArrayList<Server>) serveDAO.query(query);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        ServerList.show(servers, page, this.limit, this.totalCount, this);
    }

    public void view(int id) {
        Server toView = this.findServer(id);
        ServerShow.show(toView, this);
    }

    /**
     * edits a server (reciever)
     *
     * @param id
     */
    public void edit(int id) {
        try {
            String[] newValues = new ServerInput().getInput();
            Server toEdit = this.findServer(id);
            toEdit.setName(newValues[0]).setDescription(newValues[1]);
            serveDAO.update(toEdit);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * @param id
     */
    public void delete(int id) {
        try {
            Server toRemove = this.findServer(id);
            serveDAO.delete(toRemove);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * @return
     */
    public int currentPage() {
        return this.page;
    }

}
