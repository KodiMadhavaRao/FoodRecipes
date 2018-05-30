package foodrecepies.practise.com.foodrecepies.ui.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import foodrecepies.practise.com.foodrecepies.R;
import foodrecepies.practise.com.foodrecepies.data.db.FoodRecipeDbHelper;
import foodrecepies.practise.com.foodrecepies.data.model.Link;
import foodrecepies.practise.com.foodrecepies.ui.activities.RecipeNoteActivity;

/**
 * Created by madhav on 3/12/2018.
 */

public class NoteRecyclerAdapter extends RecyclerView.Adapter<NoteRecyclerAdapter.NoteViewHolder> {
    private final Context context;
    private final List<Link> links;

    public NoteRecyclerAdapter(Context context, List<Link> links) {
        this.context = context;
        this.links = links;
    }

    @Override
    public NoteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.link_item, parent, false);
        return new NoteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NoteViewHolder holder, int position) {
        holder.title.setText(links.get(position).getTitle());
        holder.link.setText(links.get(position).getLink());
    }

    @Override
    public int getItemCount() {
        return links.size();
    }

    public class NoteViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final TextView link;
        private final TextView title;
        private final TextView deleteNote;
        private final TextView editNote;
        private final LinearLayout openLink;

        public NoteViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.recycler_note_title);
            openLink = (LinearLayout) itemView.findViewById(R.id.open_link);
            openLink.setOnClickListener(this);
            link = (TextView) itemView.findViewById(R.id.recycler_note_link);
            editNote = (TextView) itemView.findViewById(R.id.edit_note);
            editNote.setOnClickListener(this);
            deleteNote = (TextView) itemView.findViewById(R.id.delete_note);
            deleteNote.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.edit_note:
                    editDialog(getAdapterPosition());
                    break;
                case R.id.delete_note:
                    if (new FoodRecipeDbHelper(context).deleteNote(links.get(getAdapterPosition()).getId())) {
                        int position = getAdapterPosition();
                        links.remove(position);
                        notifyItemRemoved(position);
                        notifyItemRangeChanged(position, links.size());
                    }
                    break;
                case R.id.open_link:
                    String link = links.get(getAdapterPosition()).getLink();
                    if (link.contains("http://") || link.contains("https://")) {
                        openBrowser(link);
                    } else {
                        link = "https://" + link;
                        openBrowser(link);
                    }
                    break;
            }
        }

        private void openBrowser(String link) {
            Intent browser = new Intent(Intent.ACTION_VIEW, Uri.parse(link));
            context.startActivity(browser);
        }

        private void editDialog(final int position) {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
            alertDialog.setTitle("Recipe Edit");
            final View recipeNote = ((RecipeNoteActivity) context).getLayoutInflater().inflate(R.layout.recipe_note_item, null);
            alertDialog.setView(recipeNote);
            final EditText titleET = (EditText) recipeNote.findViewById(R.id.note_title);
            titleET.setText(links.get(position).getTitle());
            final EditText linkET = (EditText) recipeNote.findViewById(R.id.note_link);
            linkET.setText(links.get(position).getLink());
            alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            alertDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    String title = titleET.getText().toString().trim();
                    String link = linkET.getText().toString().trim();
                    long length = new FoodRecipeDbHelper(context).editNote(new Link(title, link, links.get(position).getId()));
                    if (length >= 1) {
                        Toast.makeText(context, " Updated sucessuffly ", Toast.LENGTH_SHORT).show();
                        notifyItemChanged(position);

                    }
                }
            });
            alertDialog.create().show();
        }
    }
}

