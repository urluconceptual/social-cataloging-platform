package ro.project.application;

import java.util.Scanner;

public class Menu {
    private static Menu INSTANCE;

    private static final Scanner scanner = new Scanner(System.in);

    private Menu() {
    }

    public static Menu getInstance() {
        return (INSTANCE == null ? new Menu() : INSTANCE);
    }

    private static void register() {

    }

    private static void login() {
        System.out.println("""
                                   -----
                                   """);
    }

    private static void intro() {
        System.out.println("""
                                       Choose option:
                                       1 -> Register
                                       2 -> Login
                                       """);
        int option = scanner.nextInt();
        switch (option) {
            case 1 -> register();
            case 2 -> login();
            default ->  {
                System.out.println("""
                                           Invalid option! Try again.
                                           """);
                intro();
            }
        }
    }

    public static void start() {
        System.out.println("""
                                   ---- SOCIAL CATALOGING PLATFORM ------------------------------
                                   Welcome! To use the platform, you have to register or to log
                                   into your account.
                                   """);
        intro();
    }
}