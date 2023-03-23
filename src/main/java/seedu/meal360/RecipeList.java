package seedu.meal360;

import java.util.ArrayList;
import java.util.HashMap;

public class RecipeList extends ArrayList<Recipe> {
    private HashMap<String, ArrayList<Recipe>> tags = new HashMap<String, ArrayList<Recipe>>();

    public Recipe findByName(String name) {
        for (Recipe recipe : this) {
            if (recipe.getName().equalsIgnoreCase(name)) {
                return recipe;
            }
        }
        return null;
    }

    public void addRecipe(Recipe recipe) {
        super.add(recipe);
    }

    public void editRecipe(Recipe recipe, HashMap<String, Integer> ingredients) {
        recipe.ingredients = ingredients;
    }

    public Recipe deleteRecipe(int recipeNum) {
        Recipe recipeToDelete = super.get(recipeNum);
        super.remove(recipeToDelete);
        return recipeToDelete;
    }

    public void addRecipeToTag(String tag, Recipe recipe) {
        if (tags.containsKey(tag)) {
            tags.get(tag).add(recipe);
        } else {
            assert !tags.containsKey(tags);
            ArrayList<Recipe> tagRecipes = new ArrayList<Recipe>();
            tagRecipes.add(recipe);
            tags.put(tag, tagRecipes);
            assert tags.size() > 0 : "tag's size is still 0.";
        }
    }

    public void removeRecipeFromTag(String tag, Recipe recipe) {
        if (!tags.containsKey(tag)) {
            throw new IndexOutOfBoundsException("Invalid tag");
        } else {
            ArrayList<Recipe> tagRecipeList = tags.get(tag);
            for (int i = 0; i < tagRecipeList.size(); i++) {
                Recipe recipeInTag = tagRecipeList.get(i);
                if (recipe.getName().equals(recipeInTag.getName())) {
                    tagRecipeList.remove(i);
                }
            }
        }
    }

    public RecipeList listRecipes(String[] filters, boolean isTag) {
        RecipeList filteredRecipeList = new RecipeList();
        if (filters == null) {
            return this;
        }
        if (isTag) {
            for (String filter : filters) {
                filter = filter.trim();
                ArrayList<Recipe> tagRecipes = this.tags.get(filter);
                if (tagRecipes != null) {
                    filteredRecipeList.addAll(this.tags.get(filter));
                }
            }
            return filteredRecipeList;
        }
        for (Recipe recipe : this) {
            filteredRecipeList.add(recipe);
            for (String filter : filters) {
                filter = filter.trim();
                if (!recipe.getName().contains(filter) && !recipe.getIngredients().containsKey(filter)) {
                    filteredRecipeList.remove(recipe);
                }
            }
        }
        return filteredRecipeList;
    }
}
