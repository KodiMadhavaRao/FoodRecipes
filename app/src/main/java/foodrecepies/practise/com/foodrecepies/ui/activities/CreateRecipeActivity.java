package foodrecepies.practise.com.foodrecepies.ui.activities;

import android.app.Activity;
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
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import foodrecepies.practise.com.foodrecepies.R;
import foodrecepies.practise.com.foodrecepies.data.db.FoodRecipeDbHelper;
import foodrecepies.practise.com.foodrecepies.data.model.Ingredients;
import foodrecepies.practise.com.foodrecepies.data.model.Nutrition;
import foodrecepies.practise.com.foodrecepies.data.model.Recipe;

public class CreateRecipeActivity extends AppCompatActivity {

    int itemId = 101, quantityId = 301;
    int hintSeries = 1;
    Recipe recipe;
    private LinearLayout ingredientHolderView;
    private String[] levels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_recipe);
        recipe = new Recipe();
        levels = new String[]{"Difficulty Level", "Easy", "Moderate", "Hard"};
        CreateRecipeListener createRecipeListener = new CreateRecipeListener();
        RatingBar rating = (RatingBar) findViewById(R.id.create_rating);
        rating.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                recipe.setRating(rating);
            }
        });
        final Spinner levelOfDifficulty = (Spinner) findViewById(R.id.create_difficulty_level);
        levelOfDifficulty.setAdapter(new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, levels));
        levelOfDifficulty.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    ((TextView) ((Spinner) findViewById(R.id.create_difficulty_level)).getSelectedView()).setTextColor(Color.LTGRAY);

                } else {
                    recipe.setDifficultyLevel(levels[position]);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        ((ImageView) findViewById(R.id.save_recipe_into_db)).setOnClickListener(createRecipeListener);
        ((ImageView) findViewById(R.id.dismiss)).setOnClickListener(createRecipeListener);
        ingredientHolderView = (LinearLayout) findViewById(R.id.ingredients_holder);
        ImageView deleteIngredient = (ImageView) findViewById(R.id.delete_ingredients);
        deleteIngredient.setOnClickListener(createRecipeListener);
        ImageView addIngredient = (ImageView) findViewById(R.id.add_ingredients);
        addIngredient.setOnClickListener(createRecipeListener);

    }


    private class CreateRecipeListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            TextView noOfIngredients = (TextView) findViewById(R.id.no_of_ingredients);
            switch (v.getId()) {
                case R.id.save_recipe_into_db:
                    long length = saveRecipeIntoDB(AddRecipe(getNutritions(), getIngredients()));
                    if (length >= 1) {
                        Toast.makeText(CreateRecipeActivity.this, "Recipe inserted succesfully Recipes length :" + length, Toast.LENGTH_SHORT).show();
                        Intent returnIntent = new Intent();
                        setResult(Activity.RESULT_OK, returnIntent);
                        finish();
                    }
                    break;
                case R.id.dismiss:
                    finish();
                case R.id.delete_ingredients:
                    deleteIngredient(noOfIngredients);
                    break;
                case R.id.add_ingredients:
                    if (hintSeries >= 1) {
                        addIngredient(noOfIngredients);
                    } else {
                        hintSeries = 1;
                        addIngredient(noOfIngredients);
                    }
                    break;
            }


        }

        private long saveRecipeIntoDB(Recipe recipe) {
            FoodRecipeDbHelper foodRecipeDbHelper = new FoodRecipeDbHelper(CreateRecipeActivity.this);
            return foodRecipeDbHelper.createNewRecipe(recipe);
        }

        private Recipe AddRecipe(Nutrition nutritions, List<Ingredients> ingredients) {
            recipe.setRecipeName(getStringText(R.id.create_recipe_name));
            recipe.setRecipeDescription(getStringText(R.id.create_recipe_description));
            recipe.setServings(getIntValue(R.id.create_no_of_servings));
            recipe.setPreperationTime(getIntValue(R.id.create_prep_time));
            recipe.setCookTime(getIntValue(R.id.create_cook_time));
            recipe.setInstruction(getStringText(R.id.create_instructions));
            recipe.setIngredients(new Gson().toJson(ingredients));
            recipe.setNutrition(new Gson().toJson(nutritions));
            return recipe;

        }

        private String getStringText(int id) {
            return ((EditText) findViewById(id)).getText().toString().trim();
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

        private void addIngredient(TextView noOfIngredients) {
            noOfIngredients.setText("" + hintSeries);
            View ingredientView = LayoutInflater.from(CreateRecipeActivity.this).inflate(R.layout.ingredients, ingredientHolderView, false);
            ingredientView.setId(hintSeries);
            EditText itemName = (EditText) ingredientView.findViewById(R.id.ingredient_item_name);
            itemName.setId(itemId);
            itemName.setHint("Item " + hintSeries);
            EditText itemQuantity = (EditText) ingredientView.findViewById(R.id.ingredient_item_quantity);
            itemQuantity.setId(quantityId);
            itemQuantity.setHint("Quantity " + hintSeries);
            ingredientHolderView.addView(ingredientView);
            ingredientView.requestFocus();
            itemId++;
            quantityId++;
            hintSeries++;
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

        private int getIntValue(int id) {
            return Integer.parseInt(((EditText) findViewById(id)).getText().toString().trim());
        }
    }
}

