package foodrecepies.practise.com.foodrecepies.data.db;

/**
 * Created by madhav on 2/28/2018.
 */

public class FoodRecipeContract {

    public static final String TABLE_USER = "user";
    public static final String COLUMN_USER_ID = "id";
    public static final String COLUMN_USER_DISPLAY_NAME = "displayName";
    public static final String COLUMN_USER_EMAIL = "email";
    public static final String COLUMN_USER_PASSWORD = "password";
    public static final String COLUMN_USER_GENDER = "gender";
    public static final String COLUMN_USER_BIRTHDAY = "birthday";
    public static final String COLUMN_USER_COUNTRY = "country";

    public static final String TABLE_RECIPE = "recipe";
    public static final String COLUMN_RECIPE_ID = "recipeID";
    public static final String COLUMN_RECIPE_NAME = "recipeName";
    public static final String COLUMN_RECIPE_DESCRIPTION = "recipeDescription";
    public static final String COLUMN_RECIPE_SERVINGS = "recipeServings";
    public static final String COLUMN_RECIPE_PREPERATION_TIME = "recipePrepTime";
    public static final String COLUMN_RECIPE_COOK_TIME = "recipeCookTime";
    public static final String COLUMN_RECIPE_DIFFICULTY_LEVEL = "difficultyLevel";
    public static final String COLUMN_RECIPE_RATING = "recipeRating";
    public static final String COLUMN_RECIPE_INSTRUCTIONS = "recipeInstructions";
    public static final String COLUMN_RECIPE_INGRIDENTS = "recipeIngridents";
    public static final String COLUMN_RECIPE_NUTRITION = "recipeNutrition";

    public static final String TABLE_NOTE = "note";
    public static final String COLUMN_NOTE_ID = "noteID";
    public static final String COLUMN_NOTE_TITLE = "noteTitle";
    public static final String COLUMN_NOTE_LINK = "noteLink";

    public static final String CREATE_USER_TABLE = "CREATE TABLE "
            + TABLE_USER
            + "("
            + COLUMN_USER_ID + " integer primary key autoincrement, "
            + COLUMN_USER_DISPLAY_NAME + " text not null, "
            + COLUMN_USER_EMAIL + " text not null, "
            + COLUMN_USER_PASSWORD + " text not null, "
            + COLUMN_USER_GENDER + " text not null, "
            + COLUMN_USER_BIRTHDAY + " text not null, "
            + COLUMN_USER_COUNTRY + " text not null "
            + ");";

    public static final String CREATE_RECIPE_TABLE = "CREATE TABLE "
            + TABLE_RECIPE
            + "("
            + COLUMN_USER_ID + " integer, "
            + COLUMN_RECIPE_ID + " integer primary key autoincrement, "
            + COLUMN_RECIPE_NAME + " text not null, "
            + COLUMN_RECIPE_DESCRIPTION + " text, "
            + COLUMN_RECIPE_SERVINGS + " integer, "
            + COLUMN_RECIPE_PREPERATION_TIME + " integer, "
            + COLUMN_RECIPE_COOK_TIME + " integer, "
            + COLUMN_RECIPE_DIFFICULTY_LEVEL + " text, "
            + COLUMN_RECIPE_RATING + " real, "
            + COLUMN_RECIPE_INSTRUCTIONS + " text, "
            + COLUMN_RECIPE_INGRIDENTS + " text, "
            + COLUMN_RECIPE_NUTRITION + " text"
            + ");";

    public static final String CREATE_NOTE_TABLE = "CREATE TABLE "
            + TABLE_NOTE
            + "("
            + COLUMN_USER_ID + " integer, "
            + COLUMN_NOTE_ID + " integer primary key autoincrement, "
            + COLUMN_NOTE_TITLE + " text not null, "
            + COLUMN_NOTE_LINK + " text not null "
            + ");";
}
