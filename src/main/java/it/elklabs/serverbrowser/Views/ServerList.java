package it.elklabs.serverbrowser.Views;

import it.elklabs.serverbrowser.controllers.ServerController;
import it.elklabs.serverbrowser.models.Server;

import java.util.ArrayList;

public class ServerList {

    public static void show(ArrayList<Server> servers, int page, long limit, long totalCount, ServerController controller) {
        System.out.println("-----------------------------------------------");
        String header = "id - name - description";
        System.out.println(header);
        System.out.println("-----------------------------------------------");
        for (Server server : servers) {
            System.out.println(String.format("%s - %s - %s", server.getId(), server.getName(), server.getDescription()));
        }
        System.out.println("-----------------------------------------------");

        long pageTotal = (page * limit) + limit > totalCount ? totalCount : (page * limit) + limit;

        System.out.println(String.format("Viewing %s to %s of %s servers", (page * limit), pageTotal, totalCount));
        System.out.println("-----------------------------------------------");

    }

}
