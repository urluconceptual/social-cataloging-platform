package ro.project;

import ro.project.application.GeneralMenu;
import ro.project.application.PopulateScript;
import ro.project.application.TemplateMenu;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        GeneralMenu.getInstance();

        PopulateScript.populate();

        do {
            TemplateMenu menu = GeneralMenu.getInstance();
            menu.menu();
            System.out.println("Write exit to exit the program, or anything else to restart it.");
        } while (!"exit".equals(scanner.next()));
    }
}