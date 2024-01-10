package com.example.todolist;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TaskListAdapter extends RecyclerView.Adapter<TaskListAdapter.ViewHolder> {
    private List<Task> tasks;

    public TaskListAdapter(List<Task> tasks) {
        this.tasks = tasks;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_task_list, parent, false);
        return new ViewHolder(view);
    }
    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Task task = tasks.get(position);

        holder.titleTextView.setText(task.getTitle());
        holder.dueDateTextView.setText(task.getDueDate());

        switch (task.getStatus()) {
            case "completed":
                holder.statusImageView.setImageResource(R.drawable.ic_check_circle_green_24dp);
                break;
            case "in progress":
                holder.statusImageView.setImageResource(R.drawable.ic_access_time_orange_24dp);
                break;
            case "new":
                holder.statusImageView.setImageResource(R.drawable.ic_fiber_new_blue_24dp);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView;
        TextView dueDateTextView;
        ImageView statusImageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            titleTextView = itemView.findViewById(R.id.titleTextView);
            dueDateTextView = itemView.findViewById(R.id.dueDateTextView);
            statusImageView = itemView.findViewById(R.id.statusImageView);
        }
    }
}

