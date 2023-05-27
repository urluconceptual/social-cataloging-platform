package ro.project;

import ro.project.application.GeneralMenu;
import ro.project.application.PopulateScript;
import ro.project.application.TemplateMenu;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        GeneralMenu.getInstance();

        do {
            TemplateMenu menu = GeneralMenu.getInstance();
            menu.menu();
        } while (!"exit".equals(scanner.next()));
    }
}