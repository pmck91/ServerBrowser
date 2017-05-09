package it.elklabs.serverbrowser.Views;

import it.elklabs.serverbrowser.controllers.ServerController;
import it.elklabs.serverbrowser.models.Server;

import java.util.ArrayList;

/**
 * Server List view
 */
public class ServerList {

    /**
     * Shows a supplied list of servers
     *
     * @param servers    the servers model containing multiple server models
     * @param totalCount the total getCount of matching server models in the database
     * @param controller the server controller
     */
    public static void show(ArrayList<Server> servers, long totalCount, ServerController controller) {
        System.out.println("-----------------------------------------------");
        String header = "id - name - description";
        System.out.println(header);
        System.out.println("-----------------------------------------------");
        for (Server server : servers) {
            System.out.println(String.format("%s - %s - %s", server.getId(), server.getName(), server.getDescription()));
        }
        System.out.println("-----------------------------------------------");

        int page = controller.currentPage();
        long limit = controller.getLimit();
        long pageTotal = (page * limit) + limit > totalCount ? totalCount : (page * limit) + limit;

        System.out.println(String.format("Viewing %s to %s of %s servers", (page * limit), pageTotal, totalCount));
        System.out.println("-----------------------------------------------");

    }

}
