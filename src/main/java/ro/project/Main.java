package ro.project;

import ro.project.application.Menu;
import ro.project.service.UserService;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        Menu.getInstance();

        Menu.populate();

        do {
            Menu.start();
        } while (!"exit".equals(scanner.next()));
    }
}