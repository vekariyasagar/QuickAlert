package com.msd.project_group_7.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.wear.widget.WearableLinearLayoutManager;
import androidx.wear.widget.WearableRecyclerView;

import android.os.Bundle;
import android.view.View;

import com.msd.project_group_7.R;
import com.msd.project_group_7.adapter.CategoryAdapter;
import com.msd.project_group_7.databinding.ActivityCategoryBinding;
import com.msd.project_group_7.databinding.ActivityTaskNameBinding;
import com.msd.project_group_7.model.CategoryModel;
import com.msd.project_group_7.model.TaskModel;

import java.util.ArrayList;

public class CategoryActivity extends AppCompatActivity {

    ActivityCategoryBinding categoryBinding;
    CategoryAdapter categoryAdapter;
    ArrayList<CategoryModel> categoryList = new ArrayList<>();
    String isFrom = "";
    TaskModel taskModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        categoryBinding = ActivityCategoryBinding.inflate(getLayoutInflater());
        View view = categoryBinding.getRoot();
        setContentView(view);

        isFrom = getIntent().getStringExtra("isFrom");
        taskModel = (TaskModel) getIntent().getSerializableExtra("taskModel");

        init();

    }

    private void init(){

        setLayout();

        categoryList.add(new CategoryModel(getString(R.string.work), R.drawable.work));
        categoryList.add(new CategoryModel(getString(R.string.education), R.drawable.education));
        categoryList.add(new CategoryModel(getString(R.string.health), R.drawable.health));
        categoryList.add(new CategoryModel(getString(R.string.shopping), R.drawable.shopping));
        categoryList.add(new CategoryModel(getString(R.string.social), R.drawable.social));
        categoryList.add(new CategoryModel(getString(R.string.travel), R.drawable.travel));
        categoryList.add(new CategoryModel(getString(R.string.entertainment), R.drawable.entertainment));
        categoryList.add(new CategoryModel(getString(R.string.family), R.drawable.family));
        categoryList.add(new CategoryModel(getString(R.string.hobbies), R.drawable.hobbies));

        bindAdapter();

    }

    private void setLayout(){
        categoryBinding.rvCategory.setHasFixedSize(true);
        WearableLinearLayoutManager layoutManager = new WearableLinearLayoutManager(this);
        categoryBinding.rvCategory.setLayoutManager(layoutManager);
    }

    private void bindAdapter(){
        categoryAdapter = new CategoryAdapter(this, categoryList, isFrom, taskModel);
        categoryBinding.rvCategory.setAdapter(categoryAdapter);
        categoryAdapter.notifyDataSetChanged();
    }

}