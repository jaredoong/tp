package seedu.meal360;

public class Ui {

    private static final int BOXWIDTH = 100;
    private static final String SEPARATORCHAR = "-";

    public void printSeparator() {
        String separatorLine = SEPARATORCHAR.repeat(BOXWIDTH);
        System.out.println(separatorLine);
    }

    public void printWelcomeMessage() {
        String logo = " __  __          _ ____  __  __\n" + "|  \\/  |___ __ _| |__ / / / /  \\\n"
                + "| |\\/| / -_) _` | ||_ \\/ _ \\ () |\n" + "|_|  |_\\___\\__,_|_|___/\\___/\\__/\n";
        System.out.println("Welcome to Meal360, your ultimate Recipe Manager!\n" + logo);
    }

    public void printGoodbyeMessage() {
        System.out.println(formatMessage("Bye. Hope to see you again soon!"));
    }

    public String formatMessage(String message) {
        return String.format("| %-97s|", message);
    }

    public void printMessage(String message) {
        String outputMessage = formatMessage(message);
        System.out.println(outputMessage);
    }

    public void printRecipe(Recipe recipe) {
        System.out.println(formatMessage("Name of recipe: " + recipe.getName()));
        int index = 1;
        for (String ingredient : recipe.getIngredients().keySet()) {
            String outputMessage = String.format("%s(%d)", ingredient,
                    recipe.getIngredients().get(ingredient));
            System.out.println(formatMessage(index + ". " + outputMessage));
            index++;
        }
    }

    public void printWeeklyPlan(WeeklyPlan weeklyPlan) {
        if (weeklyPlan.isEmpty()) {
            printMessage("Your weekly plan is empty!");
        } else {
            printMessage("Here is your weekly plan:");
            weeklyPlan.forEach((recipe, count) -> {
                String outputMessage = String.format("%s x%d", recipe, count);
                printMessage(outputMessage);
            });
        }

    }

    public void listRecipe(RecipeList recipeListToPrint) {
        int numberOfRecipes = recipeListToPrint.size();
        int order = 0;
        if (numberOfRecipes == 0) {
            printMessage("There is nothing to list.");
            return;
        }
        printMessage("These are the recipes you have (" + numberOfRecipes + " recipes):");
        for (Recipe recipe : recipeListToPrint) {
            order = order + 1;
            printMessage(order + ". " + recipe.getName() + "   (" + recipe.getNumOfIngredients()
                    + " ingredients)");
        }
    }

    public void printHelp() {
        printMessage("These are the operations you can do. Please follow the proper input"
                + " formats while typing.");
        printMessage("1. Add Recipe: add /r {recipe name}");
        printMessage("2. View Recipe: view {index number} or view /r {recipe name}");
        printMessage("3. Edit Recipe: edit {index number} or view /r {recipe name}");
        printMessage("4. Delete Recipe: delete {index number} or view /r {recipe name}");
        printMessage("5. List All Recipes: list");
        printMessage("6. Exit: bye");
    }
}
