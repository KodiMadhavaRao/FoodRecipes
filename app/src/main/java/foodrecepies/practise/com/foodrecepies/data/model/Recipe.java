package foodrecepies.practise.com.foodrecepies.data.model;

import com.google.gson.Gson;

/**
 * Created by madhav on 2/28/2018.
 */

public class Recipe {
    private String recipeName;
    private String recipeDescription;
    private int servings;
    private int preperationTime;
    private int cookTime;
    private String difficultyLevel;
    private float rating;
    private String instruction;
    private String ingredients;
    private String nutrition;
    private int id;

    public Recipe(String recipeName, String recipeDescription, int servings, int preperationTime, int cookTime, String difficultyLevel, float rating, String instruction, String ingredients, String nutrition) {
        this.recipeName = recipeName;
        this.recipeDescription = recipeDescription;
        this.servings = servings;
        this.preperationTime = preperationTime;
        this.cookTime = cookTime;
        this.difficultyLevel = difficultyLevel;
        this.rating = rating;
        this.instruction = instruction;
        this.ingredients = ingredients;
        this.nutrition = nutrition;
    }

    public Recipe() {

    }

    public String getRecipeName() {
        return recipeName;
    }

    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
    }

    public String getRecipeDescription() {
        return recipeDescription;
    }

    public void setRecipeDescription(String recipeDescription) {
        this.recipeDescription = recipeDescription;
    }

    public int getServings() {
        return servings;
    }

    public void setServings(int servings) {
        this.servings = servings;
    }

    public int getPreperationTime() {
        return preperationTime;
    }

    public void setPreperationTime(int preperationTime) {
        this.preperationTime = preperationTime;
    }

    public int getCookTime() {
        return cookTime;
    }

    public void setCookTime(int cookTime) {
        this.cookTime = cookTime;
    }

    public String getDifficultyLevel() {
        return difficultyLevel;
    }

    public void setDifficultyLevel(String difficultyLevel) {
        this.difficultyLevel = difficultyLevel;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getInstruction() {
        return instruction;
    }

    public void setInstruction(String instruction) {
        this.instruction = instruction;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public String getNutrition() {
        return nutrition;
    }

    public void setNutrition(String nutrition) {
        this.nutrition = nutrition;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Recipe{" +
                "recipeName='" + recipeName + '\'' +
                ", recipeDescription='" + recipeDescription + '\'' +
                ", servings=" + servings +
                ", preperationTime=" + preperationTime +
                ", cookTime=" + cookTime +
                ", difficultyLevel='" + difficultyLevel + '\'' +
                ", rating=" + rating +
                ", instruction='" + instruction + '\'' +
                ", ingredients='" + new Gson().fromJson(ingredients, Ingredients[].class) + '\'' +
                ", nutrition_holder='" + nutrition + '\'' +
                ", id=" + id +
                '}';
    }
}
