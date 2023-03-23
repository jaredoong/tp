package seedu.meal360;

import java.util.Scanner;

public class Meal360 {

    /**
     * Main entry-point for the java.duke.Meal360 application.
     */

    private static Boolean canExit = false;
    private static final Ui ui = new Ui();
    private static final Parser parser = new Parser();
    private static final Database database = new Database();
    private static RecipeList recipeList;
    private static final WeeklyPlan weeklyPlan = new WeeklyPlan();

    public static void startApp() {
        ui.printSeparator();
        ui.printWelcomeMessage();
        ui.printSeparator();

        // Load database
        ui.printMessage("Loading database...");
        try {
            recipeList = database.loadDatabase();
            ui.printMessage("Database loaded successfully.");
        } catch (Exception e) {
            ui.printMessage("Error loading database, loading default database instead.");
            ui.printMessage("Overwriting database with new default database...");
            recipeList = database.defaultRecipeList();
        }

        ui.printSeparator();
    }

    public static void receiveInput(String input) {
        String[] command = input.trim().split(" ");
        if (input.equalsIgnoreCase("bye")) {
            canExit = true;
        } else if (command[0].equals("delete")) {
            ui.printSeparator();
            try {
                String deletedRecipe = parser.parseDeleteRecipe(command, recipeList);
                ui.printMessage("Noted. I've removed this recipe:");
                ui.printMessage(deletedRecipe);
                ui.printMessage("Now you have " + recipeList.size() + " recipes in the list.");
            } catch (ArrayIndexOutOfBoundsException e) {
                String errorMessage = String.format(
                        "Please enter a valid recipe number or name. You did not enter a recipe number or "
                                + "name.");
                ui.printMessage(errorMessage);
            } catch (IndexOutOfBoundsException e) {
                String errorMessage = String.format(
                        "Please enter a valid recipe number or name. You entered %s, "
                                + "which is in invalid.", command[1]);
                ui.printMessage(errorMessage);
            }
            ui.printSeparator();
        } else if (command[0].equals("view")) {
            ui.printSeparator();
            try {
                Recipe recipe = parser.parseViewRecipe(command, recipeList);
                ui.printRecipe(recipe);
            } catch (NumberFormatException e) {
                String errorMessage = String.format(
                        "Please enter a valid recipe number. You entered %s, " + "which is not a number.",
                        command[1]);
                ui.printMessage(errorMessage);
            } catch (ArrayIndexOutOfBoundsException e) {
                String errorMessage = String.format(
                        "Please enter a valid recipe number. You did not enter a recipe number.");
                ui.printMessage(errorMessage);
            } catch (IndexOutOfBoundsException e) {
                String errorMessage = String.format(
                        "Please enter a valid recipe number. You entered %s, " + "which is out of bounds.",
                        command[1]);
                ui.printMessage(errorMessage);
            }
            ui.printSeparator();
        } else if (command[0].equals("list")) {
            try {
                ui.printSeparator();
                RecipeList recipeListToPrint = parser.parseListRecipe(command, recipeList);
                ui.listRecipe(recipeListToPrint);
            } catch (IllegalArgumentException e) {
                String errorMessage = "Please enter the command in the valid format. Some arguments might be missing.";
                ui.printMessage(errorMessage);
            }
            ui.printSeparator();
        } else if (command[0].equals("add")) {
            ui.printSeparator();
            try {
                Recipe newRecipe = parser.parseAddRecipe(command, recipeList);
                ui.printMessage("I've added this new recipe:" + newRecipe.getName());
                ui.printMessage("Now you have " + recipeList.size() + " recipes in the list.");
            } catch (ArrayIndexOutOfBoundsException e) {
                String errorMessage = String.format("Please enter a valid recipe name.");
                ui.printMessage(errorMessage);
            }
            ui.printSeparator();
        } else if (command[0].equals("edit")) {
            ui.printSeparator();
            try {
                Recipe recipeToEdit = parser.parseEditRecipe(command, recipeList);
                ui.printSeparator();
                ui.printMessage("I've edited this recipe:" + recipeToEdit.getName());
            } catch (NumberFormatException e) {
                String errorMessage = String.format(
                        "Please enter a valid recipe number. You entered %s, " + "which is not a number.",
                        command[1]);
                ui.printMessage(errorMessage);
            } catch (ArrayIndexOutOfBoundsException e) {
                String errorMessage = String.format("Please enter a valid recipe name.");
                ui.printMessage(errorMessage);
            } catch (IndexOutOfBoundsException e) {
                String errorMessage = String.format(
                        "Please enter a valid recipe number. You entered %s, " + "which is out of bounds.",
                        command[1]);
                ui.printMessage(errorMessage);
            }
            ui.printSeparator();
        } else if (command[0].equals("tag")) {
            try {
                ui.printSeparator();
                String tag = parser.parseTagRecipe(command, recipeList);
                ui.printMessage("You have modified the recipe(s) in this \"" + tag + "\" tag.");
            } catch (IllegalArgumentException e) {
                String errorMessage = "Please enter the command in the correct format.";
                ui.printMessage(errorMessage);
            } catch (IndexOutOfBoundsException e) {
                String errorMessage = "Sorry, I am unable to find the recipes you entered.";
                ui.printMessage(errorMessage);
            }
            ui.printSeparator();
        } else if (command[0].equals("weekly")) {
            try {
                ui.printSeparator();
                WeeklyPlan recipeMap = parser.parseWeeklyPlan(command, recipeList);

                if (command[1].equals("/add")) {
                    ui.printMessage("I've added the recipe to your weekly plan!");
                    weeklyPlan.addPlan(recipeMap);
                } else if (command[1].equals("/delete")) {
                    ui.printMessage("I've deleted the recipe from your weekly plan!");
                    weeklyPlan.deletePlan(recipeMap);
                }
            } catch (NumberFormatException e) {
                String errorMessage = String.format("Please enter a valid number as the last argument.");
                ui.printMessage(errorMessage);
            } catch (IllegalArgumentException e) {
                ui.printMessage(e.getMessage());
            } catch (ArrayIndexOutOfBoundsException e) {
                String errorMessage = String.format("Insufficient number of arguments provided.");
                ui.printMessage(errorMessage);
            }
            ui.printSeparator();
        } else if (command[0].equals("weeklyplan")) {
            ui.printSeparator();
            ui.printWeeklyPlan(weeklyPlan);
            ui.printSeparator();
        } else if (command[0].equals("help")) {
            ui.printSeparator();
            ui.printHelp();
            ui.printSeparator();
        } else {
            ui.printSeparator();
            ui.printMessage("I'm sorry, but I don't know what that means :-(");
            ui.printSeparator();
        }
    }

    public static void exitApp() {
        ui.printSeparator();

        // Save database
        ui.printMessage("Saving database...");
        try {
            database.saveDatabase(recipeList);
            ui.printMessage("Database saved successfully.");
        } catch (Exception e) {
            ui.printMessage("Error saving database.");
        }

        ui.printGoodbyeMessage();
        ui.printSeparator();
    }

    public static void main(String[] args) {
        startApp();

        String line;
        Scanner userInput = new Scanner(System.in);

        do {
            line = userInput.nextLine();
            receiveInput(line);
        } while (!canExit);

        exitApp();
    }
}
