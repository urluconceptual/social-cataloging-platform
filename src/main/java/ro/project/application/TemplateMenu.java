package ro.project.application;

import ro.project.application.csv.CsvWriter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public abstract class TemplateMenu {
    protected static String lastOption;
    protected static String info;

    private static final CsvWriter csvWriter = CsvWriter.getInstance();

    private static final Logger logger = Logger.getGlobal();

    public final void menu() {
        welcomeMessage();
        showOptions();
        getOption();
        getInfo();
        listener();
        if(!lastOption.equals("0"))
            redirect();
    }

    protected abstract void welcomeMessage();

    protected abstract void showOptions();

    protected abstract void getOption();

    protected abstract void getInfo();

    protected void listener() {
        try {
            List<String[]> dataLines = new ArrayList<>();
            dataLines.add(new String[]{info, LocalDateTime.now().toString()});
            csvWriter.executeLineByLine(dataLines);
        } catch (Exception e) {
            e.printStackTrace();
        }

        logger.info("Your option: " + lastOption + ", with info: " + info + ", at: " + LocalDateTime.now().toString() + " was logged.\n");
    }

    protected abstract void redirect();
}
