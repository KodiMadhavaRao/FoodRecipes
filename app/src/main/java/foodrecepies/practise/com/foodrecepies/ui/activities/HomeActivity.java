package foodrecepies.practise.com.foodrecepies.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import de.hdodenhof.circleimageview.CircleImageView;
import foodrecepies.practise.com.foodrecepies.R;
import foodrecepies.practise.com.foodrecepies.data.db.FoodRecipeDbHelper;
import foodrecepies.practise.com.foodrecepies.data.model.User;
import foodrecepies.practise.com.foodrecepies.data.sp.PersistentData;
import foodrecepies.practise.com.foodrecepies.ui.adapter.OnRecipeAdapterItemClickListner;
import foodrecepies.practise.com.foodrecepies.ui.adapter.RecipeListAdapter;

public class HomeActivity extends AppCompatActivity implements OnRecipeAdapterItemClickListner, NavigationView.OnNavigationItemSelectedListener {

    public static final int REQUEST_CODE = 121;
    public static final String RECIPE = "recipeId";
    private RecyclerView recyclerView;
    private FloatingActionButton addRecipe;
    private RecipeListAdapter recipeListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        Toolbar searchToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(searchToolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, searchToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        User user=new FoodRecipeDbHelper(this).getUserDetails();

        View navHeader = navigationView.getHeaderView(0);
        CircleImageView circleImageView = (CircleImageView)navHeader. findViewById(R.id.nav_profile_image);
        if (user.getUserGender().equals("Male")){
            circleImageView.setImageDrawable(getResources().getDrawable(R.mipmap.ic_man));
        }else {
            circleImageView.setImageDrawable(getResources().getDrawable(R.mipmap.ic_girl));
        }
        TextView userName = (TextView)navHeader. findViewById(R.id.nav_user_name);
        userName.setText(user.getUserName());
//        userName.setCompoundDrawablePadding(8);
//        userName.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_action_theme_user, 0, 0, 0);
        TextView userEmail = (TextView)navHeader. findViewById(R.id.nav_user_email);
        userEmail.setText(user.getUserEmail());
//        userEmail.setCompoundDrawablePadding(8);
//        userEmail.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_action_theme_email, 0, 0, 0);
        TextView userLocation = (TextView) navHeader.findViewById(R.id.nav_user_location);
        userLocation.setText(user.getUserCountry());
//        userLocation.setCompoundDrawablePadding(8);
//        userLocation.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_action_theme_location, 0, 0, 0);
        recyclerView = (RecyclerView)findViewById(R.id.recipe_recylcer);
        addRecipe = (FloatingActionButton) findViewById(R.id.add_recipe);
        addRecipe.setOnClickListener(new HomeActivityListener());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recipeListAdapter = new RecipeListAdapter(this, new FoodRecipeDbHelper(this).getRecipeSampleDetails(new PersistentData().getID(this)), this);
        recyclerView.setAdapter(recipeListAdapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE) {
            recyclerView.setAdapter(new RecipeListAdapter(this, new FoodRecipeDbHelper(this).getRecipeSampleDetails(new PersistentData().getID(this)), this));
            recyclerView.invalidate();
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.search_menu, menu);
        MenuItem myActionMenuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) myActionMenuItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                recipeListAdapter.filter(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                recipeListAdapter.filter(newText);
                return true;
            }
        });
        return true;
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.home) {

        } else if (id == R.id.saved_links) {
            startActivity(new Intent(this, RecipeNoteActivity.class));

        } else if (id == R.id.profile) {

        } else if (id == R.id.settings) {

        } else if (id == R.id.about) {

        } else if (id == R.id.logout) {
            new PersistentData().setLoginValid(this, false);
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void setClickPosition(int positon) {
        Intent intent = new Intent(this, RecipeActivity.class);
        intent.putExtra(RECIPE, ((RecipeListAdapter) recyclerView.getAdapter()).getIDForParticularPosition(positon));
        startActivity(intent);
    }


    private class HomeActivityListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.add_recipe:
                    Intent intent = new Intent(HomeActivity.this, CreateRecipeActivity.class);
                    startActivityForResult(intent, REQUEST_CODE);
                    break;
            }
        }
    }
}
