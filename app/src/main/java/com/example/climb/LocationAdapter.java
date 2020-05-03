package com.example.climb;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.climb.models.Route;
import com.parse.ParseFile;

import java.util.List;

public class LocationAdapter extends RecyclerView.Adapter<LocationAdapter.ViewHolder> {

    private Context context;
    private List<Route> routes;

    public LocationAdapter(Context context, List<Route> routes) {
        this.context = context;
        this.routes= routes;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_climb, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Route route = routes.get(position);
        holder.bind(route);

    }

    @Override
    public int getItemCount() {
        return routes.size();
    }

    public void clear() {
        routes.clear();
        notifyDataSetChanged();
    }

    // Add a list of items -- change to type used
    public void addAll(List<Route> list) {
        routes.addAll(list);
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvClimbName;
        private TextView tvClimbDescription;
        private ImageView ivClimbThumbnail;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvClimbName = itemView.findViewById(R.id.tvClimbName);
            tvClimbDescription = itemView.findViewById(R.id.tvClimbDescription);
            ivClimbThumbnail = itemView.findViewById(R.id.ivClimbThumbnail);

        }

        public void bind(Route route) {
            // Bind the post data to the view element
            tvClimbName.setText(route.getName());
            tvClimbDescription.setText(route.getDescription());
            ParseFile image = route.getThumbnail();
            if (image != null) {
                Glide.with(context).load(image.getUrl()).into(ivClimbThumbnail);
            }
        }
    }


}
