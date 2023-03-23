package seedu.meal360;

import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

public class Database {

    private static final Ui ui = new Ui();
    private static final String recipesDatabaseFilepath = "./src/main/resources/recipesDatabase.json";

    public RecipeList loadDatabase() throws IOException {
        RecipeList database;
        Gson gson = new Gson();

        // Check if the JSON file exists
        File file = new File(recipesDatabaseFilepath);
        if (!file.exists()) {
            try {
                // Create the parent directory if it does not exist
                File parentDir = file.getParentFile();
                if (!parentDir.exists()) {
                    ui.printMessage("Database directory does not exist, creating one now...");
                    parentDir.mkdirs();
                }
                ui.printMessage("Database file does not exist, creating one now...");
                file.createNewFile();
            } catch (IOException e) {
                throw new IOException("Error creating database file.");
            }

            // Return a default recipe list if database file does not exist
            return defaultRecipeList();
        }

        // Read the JSON data from the file if it already exists
        try {
            // Read the JSON data from the file
            BufferedReader reader = new BufferedReader(new FileReader(recipesDatabaseFilepath));
            database = gson.fromJson(reader, RecipeList.class);
            reader.close();
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException("Unable to find database file.");
        } catch (IOException e) {
            throw new IOException("Error reading from database file.");
        }

        // Ensure non empty recipeList is returned
        if (database == null) {
            ui.printMessage("Database file is empty, loading default database");
            return defaultRecipeList();
        } else {
            return database;
        }
    }

    public void saveDatabase(RecipeList recipeList) throws IOException {
        Gson gson = new Gson();
        // Write the data object to the JSON file
        FileWriter writer;
        writer = new FileWriter(recipesDatabaseFilepath);
        gson.toJson(recipeList, writer);
        writer.close();
    }

    public RecipeList defaultRecipeList() {
        RecipeList defaultRecipeList = new RecipeList();
        defaultRecipeList.add(new Recipe("Chicken Rice", new HashMap<String, Integer>() {
            {
                put("Chicken", 1);
                put("Rice", 1);
                put("Salt", 1);
                put("Pepper", 1);
            }
        }));

        defaultRecipeList.add(new Recipe("Tomato fritters", new HashMap<String, Integer>() {
            {
                put("Ripe mixed-colour cherry tomatoes ", 250);
                put("Flat-leaf parsley", 15);
                put("Garlic cloves", 2);
                put("Plain flour", 2);
                put("Eggs", 2);
                put("Breadcrumbs", 100);
                put("Vegetable oil", 2);
                put("Feta cheese", 30);
            }
        }));

        defaultRecipeList.add(new Recipe("Chicken and mushroom pie", new HashMap<String, Integer>() {
            {
                put("Chicken breast fillets", 2);
                put("Mushrooms", 250);
                put("Onion", 1);
                put("Plain flour", 2);
                put("Butter", 2);
                put("Milk", 2);
                put("Puff pastry", 1);
            }
        }));

        defaultRecipeList.add(new Recipe("Seafood paella", new HashMap<String, Integer>() {
            {
                put("Prawns", 500);
                put("Mussels", 500);
                put("Chorizo", 100);
                put("Onion", 1);
                put("Garlic cloves", 2);
                put("Rice", 1);
                put("Cherry tomatoes", 250);
                put("Vegetable oil", 2);
                put("Chicken stock", 1);
                put("Saffron", 1);
            }
        }));

        defaultRecipeList.add(new Recipe("Avocado toast", new HashMap<String, Integer>() {
            {
                put("Avocado", 1);
                put("Bread", 2);
                put("Lemon", 1);
                put("Salt", 1);
                put("Pepper", 1);
            }
        }));

        defaultRecipeList.add(new Recipe("Italian sausage orzo soup", new HashMap<String, Integer>() {
            {
                put("Italian sausage", 1);
                put("Onion", 1);
                put("Garlic cloves", 2);
                put("Carrot", 1);
                put("Celery", 1);
                put("Dried oregano", 1);
                put("Dried basil", 1);
                put("Dried thyme", 1);
                put("Dried parsley", 1);
                put("Dried rosemary", 1);
                put("Chicken stock", 1);
                put("Orzo", 1);
                put("Parmesan cheese", 1);
            }
        }));

        defaultRecipeList.add(new Recipe("Chicken and mushroom pasta", new HashMap<String, Integer>() {
            {
                put("Chicken breast fillets", 2);
                put("Mushrooms", 250);
                put("Onion", 1);
                put("Garlic cloves", 2);
                put("Plain flour", 2);
                put("Butter", 2);
                put("Milk", 2);
                put("Pasta", 1);
            }
        }));

        defaultRecipeList.add(new Recipe("Creamy Hummus Pasta", new HashMap<String, Integer>() {
            {
                put("Pasta", 1);
                put("Hummus", 1);
                put("Garlic cloves", 2);
                put("Lemon", 1);
                put("Salt", 1);
                put("Pepper", 1);
            }
        }));

        defaultRecipeList.add(new Recipe("Ground beef and potato casserole", new HashMap<String, Integer>() {
            {
                put("Ground beef", 1);
                put("Potato", 1);
                put("Onion", 1);
                put("Garlic cloves", 2);
                put("Tomato paste", 1);
                put("Tomato sauce", 1);
                put("Beef stock", 1);
                put("Cheddar cheese", 1);
            }
        }));

        defaultRecipeList.add(new Recipe("Buffalo Chicken Mac 'n' Cheese", new HashMap<String, Integer>() {
            {
                put("Chicken breast fillets", 2);
                put("Macaroni", 1);
                put("Butter", 2);
                put("Flour", 2);
                put("Milk", 2);
                put("Cheddar cheese", 1);
                put("Blue cheese", 1);
                put("Hot sauce", 1);
            }
        }));

        return defaultRecipeList;
    }
}

