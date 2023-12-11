package com.msd.project_group_7.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.msd.project_group_7.R;
import com.msd.project_group_7.activity.DateTimeActivity;
import com.msd.project_group_7.activity.ViewTaskDetailActivity;
import com.msd.project_group_7.databinding.ItemCategoryBinding;
import com.msd.project_group_7.databinding.ItemTaskBinding;
import com.msd.project_group_7.model.CategoryModel;
import com.msd.project_group_7.model.TaskModel;
import com.msd.project_group_7.utils.Utils;

import java.util.ArrayList;


public class TaskAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Activity activity;
    ItemTaskBinding taskBinding;
    ArrayList<TaskModel> taskArray;
    String isFrom = "";
    TaskModel taskModel;

    public TaskAdapter(Activity activity, ArrayList<TaskModel> taskArray) {
        this.activity = activity;
        this.taskArray = taskArray;
    }

    // bind the row layout
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        taskBinding = ItemTaskBinding.inflate(layoutInflater, parent, false);
        return new ViewHolder(taskBinding);
    }

    // bind view with the ViewHolder
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((ViewHolder) holder).bindView(taskArray.get(position), position);
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ItemTaskBinding taskBinding;
        int position;

        public ViewHolder(ItemTaskBinding taskBinding) {
            super(taskBinding.getRoot());
            this.taskBinding = taskBinding;
        }

        // bind the data into the textview using the StringBuffer
        void bindView(TaskModel taskModel, final int position){
            this.position = position;

            if(taskModel.getTaskType().equals("Location")){
                taskBinding.imgCategory.setImageResource(R.drawable.location);
                taskBinding.txtSubText.setText(taskModel.getTaskAddress());
            } else {
                taskBinding.imgCategory.setImageResource(Utils.getImageFromCategory(activity, taskModel.getTaskCategory()));
                taskBinding.txtSubText.setText(taskModel.getTaskDate() + " " + taskModel.getTaskTime());
            }

            taskBinding.txtName.setText(taskModel.getTaskName());
            taskBinding.mainLout.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            if(view.getId() == R.id.mainLout){
                activity.startActivity(new Intent(activity, ViewTaskDetailActivity.class)
                        .putExtra("taskModel", taskArray.get(position)));
            }
        }

    }

    // set count of the list
    @Override
    public int getItemCount() {
        return taskArray.size();
    }



}
