package it.elklabs.serverbrowser.Views;

import java.util.Scanner;

public class ServerInput {

    public String[] getInput() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Server Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Server Description: ");
        String description = scanner.nextLine();
        return new String[]{name, description};
    }
}
