package it.elklabs.serverbrowser.Views;

import it.elklabs.serverbrowser.controllers.ServerController;
import it.elklabs.serverbrowser.models.Server;

import java.util.Scanner;

public class ServerShow {

    public static void show(Server server, ServerController controller) {

        String commandString = "edit: Edit the server, delete: Delete the server, return: Return to list";
        System.out.println("-----------------------------------------------");
        System.out.println(String.format("Displaying Server (id: %s)", server.getId()));
        System.out.println(String.format("Server Name: %s", server.getName()));
        System.out.println(String.format("Server Desc: %s", server.getDescription()));
        System.out.println("-----------------------------------------------");
        System.out.println(commandString);

        Boolean cont = true;
        while (cont) {
            Scanner scanner = new Scanner(System.in);
            String command = scanner.nextLine().toLowerCase();

            switch (command) {
                case "edit":
                    controller.edit(server.getId());
                    cont = false;
                    break;
                case "delete":
                    controller.delete(server.getId());
                    cont = false;
                    break;
                case "return":
                    cont = false;
                    break;
                default:
                    System.out.println("Unrecognised command, try again");
                    System.out.println(commandString);
            }
        }
    }
}
