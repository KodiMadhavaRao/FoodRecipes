package foodrecepies.practise.com.foodrecepies.ui.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

import foodrecepies.practise.com.foodrecepies.R;
import foodrecepies.practise.com.foodrecepies.data.db.FoodRecipeDbHelper;
import foodrecepies.practise.com.foodrecepies.data.model.Ingredients;
import foodrecepies.practise.com.foodrecepies.data.model.Recipe;
import foodrecepies.practise.com.foodrecepies.data.sp.PersistentData;

public class RecipeActivity extends AppCompatActivity {

    private LinearLayout nutritionHolder;
    private LinearLayout ingredientsHolder;
    private Recipe recipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);
        Toolbar toolbar = (Toolbar) findViewById(R.id.anim_toolbar);
        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.ExpandedAppBar);
        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.CollapsedAppBar);

        setSupportActionBar(toolbar);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.edit_recipe);
        fab.setOnClickListener(new RecipeEditListener());
        Bundle recipeBundle = getIntent().getExtras();
        int id = 0;
        if (recipeBundle != null) {
            id = recipeBundle.getInt(HomeActivity.RECIPE);
        }
        recipe = new FoodRecipeDbHelper(this).getRecipeBasedOnId(id, new PersistentData().getID(this));
        final RatingBar mratingBar = (RatingBar) findViewById(R.id.edit_recipe_rating);
        mratingBar.setOnTouchListener(new View.OnTouchListener() {
            float ratingValue;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(RecipeActivity.this);
                    alertDialog.setTitle("Rate " + recipe.getRecipeName());
                    View rateDialog = getLayoutInflater().inflate(R.layout.rate_recipe_dialog, null);
                    final RatingBar ratingBar = (RatingBar) rateDialog.findViewById(R.id.rate_dialog);
                    ratingBar.setNumStars(5);
                    ratingBar.setRating(recipe.getRating());
                    ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                        @Override
                        public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                            ratingValue = rating;
                        }
                    });
                    alertDialog.setView(rateDialog);
                    alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    alertDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            mratingBar.setRating(ratingValue);
                            new FoodRecipeDbHelper(RecipeActivity.this).rateRecipe(recipe.getId(), ratingValue);
                        }
                    });
                    alertDialog.create().show();

                }
                return true;
            }

        });
        mratingBar.setRating(recipe.getRating());
        collapsingToolbarLayout.setTitle("" + recipe.getRecipeName());
        ImageView imageView = (ImageView) findViewById(R.id.image_header);
        imageView.setImageResource(R.drawable.f1);
        TextView diffType = getView(R.id.difficulty_type);
        diffType.setText(recipe.getDifficultyLevel());
        TextView cgTime = getView(R.id.cg_time);
        cgTime.setText("" + recipe.getCookTime());
        TextView noOfServings = getView(R.id.no_serving);
        noOfServings.setText("" + recipe.getPreperationTime());
        TextView cookingTime = getView(R.id.cooking_time);
        cookingTime.setText("" + recipe.getServings());
        TextView description = getView(R.id.description);
        description.setText(recipe.getRecipeDescription());
        TextView prepDesc = getView(R.id.pre_desc);
        prepDesc.setText(recipe.getInstruction());

        nutritionHolder = (LinearLayout) findViewById(R.id.nutrition_layout_holder);
        ingredientsHolder = (LinearLayout) findViewById(R.id.ingredients_layout_holder);

        Gson gson = new Gson();
        Ingredients[] ingredients = gson.fromJson(recipe.getIngredients(), Ingredients[].class);
        for (int i = 0; i < ingredients.length; i++) {
            View ingredientsView = inflate(R.layout.ingredients_holder, null);
            TextView ingredient = (TextView) ingredientsView.findViewById(R.id.ingredient_item);
            ingredientsHolder.addView(ingredient, i + 1);
            ingredient.setText("" + ingredients[i].getIngredientName() + " : " + ingredients[i].getQuantity());
        }

        try {
            JSONObject jsonObject = new JSONObject(recipe.getNutrition());
            Iterator<String> iter = jsonObject.keys();
            while (iter.hasNext()) {
                String key = iter.next();
                try {
                    View nutitionView = inflate(R.layout.nutrition_holder, null);
                    TextView textView = (TextView) nutitionView.findViewById(R.id.nutrition_values);
                    nutritionHolder.addView(textView);
                    textView.setText("" + key + "  :  " + jsonObject.get(key).toString());
                } catch (JSONException e) {
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    private View inflate(int layout, LinearLayout holder) {
        return LayoutInflater.from(this).inflate(layout, holder, false);
    }

    private TextView getView(int id) {
        return (TextView) findViewById(id);
    }

    private class RecipeEditListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.edit_recipe:
                    Intent intent = new Intent(RecipeActivity.this, EditRecipeActivity.class);
                    intent.putExtra("recipeId", recipe.getId());
                    startActivity(intent);
                    break;
            }
        }
    }
}


