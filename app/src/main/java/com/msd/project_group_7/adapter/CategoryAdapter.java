package com.msd.project_group_7.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.msd.project_group_7.R;
import com.msd.project_group_7.activity.DateTimeActivity;
import com.msd.project_group_7.databinding.ItemCategoryBinding;
import com.msd.project_group_7.model.CategoryModel;
import com.msd.project_group_7.model.TaskModel;

import java.util.ArrayList;


public class CategoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Activity activity;
    ItemCategoryBinding categoryBinding;
    ArrayList<CategoryModel> categoryArray;
    String isFrom = "";
    TaskModel taskModel;

    public CategoryAdapter(Activity activity, ArrayList<CategoryModel> categoryArray, String isFrom, TaskModel taskModel) {
        this.activity = activity;
        this.categoryArray = categoryArray;
        this.isFrom = isFrom;
        this.taskModel = taskModel;
    }

    // bind the row layout
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        categoryBinding = ItemCategoryBinding.inflate(layoutInflater, parent, false);
        return new ViewHolder(categoryBinding);
    }

    // bind view with the ViewHolder
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((ViewHolder) holder).bindView(categoryArray.get(position), position);
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ItemCategoryBinding categoryBinding;
        int position;

        public ViewHolder(ItemCategoryBinding categoryBinding) {
            super(categoryBinding.getRoot());
            this.categoryBinding = categoryBinding;
        }

        // bind the data into the textview using the StringBuffer
        void bindView(CategoryModel categoryModel, final int position){
            this.position = position;

            categoryBinding.imgCategory.setImageResource(categoryModel.getImage());
            categoryBinding.txtName.setText(categoryModel.getName());

            categoryBinding.mainLout.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            if(view.getId() == R.id.mainLout){
                taskModel.setTaskCategory(categoryArray.get(position).getName());
                activity.startActivity(new Intent(activity, DateTimeActivity.class)
                        .putExtra("isFrom",isFrom).putExtra("taskModel", taskModel));
            }
        }

    }

    // set count of the list
    @Override
    public int getItemCount() {
        return categoryArray.size();
    }

}
