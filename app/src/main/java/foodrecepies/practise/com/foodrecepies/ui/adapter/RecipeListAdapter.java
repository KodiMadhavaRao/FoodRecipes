package foodrecepies.practise.com.foodrecepies.ui.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import foodrecepies.practise.com.foodrecepies.R;
import foodrecepies.practise.com.foodrecepies.data.db.FoodRecipeDbHelper;
import foodrecepies.practise.com.foodrecepies.data.model.Recipe;

/**
 * Created by madhav on 3/5/2018.
 */

public class RecipeListAdapter extends RecyclerView.Adapter<RecipeListAdapter.RecipeHolder> {
    private final Context context;
    private List<Recipe> recipes = new ArrayList<>();
    private List<Recipe> recipeCopy = new ArrayList<>();
    private OnRecipeAdapterItemClickListner onRecipeItemClickListner;
    private int drawableArray[];

    public RecipeListAdapter(Context context, List<Recipe> dbRecipes, OnRecipeAdapterItemClickListner onRecipeItemClickListner) {
        this.context = context;
        this.recipes = dbRecipes;
        this.recipeCopy.addAll(recipes);
        this.onRecipeItemClickListner = onRecipeItemClickListner;
        drawableArray = new int[]{R.drawable.f1, R.drawable.f2, R.drawable.f3, R.drawable.f4, R.drawable.f5, R.drawable.f6, R.drawable.f7};
    }

    @Override
    public RecipeHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recipe_item, parent, false);
        return new RecipeHolder(view, onRecipeItemClickListner);
    }

    @Override
    public void onBindViewHolder(RecipeHolder holder, int position) {
        Recipe recipe = recipes.get(position);
        holder.recipeImage.setImageDrawable(getDrawable(position % 7));
        holder.cookRating.setRating(recipe.getRating());
        holder.recipeName.setText(recipe.getRecipeName());
        holder.cookTime.setText("" + recipe.getCookTime());
        holder.noOfServings.setText("" + recipe.getServings());
        holder.levelOfDifficulty.setText(recipe.getDifficultyLevel());
    }

    private Drawable getDrawable(int position) {
        return context.getResources().getDrawable(drawableArray[position]);
    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }

    public int getIDForParticularPosition(int position) {
        return recipes.get(position).getId();
    }

    public void filter(String searchText) {
        recipes.clear();
        if (searchText.isEmpty()) {
            recipes.addAll(recipeCopy);
        } else {
            searchText = searchText.toLowerCase();
            for (int i = 0; i < recipeCopy.size(); i++) {
                Recipe recipe = recipeCopy.get(i);
                //searching only name you can also get the recipe description but have to change in database getRecipeSampleDetails() method
                // if you want to description add desctipion colum in that method
                if (recipe.getRecipeName().toLowerCase().contains(searchText)) {
                    recipes.add(recipe);
                }
            }
        }
        notifyDataSetChanged();
    }

    public class RecipeHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final OnRecipeAdapterItemClickListner listener;
        private ImageView recipeImage;
        private RatingBar cookRating;
        private TextView recipeName;
        private TextView cookTime;
        private TextView noOfServings;
        private TextView levelOfDifficulty;
        private ImageView deleteRecipe;

        private RecipeHolder(View itemView, OnRecipeAdapterItemClickListner onRecipeItemClickListner) {
            super(itemView);
            this.listener = onRecipeItemClickListner;
            itemView.setOnClickListener(this);
            recipeImage = (ImageView) itemView.findViewById(R.id.recipe_list_image);
            cookRating = (RatingBar) itemView.findViewById(R.id.recipe_list_rating);
            recipeName = (TextView) itemView.findViewById(R.id.recipe_list_name);
            cookTime = (TextView) itemView.findViewById(R.id.recipe_list_cook_time);
            noOfServings = (TextView) itemView.findViewById(R.id.recipe_list_no_services);
            levelOfDifficulty = (TextView) itemView.findViewById(R.id.recipe_list_level);
            deleteRecipe = (ImageView) itemView.findViewById(R.id.delete_recipe);
            deleteRecipe.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            if (v.getId() == R.id.delete_recipe) {
                getConformation();

            } else {
                listener.setClickPosition(getAdapterPosition());
            }
        }

        private void getConformation() {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
            alertDialog.setTitle("Delete Recipe");
            alertDialog.setMessage("Are your sure want to delete this ");
            alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            alertDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    int position = getAdapterPosition();
                    boolean isDeleted = new FoodRecipeDbHelper(context).deleteRecipe(getIDForParticularPosition(position));
                    if (isDeleted) {
                        recipes.remove(position);
                        notifyItemRemoved(position);
                        notifyItemRangeChanged(position, recipes.size());
                    }
                }
            });
            alertDialog.create().show();

        }


    }
}
