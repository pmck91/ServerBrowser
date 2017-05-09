package it.elklabs.serverbrowser.Views;

import java.util.Scanner;

/**
 * Server input view
 */
public class ServerInput {

    /**
     * The view for inputting server information
     *
     * @return a string array of the name and description
     */
    public String[] getInput() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("-----------------------------------------------");
        System.out.print("Enter Server Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Server Description: ");
        String description = scanner.nextLine();
        System.out.println("-----------------------------------------------");
        return new String[]{name, description};
    }
}
