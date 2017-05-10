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

    private final String commandString =
                    "-----------------------------------------------\n" +
                    "add: save a new server \n" +
                    "view <id>: view server with id \n" +
                    "edit <id>: edit server with id \n" +
                    "delete <id>: delete server with id \n" +
                    "next: next page \n" +
                    "prev: previous page \n" +
                    "search <col> <value>: search for server by col \n" +
                    "list: Lists all servers \n" +
                    "count: displays total count of servers \n" +
                    "order <col> <asc/des>: order list by col in asc or dec order \n" +
                    "parse <file>: parses an xml files \n" +
                    "help: shows usage \n" +
                    "q: quit \n" +
                    "-----------------------------------------------";
    private ConnectionSource connectionSource;
    private ServerController serverController;

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
                    case "add":
                        serverController.saveServer();
                        printList(false, page, null, null);
                        break;
                    case "view":
                        if (input.length < 2) {
                            System.err.println("Wrong number of args");
                            break;
                        }
                        serverController.view(Integer.parseInt(input[1]));
                        printList(false, page, null, null);
                        break;
                    case "delete":
                        if (input.length < 2) {
                            System.err.println("Wrong number of args");
                            break;
                        }
                        serverController.delete(Integer.parseInt(input[1]));
                        printList(false, page, null, null);
                        break;
                    case "edit":
                        if (input.length < 2) {
                            System.err.println("Wrong number of args");
                            break;
                        }
                        serverController.edit(Integer.parseInt(input[1]));
                        printList(false, page, null, null);
                        break;
                    case "next":
                        page = serverController.nextPage();
                        printList(inSearch, page, seachCol, searchLike);
                        break;
                    case "prev":
                        page = serverController.previousPage();
                        printList(inSearch, page, seachCol, searchLike);
                        break;
                    case "order":
                        if (input.length < 3) {
                            System.err.println("Wrong number of args");
                            break;
                        }
                        String col = input[1];
                        Order order = input[2].equals("asc") ? Order.ASSENDING : Order.DECENDING;
                        serverController.setOrder(col, order);
                        printList(inSearch, page, seachCol, searchLike);
                        break;
                    case "search":
                        if (input.length < 3) {
                            System.err.println("Wrong number of args");
                            break;
                        }
                        seachCol = input[1];
                        searchLike = input[2];
                        page = 0;
                        inSearch = true;
                        printList(inSearch, page, seachCol, searchLike);
                        break;
                    case "list":
                        inSearch = false;
                        page = 0;
                        printList(inSearch, page, null, null);
                        break;
                    case "count":
                        serverController.count();
                        break;
                    case "parse":
                        if (input.length < 2) {
                            System.err.println("Wrong number of args");
                            break;
                        }
                        String filePath = input[1];
                        Servers servers = XMLController.parse(filePath);
                        serverController.saveServers(servers);
                        page = 0;
                        printList(false, page, null, null);
                        break;
                    case "help":
                    case "h":
                    case "?":
                        System.out.println(commandString);
                        break;
                    case "q":
                    case "quit":
                        cont = false;
                        close();
                        break;
                    default:
                        System.out.println("Unrecognised command, see usage with help, h or, ?");
                        break;
                }

            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }

    }

    /**
     * Closes the database connection
     */
    private void close() {
        try {
            this.connectionSource.close();
        } catch (IOException e) {
            System.err.println("Something has gone wrong, couldn't close connection to database: " + e.getMessage());
        }
    }

    /**
     * prints the server list for search or normal
     *
     * @param inSearchMode is the program currently paging search results?
     * @param page         current page
     * @param col          search col (can be null)
     * @param like         search term (can be null)
     */
    private void printList(boolean inSearchMode, int page, String col, String like) {
        if (inSearchMode) {
            serverController.page(page, col, like);
        } else {
            serverController.page(page, null, null);
        }
    }
}
