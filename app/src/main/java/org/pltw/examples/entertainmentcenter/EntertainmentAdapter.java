package org.pltw.examples.entertainmentcenter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.List;

public class EntertainmentAdapter extends RecyclerView.Adapter<EntertainmentAdapter.EntertainmentViewHolder> {
    private List<Entertainment> entertainmentList;
    private static final String TAG = EntertainmentAdapter.class.getSimpleName();

    // Provide a suitable constructor (depends on the kind of dataset)
    public EntertainmentAdapter(List<Entertainment> entertainment) {
        entertainmentList = entertainment;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public EntertainmentViewHolder onCreateViewHolder(ViewGroup parent,
                                                      int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_simple_entertainment, parent, false);

        EntertainmentViewHolder vh = new EntertainmentViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(EntertainmentViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        Entertainment et = entertainmentList.get(position);
        holder.tvTitle.setText(et.getTitle());
        holder.tvDescription.setText(et.getDescription());
        holder.rbRating.setRating(et.getRating());
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return entertainmentList.size();
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class EntertainmentViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView tvTitle;
        public TextView tvDescription;
        public RatingBar rbRating;
        public View layout;

        public EntertainmentViewHolder(View v) {
            super(v);
            layout = v;
            tvTitle = layout.findViewById(R.id.tv_title);
            tvDescription = layout.findViewById(R.id.tv_description);
            rbRating = layout.findViewById(R.id.rb_rating);
        }
    }
}