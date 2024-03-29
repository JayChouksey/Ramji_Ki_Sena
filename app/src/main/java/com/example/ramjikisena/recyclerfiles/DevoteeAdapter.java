package com.example.ramjikisena.recyclerfiles;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ramjikisena.R;

import java.util.List;

public class DevoteeAdapter extends RecyclerView.Adapter<DevoteeAdapter.DevoteeViewHolder> {
    private List<Devotee> devoteeList;
    private static OnRecyclerViewClickListener listener; // Change to proper interface name

    // Interface for click listener
    public interface OnRecyclerViewClickListener {
        void onItemClick(int position);
    }

    // Setter method for click listener
    public void setOnRecyclerViewClickListener(OnRecyclerViewClickListener listener) {
        this.listener = listener;
    }

    private int lastClickedPosition = RecyclerView.NO_POSITION; // color

    public DevoteeAdapter(List<Devotee> devoteeList) {
        this.devoteeList = devoteeList;
    }

    @NonNull
    @Override
    public DevoteeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_devotee, parent, false);
        return new DevoteeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DevoteeViewHolder holder, int position) {
        Devotee devotee = devoteeList.get(position);
        holder.bind(devotee);
        holder.setSelected(position == lastClickedPosition); // color
    }

    @Override
    public int getItemCount() {
        return devoteeList.size();
    }

    public static class DevoteeViewHolder extends RecyclerView.ViewHolder {
        TextView rankTextView, nameTextView, todaysCountTextView, totalCountTextView;

        public DevoteeViewHolder(@NonNull View itemView) {
            super(itemView);
            rankTextView = itemView.findViewById(R.id.devotee_rank);
            nameTextView = itemView.findViewById(R.id.devotee_name);
            todaysCountTextView = itemView.findViewById(R.id.devotee_todays_count);
            totalCountTextView = itemView.findViewById(R.id.devotee_total_count);

            // Set OnClickListener inside the constructor
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.onItemClick(getAdapterPosition());
                    }
                }
            });
        }

        public void bind(Devotee devotee) {
            rankTextView.setText(devotee.getRank());
            nameTextView.setText(devotee.getName());
            todaysCountTextView.setText(String.valueOf(devotee.getTodaysCount()));
            totalCountTextView.setText(String.valueOf(devotee.getTotalCount()));
        }

        public void setSelected(boolean isSelected) {
            itemView.setBackgroundColor(isSelected ? ContextCompat.getColor(itemView.getContext(), R.color.red) : Color.TRANSPARENT);
        }
    }

}
