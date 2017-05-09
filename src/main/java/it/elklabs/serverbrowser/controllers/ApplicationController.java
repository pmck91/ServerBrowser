package it.elklabs.serverbrowser.controllers;


import com.j256.ormlite.support.ConnectionSource;
import it.elklabs.serverbrowser.Order;
import it.elklabs.serverbrowser.models.Servers;

import java.io.IOException;
import java.util.Scanner;

/**
 * This class handles the application and database connection
 */
public class ApplicationController {

    private ConnectionSource connectionSource;
    private ServerController serverController;
    private final String commandString = "save: save a new server \n" +
            "view <id>: view server with id \n" +
            "edit <id>: edit server with id \n" +
            "delete <id>: delete server with id \n" +
            "next: next page \n" +
            "prev: previous page \n" +
            "search <col> <value>: search for server by col \n" +
            "list: Lists all servers \n" +
            "count: displays total count of servers \n" +
            "filter <col> <asc/des>: filter list by col in asc or dec order \n" +
            "parse <file>: parses an xml files \n" +
            "help: shows usage \n" +
            "q: quit";

    public ApplicationController(ConnectionSource connectionSource) {
        this.connectionSource = connectionSource;
        this.serverController = new ServerController(this.connectionSource);

    }

    /**
     * This method handles the main program loop and user input
     */
    public void run() {

        Scanner scanner = new Scanner(System.in);
        Boolean cont = true;
        serverController.page(0, null, null);
        System.out.println();
        System.out.println(commandString);
        int page = serverController.currentPage();
        boolean inSearch = false;

        String seachCol = "";
        String searchLike = "";

        while (cont) {
            System.out.print("Enter a command $>");
            try {
                String[] input = scanner.nextLine().split(" ");

                switch (input[0].toLowerCase()) {
                    case "saveServers":
                        serverController.saveServer();
                        break;
                    case "view":
                        serverController.view(Integer.parseInt(input[1]));
                        break;
                    case "delete":
                        serverController.delete(Integer.parseInt(input[1]));
                        break;
                    case "edit":
                        serverController.edit(Integer.parseInt(input[1]));
                        break;
                    case "next":
                        page = serverController.nextPage();
                        break;
                    case "prev":
                        page = serverController.previousPage();
                        break;
                    case "filter":
                        String col = input[1];
                        Order order = input[2].equals("asc") ? Order.ASSENDING : Order.DECENDING;
                        serverController.setFilter(col, order);
                        break;
                    case "search":
                        seachCol = input[1];
                        searchLike = input[2];
                        page = 0;
                        inSearch = true;
                        break;
                    case "list":
                        inSearch = false;
                        page = 0;
                        break;
                    case "count":
                        System.out.println(String.format("There are %s servers", serverController.count()));
                        break;
                    case "parse":
                        String filePath = input[1];
                        Servers servers = XMLController.parse(filePath);
                        serverController.saveServers(servers);
                        break;
                    case "help":
                    case "h":
                        System.out.println(commandString);
                        break;
                    case "q":
                    case "quit":
                        cont = false;
                        close();
                        System.exit(0);
                        break;
                }

                if (inSearch) {
                    serverController.page(page, seachCol, searchLike);
                } else {
                    serverController.page(page, null, null);
                }


            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }

    }

    /**
     * Closes the database connection
     */
    private void close(){
        try {
            this.connectionSource.close();
        } catch (IOException e) {
            System.err.println("Something has gone wrong, couldn't close connection to database: " + e.getMessage());
        }
    }
}
