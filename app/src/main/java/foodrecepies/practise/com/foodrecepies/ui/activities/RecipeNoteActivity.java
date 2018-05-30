package foodrecepies.practise.com.foodrecepies.ui.activities;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import foodrecepies.practise.com.foodrecepies.R;
import foodrecepies.practise.com.foodrecepies.data.db.FoodRecipeDbHelper;
import foodrecepies.practise.com.foodrecepies.data.model.Link;
import foodrecepies.practise.com.foodrecepies.ui.adapter.NoteRecyclerAdapter;

public class RecipeNoteActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private NoteRecyclerAdapter noteRecyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_note);
        FloatingActionButton noteFab = (FloatingActionButton) findViewById(R.id.recipe_note);
        noteFab.setOnClickListener(new RecipeNoteActivityListener());
        recyclerView = (RecyclerView) findViewById(R.id.recipe_note_recycler);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), linearLayoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);
        noteRecyclerAdapter = new NoteRecyclerAdapter(this, new FoodRecipeDbHelper(this).getNotes());
        recyclerView.setAdapter(noteRecyclerAdapter);

    }

    private class RecipeNoteActivityListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            if (view.getId() == R.id.recipe_note) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(RecipeNoteActivity.this);
                alertDialog.setTitle("Recipe Note");
                final View recipeNote = getLayoutInflater().inflate(R.layout.recipe_note_item, null);
                alertDialog.setView(recipeNote);
                alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                alertDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        EditText titleET = (EditText) recipeNote.findViewById(R.id.note_title);
                        String title = titleET.getText().toString().trim();
                        EditText linkET = (EditText) recipeNote.findViewById(R.id.note_link);
                        String link = linkET.getText().toString().trim();
                        long length = new FoodRecipeDbHelper(RecipeNoteActivity.this).addNote(new Link(title, link));
                        if (length >= 1) {
                            Toast.makeText(RecipeNoteActivity.this, " note saved sucessfully " + length, Toast.LENGTH_SHORT).show();
                            recyclerView.setAdapter(new NoteRecyclerAdapter(RecipeNoteActivity.this, new FoodRecipeDbHelper(RecipeNoteActivity.this).getNotes()));
                        }
                    }
                });
                alertDialog.create().show();
            }
        }
    }
}
