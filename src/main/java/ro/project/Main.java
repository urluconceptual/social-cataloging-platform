package ro.project;

import ro.project.application.GeneralMenu;
import ro.project.application.PopulateScript;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        GeneralMenu.getInstance();

        PopulateScript.getInstance();

        //PopulateScript.populate();

        do {
            GeneralMenu.start();
        } while (!"exit".equals(scanner.next()));
    }
}