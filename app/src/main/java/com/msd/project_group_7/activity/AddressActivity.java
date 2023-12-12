package com.msd.project_group_7.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.msd.project_group_7.R;
import com.msd.project_group_7.databinding.ActivityAddressBinding;
import com.msd.project_group_7.databinding.ActivityDateTimeBinding;
import com.msd.project_group_7.model.TaskModel;
import com.msd.project_group_7.utils.DBHelper;
import com.msd.project_group_7.utils.Utils;

import java.util.Arrays;

public class AddressActivity extends AppCompatActivity implements View.OnClickListener {

    ActivityAddressBinding addressBinding;
    String address = "";
    double lat,lng;
    String isFrom = "";
    TaskModel taskModel;
    DBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addressBinding = ActivityAddressBinding.inflate(getLayoutInflater());
        View view = addressBinding.getRoot();
        setContentView(view);

        isFrom = getIntent().getStringExtra("isFrom");
        taskModel = (TaskModel) getIntent().getSerializableExtra("taskModel");

        init();

    }

    private void init(){

        db = new DBHelper(this);
        Places.initialize(this, getString(R.string.google_places_api_key));

        addressBinding.tvAddress.setOnClickListener(this);
        addressBinding.done.setOnClickListener(this);

        if(isFrom.equals("edit")) {
            address = taskModel.getTaskAddress();
            addressBinding.tvAddress.setText(address);
        }

    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.tvAddress){
            Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.FULLSCREEN,
                    Arrays.asList(Place.Field.ADDRESS_COMPONENTS,
                            Place.Field.NAME, Place.Field.ADDRESS,
                            Place.Field.LAT_LNG)).build(this);
            startActivityForResult(intent, 1997);
        } else if(view.getId() == R.id.done) {
            if(address.equals("")) {
                Toast.makeText(this, getString(R.string.enter_task_address), Toast.LENGTH_SHORT).show();
            }else {
                if(isFrom.equals("edit")) {
                    taskModel.setTaskAddress(address);
                    taskModel.setTaskLatitude(lat);
                    taskModel.setTaskLongitude(lng);
                    taskModel.setTaskDate(null);
                    taskModel.setTaskTime(null);
                    taskModel.setTaskMilliseconds(0l);
                    boolean updateStatus = db.updateTaskById(taskModel);
                    if (updateStatus) {
                        Toast.makeText(this, getString(R.string.task_updated_successfully), Toast.LENGTH_LONG).show();
                        Intent i = new Intent(this, MainActivity.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(i);
                    } else {
                        Toast.makeText(this, getString(R.string.failure), Toast.LENGTH_LONG).show();
                    }
                }else{
                    taskModel.setTaskAddress(address);
                    taskModel.setTaskLatitude(lat);
                    taskModel.setTaskLongitude(lng);
                    long taskId = db.addTask(taskModel);
                    if(taskId!=-1) {
                        taskModel.setTaskId((int) taskId);
                        Toast.makeText(this,getString(R.string.task_added_successfully), Toast.LENGTH_LONG).show();
                        Intent i = new Intent(this, MainActivity.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(i);
                    } else {
                        Toast.makeText(this,getString(R.string.failure), Toast.LENGTH_LONG).show();
                    }
                }
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if(requestCode==1997 && data!=null){
                Place place = Autocomplete.getPlaceFromIntent(data);
                address = place.getAddress();
                lat = place.getLatLng().latitude;
                lng = place.getLatLng().longitude;
                addressBinding.tvAddress.setText(address);
            }
        }
    }

}