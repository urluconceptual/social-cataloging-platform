package ro.project.application;

public abstract class TemplateMenu {
    public final void menu() {
        welcomeMessage();
        showOptions();
        getOption();
        redirect();
    }

    protected abstract void welcomeMessage();
    protected abstract void showOptions();
    protected abstract void getOption();
    protected abstract void redirect();
}
