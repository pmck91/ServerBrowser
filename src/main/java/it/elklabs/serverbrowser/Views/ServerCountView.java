package it.elklabs.serverbrowser.Views;

import it.elklabs.serverbrowser.controllers.ServerController;

/**
 * Server getCount view
 */
public class ServerCountView {

    /**
     * The view method for displaying the getCount
     * @param serverController the Server controller
     */
    public static void show(ServerController serverController){
        System.out.println("-----------------------------------------------");
        System.out.println(String.format("There are %s servers in the repo", serverController.getCount()));
        System.out.println("-----------------------------------------------");
    }

}
