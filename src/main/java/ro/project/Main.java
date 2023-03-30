package ro.project;

import ro.project.application.Menu;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        Menu.getInstance();

        do {
            Menu.start();
        } while (!"exit".equals(scanner.next()));
    }
}