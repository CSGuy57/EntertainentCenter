package org.pltw.examples.entertainmentcenter;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;

import java.util.List;

public class EntertainmentAdapter extends RecyclerView.Adapter<EntertainmentAdapter.EntertainmentViewHolder> {
    private List<Entertainment> entertainmentList;
    private static final String TAG = EntertainmentAdapter.class.getSimpleName();
    private Entertainment recentlyRemovedItem;
    private int recentlyRemovedItemPosition;

    // Provide a suitable constructor (depends on the kind of dataset)
    public EntertainmentAdapter(List<Entertainment> entertainment) {
        entertainmentList = entertainment;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public EntertainmentViewHolder onCreateViewHolder(ViewGroup parent,
                                                      int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());

        EntertainmentViewHolder vh = new EntertainmentViewHolder(layoutInflater, parent);
        return vh;
    }

    @Override
    public void onBindViewHolder(EntertainmentViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        Entertainment et = entertainmentList.get(position);

        holder.bind(et);
    }

    private void showUndoSnackbar(RecyclerView.ViewHolder holder) {
        View eVH = ((EntertainmentViewHolder) holder).viewContainer;

        Snackbar snackbar = Snackbar
                .make(eVH, R.string.snack_bar_title, Snackbar.LENGTH_LONG)
                .setAction(R.string.snack_bar_undo, v -> undoRemove());
        snackbar.show();
    }

    private void undoRemove() {
        entertainmentList.add(recentlyRemovedItemPosition, recentlyRemovedItem);
        notifyItemInserted(recentlyRemovedItemPosition);

        recentlyRemovedItem = null;
    }

    public void removeItemWithUndo(RecyclerView.ViewHolder holder, int position) {
        final Entertainment entertainment = entertainmentList.get(position);

        // If there has been an item already deleted
        if(recentlyRemovedItem != null) {
            // Actually delete the previous item from the DB
            deleteEntertainmentItem();
        }

        Log.i(TAG, "Item removed from RecyclerView: " + position);
        entertainmentList.remove(position);
        notifyItemRemoved(position);
        recentlyRemovedItem = entertainment;
        recentlyRemovedItemPosition = position;
        showUndoSnackbar(holder);
    }

    public void deleteEntertainmentItem(){
        // New contact object has been saved, now it can be deleted
        Backendless.Persistence.of(Entertainment.class).remove(
                recentlyRemovedItem, new AsyncCallback<Long>() {
                    @Override
                    public void handleResponse( Long response )
                    {
                        Log.i(TAG, "Item Deleted");
                    }
                    @Override
                    public void handleFault( BackendlessFault fault )
                    {
                        Log.i(TAG, fault.toString());
                    }
                } );
    }

    @Override
    public int getItemCount() {
        return entertainmentList.size();
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class EntertainmentViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener{
        // each data item is just a string in this case
        public View viewContainer;
        private TextView tvTitle;
        private TextView tvDescription;
        private RatingBar rbRating;
        private Entertainment entertainment;

        public EntertainmentViewHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.item_simple_entertainment, parent, false));
            this.viewContainer = itemView;

            itemView.setOnClickListener(this);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvDescription = itemView.findViewById(R.id.tv_description);
            rbRating = itemView.findViewById(R.id.rb_rating);
        }

        public void bind(Entertainment entertainment){
            this.entertainment = entertainment;

            tvTitle.setText(entertainment.getTitle());
            tvDescription.setText(entertainment.getDescription());
            rbRating.setRating(entertainment.getRating());
        }

        @Override
        public void onClick(View v) {
            Intent i = new Intent(v.getContext(), EntertainmentActivity.class);
            i.putExtra(MainActivity.ET_ITEM, entertainment);
            v.getContext().startActivity(i);
        }
    }
}