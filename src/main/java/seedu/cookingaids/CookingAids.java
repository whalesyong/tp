package seedu.cookingaids;

import seedu.cookingaids.ui.Ui;


public class CookingAids {
    public static void main(String[] args) {

        Ui.showWelcomeMessage();
        System.out.println("CookingAIDS v1.0. Type \"help\" to see available commands");
        Ui.waitForCommand();
    }
}

//TODO: add underscoring for names of recipes
