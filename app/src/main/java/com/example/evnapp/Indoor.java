package com.example.evnapp;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.TimePicker;

public class Indoor extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener, PopupMenu.OnMenuItemClickListener {

    private EditText editTextInstituteName, editTextClassRoomId, editTextOccupants, editTextAC, editTextFans, editTextWindow, editTextDoors, editTextStartTime, editTextEndTime;
    private Button saveDetails;
    private String InstituteName, ClassRoomId, Occupants, AC, Fans, Window, Doors, StartTime, EndTime;
    Constants constants=null;
    private boolean CLASS_STARTED = false;
    String set_time;
    private Intent intent=null;
    boolean start_end=false;
    private int menuPosition;
    static private boolean flag=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_indoor);

        editTextInstituteName = findViewById(R.id.Instituename);
        editTextClassRoomId = findViewById(R.id.classid);
        editTextOccupants = findViewById(R.id.occupants);
        editTextAC = findViewById(R.id.ac);
        editTextFans = findViewById(R.id.fan);
        editTextWindow = findViewById(R.id.window);
        editTextDoors = findViewById(R.id.door);
        editTextStartTime = findViewById(R.id.start_time);
        editTextEndTime = findViewById(R.id.end_time);
        saveDetails = findViewById(R.id.saveIndoor);

        editTextStartTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment timePicker = new TimePickerFragment();
                timePicker.show(getSupportFragmentManager(), "time picker");
                start_end=true;
            }
        });
        editTextEndTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment timePicker = new TimePickerFragment();
                timePicker.show(getSupportFragmentManager(), "time picker");
                start_end=false;
            }
        });
        constants = new Constants();
        intent = new Intent(this, Datasets.class);

        saveDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDetails();
                if(setEmptyErrors())
                {
                    putDetails();
                    startActivity(intent);
                }
            }
        });
    }

    private boolean setEmptyErrors() {
        boolean flag=true;
        if (TextUtils.isEmpty(InstituteName))
        {editTextInstituteName.setError("Field Can't be Empty");flag=false;}
        else if (TextUtils.isEmpty(ClassRoomId))
        { editTextClassRoomId.setError("Field Can't be Empty"); flag=false;}
        else if (TextUtils.isEmpty(AC))
        {editTextAC.setError("Field Can't be Empty"); flag=false;}
        else if (TextUtils.isEmpty(Fans))
        {editTextFans.setError("Field Can't be Empty");flag=false;}
        else if (TextUtils.isEmpty(Doors))
        {editTextDoors.setError("Field Can't be Empty");flag=false;}
        else if (TextUtils.isEmpty(Window))
        {editTextWindow.setError("Field Can't be Empty");flag=false;}

        return flag;
    }

    private void getDetails() {
        InstituteName = editTextInstituteName.getText().toString().trim();
        ClassRoomId = editTextClassRoomId.getText().toString().trim();
        Occupants = editTextOccupants.getText().toString().trim();
        if (TextUtils.isEmpty(Occupants))
            Occupants = "0";
        AC = editTextAC.getText().toString().trim();
        Fans = editTextFans.getText().toString().trim();
        Window = editTextWindow.getText().toString().trim();
        Doors = editTextDoors.getText().toString().trim();
        StartTime = editTextStartTime.getText().toString().trim();
        if (TextUtils.isEmpty(StartTime)){
            StartTime = "00:00";
        }
        else
            CLASS_STARTED = true;
        EndTime = editTextEndTime.getText().toString().trim();
        if (TextUtils.isEmpty(EndTime))
            EndTime = "00:00";
    }

    private void putDetails() {
        intent.putExtra(constants.INSTITUTE_ID, InstituteName);
        intent.putExtra(constants.CLASSROOM_ID, ClassRoomId);
        intent.putExtra(constants.OCCUPANTS_ID, Occupants);
        intent.putExtra(constants.AC_ID, AC);
        intent.putExtra(constants.FANS_ID, Fans);
        intent.putExtra(constants.DOORS_ID, Doors);
        intent.putExtra(constants.WINDOW_ID, Window);
        intent.putExtra(constants.START_TIME_ID, StartTime);
        intent.putExtra(constants.END_TIME_ID, EndTime);
    }

    public void showInstituteMenu(View view) {
        //menuPosition = listItemPosition;
        PopupMenu pm=new PopupMenu(this,findViewById(R.id.Instituename));
        pm.setOnMenuItemClickListener(this);
        pm.inflate(R.menu.nameofinstitute);
        flag=false;
        pm.show();
    }

    public void showClassMenu(View view)
    {
        PopupMenu pm=new PopupMenu(this,findViewById(R.id.classid));
        pm.setOnMenuItemClickListener(this);
        pm.inflate(R.menu.nameofclassroom);
        flag=true;
        pm.show();
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        set_time=hourOfDay+":"+minute;
        if(start_end)
            editTextStartTime.setText(set_time);
        else
            editTextEndTime.setText(set_time);
    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        if(!flag)
            editTextInstituteName.setText(menuItem.getTitle());
        else
            editTextClassRoomId.setText(menuItem.getTitle());
        return false;
    }
}
