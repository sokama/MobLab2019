package com.example.screengo.model;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.screengo.R;

import java.util.ArrayList;
import java.util.List;

public class PlaceAdapter extends RecyclerView.Adapter<PlaceAdapter.PlaceViewHolder> {

    private static final List<Place> items = new ArrayList<>();

    @NonNull
    @Override
    public PlaceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.place_list_item, parent, false);
        PlaceViewHolder viewHolder = new PlaceViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final PlaceViewHolder holder, int position) {
        Place item = items.get(position);
        holder.name.setText(item.name);
        holder.brightness.setProgress(item.brightness);
        holder.locationText.setText(item.locationText);
        holder.radius.setText("Radius: " + item.radius + "m");

        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removeItem(holder.getAdapterPosition());
            }
        });
    }

    private void removeItem(int position) {
        Place removed = items.remove(position);
        removed.delete();
        notifyItemRemoved(position);
        if (position < items.size()) {
            notifyItemRangeChanged(position, items.size() - position);
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void addItem(Place item) {
        items.add(item);
        notifyItemInserted(items.size() - 1);
    }

    public void update(List<Place> newItems) {
        items.clear();
        items.addAll(newItems);
        notifyDataSetChanged();
    }

    public class PlaceViewHolder extends RecyclerView.ViewHolder {

        TextView name;
        SeekBar brightness;
        TextView locationText;
        TextView radius;
        ImageButton deleteButton;

        public PlaceViewHolder(@NonNull View itemView) {
            super(itemView);

            name = (TextView) itemView.findViewById(R.id.placeName);
            brightness = (SeekBar) itemView.findViewById(R.id.placeBrightness);
            locationText = (TextView) itemView.findViewById(R.id.placeLocationText);
            radius = (TextView) itemView.findViewById(R.id.placeRadius);
            deleteButton = (ImageButton) itemView.findViewById(R.id.placeDeleteButton);
        }
    }
}
