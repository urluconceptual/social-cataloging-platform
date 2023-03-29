package ro.project.application;

public class Menu {
    private static Menu INSTANCE;

    private Menu() {}

    public static Menu getInstance() {
        return (INSTANCE == null ? new Menu() : INSTANCE);
    }

}