package foodrecepies.practise.com.foodrecepies.data.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import foodrecepies.practise.com.foodrecepies.data.model.Link;
import foodrecepies.practise.com.foodrecepies.data.model.Recipe;
import foodrecepies.practise.com.foodrecepies.data.model.Registration;
import foodrecepies.practise.com.foodrecepies.data.model.User;
import foodrecepies.practise.com.foodrecepies.data.sp.PersistentData;

/**
 * Created by madhav on 2/28/2018.
 */

public class FoodRecipeDbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "Recipes.db";
    private Context context;
    private List<Link> notes;

    public FoodRecipeDbHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(FoodRecipeContract.CREATE_USER_TABLE);
        db.execSQL(FoodRecipeContract.CREATE_RECIPE_TABLE);
        db.execSQL(FoodRecipeContract.CREATE_NOTE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + FoodRecipeContract.TABLE_USER);
        onCreate(db);
    }

    public long userRegistration(Registration registration) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(FoodRecipeContract.COLUMN_USER_DISPLAY_NAME, registration.getDisplayName());
        values.put(FoodRecipeContract.COLUMN_USER_EMAIL, registration.getEmail());
        values.put(FoodRecipeContract.COLUMN_USER_PASSWORD, registration.getPassword());
        values.put(FoodRecipeContract.COLUMN_USER_GENDER, registration.getGender());
        values.put(FoodRecipeContract.COLUMN_USER_BIRTHDAY, registration.getBirthday());
        values.put(FoodRecipeContract.COLUMN_USER_COUNTRY, registration.getCountry());
        return sqLiteDatabase.insert(FoodRecipeContract.TABLE_USER, null, values);
    }

    public Cursor authenticateUser(String[] loginDetails) {
        Cursor cursor = getReadableDatabase().query(FoodRecipeContract.TABLE_USER
                , new String[]{FoodRecipeContract.COLUMN_USER_ID, FoodRecipeContract.COLUMN_USER_EMAIL, FoodRecipeContract.COLUMN_USER_PASSWORD}
                , FoodRecipeContract.COLUMN_USER_EMAIL + "=?" + " AND " + FoodRecipeContract.COLUMN_USER_PASSWORD + "=?"
                , loginDetails
                , null, null, null);
        return cursor;
    }

    public long createNewRecipe(Recipe recipe) {

        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(FoodRecipeContract.COLUMN_USER_ID, new PersistentData().getID(context));
        values.put(FoodRecipeContract.COLUMN_RECIPE_NAME, recipe.getRecipeName());
        values.put(FoodRecipeContract.COLUMN_RECIPE_DESCRIPTION, recipe.getRecipeDescription());
        values.put(FoodRecipeContract.COLUMN_RECIPE_SERVINGS, recipe.getServings());
        values.put(FoodRecipeContract.COLUMN_RECIPE_PREPERATION_TIME, recipe.getPreperationTime());
        values.put(FoodRecipeContract.COLUMN_RECIPE_COOK_TIME, recipe.getCookTime());
        values.put(FoodRecipeContract.COLUMN_RECIPE_DIFFICULTY_LEVEL, recipe.getDifficultyLevel());
        values.put(FoodRecipeContract.COLUMN_RECIPE_RATING, recipe.getRating());
        values.put(FoodRecipeContract.COLUMN_RECIPE_INSTRUCTIONS, recipe.getInstruction());
        values.put(FoodRecipeContract.COLUMN_RECIPE_INGRIDENTS, recipe.getIngredients());
        values.put(FoodRecipeContract.COLUMN_RECIPE_NUTRITION, recipe.getNutrition());
        return sqLiteDatabase.insert(FoodRecipeContract.TABLE_RECIPE, null, values);
    }
    public User getUserDetails() {
        Cursor cursor = getReadableDatabase().query(FoodRecipeContract.TABLE_USER
                , new String[]{FoodRecipeContract.COLUMN_USER_DISPLAY_NAME, FoodRecipeContract.COLUMN_USER_EMAIL, FoodRecipeContract.COLUMN_USER_COUNTRY,FoodRecipeContract.COLUMN_USER_GENDER}
                , FoodRecipeContract.COLUMN_USER_ID + "=?"
                , new String[]{String.valueOf(new PersistentData().getID(context))}
                , null, null, null);
        return getUser(cursor);
    }

    private User getUser(Cursor cursor) {
        User user=new User();
        try {
            while (cursor.moveToNext()) {
                user.setUserName(cursor.getString(cursor.getColumnIndex(FoodRecipeContract.COLUMN_USER_DISPLAY_NAME)));
                user.setUserEmail(cursor.getString(cursor.getColumnIndex(FoodRecipeContract.COLUMN_USER_EMAIL)));
                user.setUserCountry(cursor.getString(cursor.getColumnIndex(FoodRecipeContract.COLUMN_USER_COUNTRY)));
                user.setUserGender(cursor.getString(cursor.getColumnIndex(FoodRecipeContract.COLUMN_USER_GENDER)));
            }
        } finally {
            cursor.close();
        }
        return user;
    }

    public List<Recipe> getRecipeSampleDetails(int id) {
        Cursor cursor = getReadableDatabase().query(FoodRecipeContract.TABLE_RECIPE
                , new String[]{FoodRecipeContract.COLUMN_RECIPE_ID, FoodRecipeContract.COLUMN_RECIPE_NAME, FoodRecipeContract.COLUMN_RECIPE_RATING, FoodRecipeContract.COLUMN_RECIPE_COOK_TIME, FoodRecipeContract.COLUMN_RECIPE_DIFFICULTY_LEVEL, FoodRecipeContract.COLUMN_RECIPE_SERVINGS}
                , FoodRecipeContract.COLUMN_USER_ID + "=?"
                , new String[]{String.valueOf(id)}
                , null, null, null);
        return getListofRecipes(cursor);
    }


    public Recipe getRecipeBasedOnId(int recipeId, int id) {
        Cursor cursor = getReadableDatabase().query(FoodRecipeContract.TABLE_RECIPE
                , null
                , FoodRecipeContract.COLUMN_RECIPE_ID + "=?" + " AND " + FoodRecipeContract.COLUMN_USER_ID + "=?"
                , new String[]{String.valueOf(recipeId), String.valueOf(id)}
                , null, null, null);
        return getRecipeInformation(cursor);
    }

    private Recipe getRecipeInformation(Cursor cursor) {
        Recipe recipe = new Recipe();
        try {
            while (cursor.moveToNext()) {
                recipe.setId(cursor.getInt(cursor.getColumnIndex(FoodRecipeContract.COLUMN_RECIPE_ID)));
                recipe.setRecipeName(cursor.getString(cursor.getColumnIndex(FoodRecipeContract.COLUMN_RECIPE_NAME)));
                recipe.setRecipeDescription(cursor.getString(cursor.getColumnIndex(FoodRecipeContract.COLUMN_RECIPE_DESCRIPTION)));
                recipe.setServings(cursor.getInt(cursor.getColumnIndex(FoodRecipeContract.COLUMN_RECIPE_SERVINGS)));
                recipe.setPreperationTime(cursor.getInt(cursor.getColumnIndex(FoodRecipeContract.COLUMN_RECIPE_PREPERATION_TIME)));
                recipe.setCookTime(cursor.getInt(cursor.getColumnIndex(FoodRecipeContract.COLUMN_RECIPE_COOK_TIME)));
                recipe.setDifficultyLevel(cursor.getString(cursor.getColumnIndex(FoodRecipeContract.COLUMN_RECIPE_DIFFICULTY_LEVEL)));
                recipe.setRating(cursor.getFloat(cursor.getColumnIndex(FoodRecipeContract.COLUMN_RECIPE_RATING)));
                recipe.setInstruction(cursor.getString(cursor.getColumnIndex(FoodRecipeContract.COLUMN_RECIPE_INSTRUCTIONS)));
                recipe.setIngredients(cursor.getString(cursor.getColumnIndex(FoodRecipeContract.COLUMN_RECIPE_INGRIDENTS)));
                recipe.setNutrition(cursor.getString(cursor.getColumnIndex(FoodRecipeContract.COLUMN_RECIPE_NUTRITION)));
            }
        } finally {
            cursor.close();
        }
        return recipe;
    }

    public long addNote(Link link) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(FoodRecipeContract.COLUMN_USER_ID, new PersistentData().getID(context));
        values.put(FoodRecipeContract.COLUMN_NOTE_TITLE, link.getTitle());
        values.put(FoodRecipeContract.COLUMN_NOTE_LINK, link.getLink());
        return sqLiteDatabase.insert(FoodRecipeContract.TABLE_NOTE, null, values);
    }


    public boolean deleteRecipe(int recipeId) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        return sqLiteDatabase.delete(FoodRecipeContract.TABLE_RECIPE, FoodRecipeContract.COLUMN_RECIPE_ID + "=" + recipeId + " AND " + FoodRecipeContract.COLUMN_USER_ID + "=" + new PersistentData().getID(context), null) > 0;
    }

    public long rateRecipe(int recipeId, float rating) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(FoodRecipeContract.COLUMN_RECIPE_RATING, rating);
        return sqLiteDatabase.update(FoodRecipeContract.TABLE_RECIPE, values, FoodRecipeContract.COLUMN_RECIPE_ID + "=" + recipeId + " AND " + FoodRecipeContract.COLUMN_USER_ID + "=" + new PersistentData().getID(context), null);
    }

    public List<Recipe> getListofRecipes(Cursor cursor) {
        List<Recipe> recipes = new ArrayList<>();

        try {
            while (cursor.moveToNext()) {
                Recipe recipe = new Recipe();
                recipe.setId(cursor.getInt(cursor.getColumnIndex(FoodRecipeContract.COLUMN_RECIPE_ID)));
                recipe.setRecipeName(cursor.getString(cursor.getColumnIndex(FoodRecipeContract.COLUMN_RECIPE_NAME)));
                recipe.setRating(cursor.getFloat(cursor.getColumnIndex(FoodRecipeContract.COLUMN_RECIPE_RATING)));
                recipe.setCookTime(cursor.getInt(cursor.getColumnIndex(FoodRecipeContract.COLUMN_RECIPE_COOK_TIME)));
                recipe.setDifficultyLevel(cursor.getString(cursor.getColumnIndex(FoodRecipeContract.COLUMN_RECIPE_DIFFICULTY_LEVEL)));
                recipe.setServings(cursor.getInt(cursor.getColumnIndex(FoodRecipeContract.COLUMN_RECIPE_SERVINGS)));
                recipes.add(recipe);
            }
        } finally {
            cursor.close();
        }
        return recipes;
    }

    public int updateRecipe(Recipe recipe, int id) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(FoodRecipeContract.COLUMN_RECIPE_NAME, recipe.getRecipeName());
        values.put(FoodRecipeContract.COLUMN_RECIPE_DESCRIPTION, recipe.getRecipeDescription());
        values.put(FoodRecipeContract.COLUMN_RECIPE_SERVINGS, recipe.getServings());
        values.put(FoodRecipeContract.COLUMN_RECIPE_PREPERATION_TIME, recipe.getPreperationTime());
        values.put(FoodRecipeContract.COLUMN_RECIPE_COOK_TIME, recipe.getCookTime());
        values.put(FoodRecipeContract.COLUMN_RECIPE_DIFFICULTY_LEVEL, recipe.getDifficultyLevel());
        values.put(FoodRecipeContract.COLUMN_RECIPE_RATING, recipe.getRating());
        values.put(FoodRecipeContract.COLUMN_RECIPE_INSTRUCTIONS, recipe.getInstruction());
        values.put(FoodRecipeContract.COLUMN_RECIPE_INGRIDENTS, recipe.getIngredients());
        values.put(FoodRecipeContract.COLUMN_RECIPE_NUTRITION, recipe.getNutrition());
        return sqLiteDatabase.update(FoodRecipeContract.TABLE_RECIPE, values, FoodRecipeContract.COLUMN_RECIPE_ID + "=" + id + " AND " + FoodRecipeContract.COLUMN_USER_ID + "=" + new PersistentData().getID(context), null);
    }

    public List<Link> getNotes() {
        Cursor cursor = getReadableDatabase().query(FoodRecipeContract.TABLE_NOTE
                , null
                , FoodRecipeContract.COLUMN_USER_ID + "=?"
                , new String[]{String.valueOf(new PersistentData().getID(context))}
                , null, null, null);
        return getNotesValues(cursor);
    }

    public List<Link> getNotesValues(Cursor cursor) {
        List<Link> links = new ArrayList<>();
        try {
            while (cursor.moveToNext()) {
                Link link = new Link();
                link.setId(cursor.getInt(cursor.getColumnIndex(FoodRecipeContract.COLUMN_NOTE_ID)));
                link.setTitle(cursor.getString(cursor.getColumnIndex(FoodRecipeContract.COLUMN_NOTE_TITLE)));
                link.setLink(cursor.getString(cursor.getColumnIndex(FoodRecipeContract.COLUMN_NOTE_LINK)));
                links.add(link);
            }
        } finally {
            cursor.close();
        }
        return links;
    }

    public long editNote(Link link) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(FoodRecipeContract.COLUMN_NOTE_TITLE, link.getTitle());
        values.put(FoodRecipeContract.COLUMN_NOTE_LINK, link.getLink());
        return sqLiteDatabase.update(FoodRecipeContract.TABLE_NOTE, values, FoodRecipeContract.COLUMN_NOTE_ID + "=" + link.getId() + " AND " + FoodRecipeContract.COLUMN_USER_ID + "=" + new PersistentData().getID(context), null);
    }

    public boolean deleteNote(int position) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        return sqLiteDatabase.delete(FoodRecipeContract.TABLE_NOTE, FoodRecipeContract.COLUMN_NOTE_ID + "=" + position + " AND " + FoodRecipeContract.COLUMN_USER_ID + "=" + new PersistentData().getID(context), null) > 0;
    }
}
