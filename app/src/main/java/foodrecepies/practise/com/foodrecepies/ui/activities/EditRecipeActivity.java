package foodrecepies.practise.com.foodrecepies.ui.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import foodrecepies.practise.com.foodrecepies.R;
import foodrecepies.practise.com.foodrecepies.data.db.FoodRecipeDbHelper;
import foodrecepies.practise.com.foodrecepies.data.model.Ingredients;
import foodrecepies.practise.com.foodrecepies.data.model.Nutrition;
import foodrecepies.practise.com.foodrecepies.data.model.Recipe;
import foodrecepies.practise.com.foodrecepies.data.sp.PersistentData;

public class EditRecipeActivity extends AppCompatActivity {
    private static final int REQUEST_CODE = 121;
    int nutritionIds = 0;
    int itemId = 101, quantityId = 301;
    int hintSeries = 1;
    private Recipe recipe;
    private LinearLayout ingredientHolderView;
    private TextView editNofoingredients;
    private String[] levels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_recipe);
        if (getIntent().getExtras().getInt("recipeId") != 0) {
            EditListener editListener = new EditListener();
            ((ImageView) findViewById(R.id.update_recipe_into_db)).setOnClickListener(editListener);
            ((ImageView) findViewById(R.id.edit_dismiss)).setOnClickListener(editListener);
            levels = new String[]{"Difficulty Level", "Easy", "Moderate", "Hard"};
            RatingBar rating = (RatingBar) findViewById(R.id.edit_rating);
            rating.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                @Override
                public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                    recipe.setRating(rating);
                }
            });
            final Spinner levelOfDifficulty = (Spinner) findViewById(R.id.edit_difficulty_level);
            levelOfDifficulty.setAdapter(new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, levels));
            levelOfDifficulty.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    if (position == 0) {
                        ((TextView) ((Spinner) findViewById(R.id.edit_difficulty_level)).getSelectedView()).setTextColor(Color.LTGRAY);

                    } else {
                        recipe.setDifficultyLevel(levels[position]);
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
            ingredientHolderView = (LinearLayout) findViewById(R.id.edit_ingredients_holder);
            editNofoingredients = (TextView) findViewById(R.id.edit_no_of_ingredients);
            ImageView addIngredient = (ImageView) findViewById(R.id.edit_add_ingredients);
            addIngredient.setOnClickListener(editListener);
            ImageView deleteIngredient = (ImageView) findViewById(R.id.edit_delete_ingredients);
            deleteIngredient.setOnClickListener(editListener);

            recipe = new FoodRecipeDbHelper(this).getRecipeBasedOnId(getIntent().getExtras().getInt("recipeId"), new PersistentData().getID(this));
            fillNutritionValues();
            fillRecipeBasicDetails();
            setRating();
            setIngredients();
        }

    }

    private void setIngredients() {
        Ingredients[] ingredients = new Gson().fromJson(recipe.getIngredients(), Ingredients[].class);
        for (int i = 0; i < ingredients.length; i++) {
            addIngredient(ingredients[i].getIngredientName(), ingredients[i].getQuantity());
        }
    }

    private void setRating() {
        ((RatingBar) findViewById(R.id.edit_rating)).setRating(recipe.getRating());
    }

    private void fillRecipeBasicDetails() {
        int[] ids = new int[]{R.id.edit_recipe_name, R.id.edit_recipe_description, R.id.edit_no_of_servings, R.id.edit_prep_time, R.id.edit_cook_time, R.id.edit_instructions};
        String[] values = new String[]{recipe.getRecipeName(), recipe.getRecipeDescription(), Integer.toString(recipe.getServings()), Integer.toString(recipe.getPreperationTime()), Integer.toString(recipe.getCookTime()), recipe.getInstruction()};
        for (int i = 0; i < ids.length; i++) {
            getViewById(ids[i]).setText(values[i]);
        }
    }

    private void fillNutritionValues() {
        JSONObject jsonObject = null;
        int[] ids = new int[]{R.id.calories, R.id.fat, R.id.carbohydrates, R.id.protien, R.id.cholestrol, R.id.sodium};
        try {
            jsonObject = new JSONObject(recipe.getNutrition());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Iterator<String> iter = jsonObject.keys();
        while (iter.hasNext()) {
            String key = iter.next();
            try {
                getViewById(ids[nutritionIds]).setText(jsonObject.get(key).toString());
            } catch (JSONException e) {
            }
            nutritionIds++;
        }
    }

    private EditText getViewById(int id) {
        return (EditText) findViewById(id);
    }

    private void addIngredient(String ingreitemName, String ingrequantity) {
        editNofoingredients.setText("" + hintSeries);
        View ingredientView = LayoutInflater.from(EditRecipeActivity.this).inflate(R.layout.ingredients, ingredientHolderView, false);
        ingredientView.setId(hintSeries);
        EditText itemName = (EditText) ingredientView.findViewById(R.id.ingredient_item_name);
        if (ingreitemName != null)
            itemName.setText(ingreitemName);
        itemName.setId(itemId);
        itemName.setHint("Item " + hintSeries);
        EditText itemQuantity = (EditText) ingredientView.findViewById(R.id.ingredient_item_quantity);
        itemQuantity.setId(quantityId);
        if (ingrequantity != null)
            itemQuantity.setText(ingrequantity);
        itemQuantity.setHint("Quantity " + hintSeries);
        ingredientHolderView.addView(ingredientView);
        ingredientView.requestFocus();
        itemId++;
        quantityId++;
        hintSeries++;
    }

    private class EditListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.update_recipe_into_db:
                    int length = updateRecipeinDB(AddRecipe(getNutritions(), getIngredients()));
                    if (length >= 1) {
                        Intent returnIntent = new Intent(EditRecipeActivity.this, HomeActivity.class);
                        startActivity(returnIntent);
                        finish();
                    }
                    break;
                case R.id.edit_dismiss:
                    finish();
                case R.id.edit_delete_ingredients:
                    deleteIngredient(editNofoingredients);
                    break;
                case R.id.edit_add_ingredients:
                    if (hintSeries >= 1) {
                        addIngredient(null, null);
                    } else {
                        hintSeries = 1;
                        addIngredient(null, null);
                    }
                    break;
            }
        }

        private void deleteIngredient(TextView noOfIngredients) {
            hintSeries = hintSeries - 1;
            if (hintSeries >= 1) {
                ingredientHolderView.removeViewAt(hintSeries);
                if (hintSeries == 1)
                    noOfIngredients.setText("");
                else
                    noOfIngredients.setText("" + hintSeries);


            }
        }

        private int updateRecipeinDB(Recipe recipe) {
            FoodRecipeDbHelper foodRecipeDbHelper = new FoodRecipeDbHelper(EditRecipeActivity.this);
            return foodRecipeDbHelper.updateRecipe(recipe, recipe.getId());
        }

        private Recipe AddRecipe(Nutrition nutritions, List<Ingredients> ingredients) {
            recipe.setRecipeName(getStringText(R.id.edit_recipe_name));
            recipe.setRecipeDescription(getStringText(R.id.edit_recipe_description));
            recipe.setServings(getIntValue(R.id.edit_no_of_servings));
            recipe.setPreperationTime(getIntValue(R.id.edit_prep_time));
            recipe.setCookTime(getIntValue(R.id.edit_cook_time));
            recipe.setInstruction(getStringText(R.id.edit_instructions));
            recipe.setIngredients(new Gson().toJson(ingredients));
            recipe.setNutrition(new Gson().toJson(nutritions));
            return recipe;

        }

        private String getStringText(int id) {
            return ((EditText) findViewById(id)).getText().toString().trim();
        }

        private int getIntValue(int id) {
            return Integer.parseInt(((EditText) findViewById(id)).getText().toString().trim());
        }

        public Nutrition getNutritions() {
            Nutrition nutrition = new Nutrition();
            nutrition.setCalories(getIntValue(R.id.calories));
            nutrition.setFat(getIntValue(R.id.fat));
            nutrition.setCarbohydrates(getIntValue(R.id.carbohydrates));
            nutrition.setProtein(getIntValue(R.id.protien));
            nutrition.setCholesterol(getIntValue(R.id.cholestrol));
            nutrition.setSodium(getIntValue(R.id.sodium));
            return nutrition;
        }

        public List<Ingredients> getIngredients() {
            List<Ingredients> ingredients = new ArrayList<>();
            for (int i = 1, itemId = 101, quantityId = 301; i < hintSeries; i++, itemId++, quantityId++) {
                View view = (View) ingredientHolderView.findViewById(i);
                EditText itemName = (EditText) view.findViewById(itemId);
                EditText quantity = (EditText) view.findViewById(quantityId);
                ingredients.add(new Ingredients(itemName.getText().toString().trim(), quantity.getText().toString().trim()));
            }
            return ingredients;
        }
    }
}
