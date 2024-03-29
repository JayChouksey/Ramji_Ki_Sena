package com.example.ramjikisena.recyclerfiles;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ramjikisena.R;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {

    private Context context;
    private List<Users> userList;

    public UserAdapter(List<Users> userList) {
        this.userList = userList;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_view_users, parent, false);

        // Create a new UserViewHolder instance and pass the inflated layout
        return new UserViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        Users user = userList.get(position);
        holder.bind(user);
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public static class UserViewHolder extends RecyclerView.ViewHolder {

        TextView usernameTextView, nameTextView, cityTextView, totalCountTextView, contactTextView, joiningDateTextView;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            usernameTextView = itemView.findViewById(R.id.admin_username);
            nameTextView = itemView.findViewById(R.id.admin_name);
            cityTextView = itemView.findViewById(R.id.admin_city);
            totalCountTextView = itemView.findViewById(R.id.admin_total_count);
            contactTextView = itemView.findViewById(R.id.admin_contact);
            joiningDateTextView = itemView.findViewById(R.id.admin_joining_date);
        }

        public void bind(Users user) {
            usernameTextView.setText(user.getUsername());
            nameTextView.setText(user.getName());
            cityTextView.setText(user.getCity());
            totalCountTextView.setText(user.getTotalCount());
            contactTextView.setText(user.getContact());
            joiningDateTextView.setText(user.getJoiningDate());
        }
    }
}
