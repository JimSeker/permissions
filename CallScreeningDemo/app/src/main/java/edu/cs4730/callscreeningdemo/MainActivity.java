package edu.cs4730.callscreeningdemo;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.app.Activity;
import android.app.role.RoleManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Switch;

import com.google.android.material.switchmaterial.SwitchMaterial;

import edu.cs4730.callscreeningdemo.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    public static final String PREFS_NAME = "CallScreen";
    CallScreenData myCallScreenData = new CallScreenData();
    ActivityResultLauncher<Intent> myActivityResultLauncher;
    ActivityMainBinding binding;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        myActivityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    //Intent data = result.getData();
                    Log.d(TAG, "We are the call screening app");
                } else {
                    // Your app is not the call screening app
                    Log.wtf(TAG, "We are NOT the call screening app");
                }
            }
        });


        //set everything.
        loadTitlePref(getApplicationContext(), myCallScreenData);
        binding.noring.setChecked(myCallScreenData.noring);
        binding.disallow.setChecked(myCallScreenData.disallow);
        binding.nonot.setChecked(myCallScreenData.nonot);
        binding.nolog.setChecked(myCallScreenData.nolog);
        binding.reject.setChecked(myCallScreenData.reject);

        binding.noring.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myCallScreenData.noring = binding.noring.isChecked();
                saveTitlePref(getApplicationContext(), myCallScreenData);
            }
        });
        binding.disallow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!binding.disallow.isChecked()) { //turned off
                    binding.reject.setChecked(false);
                    binding.nonot.setChecked(false);
                    binding.nolog.setChecked(false);
                }
                myCallScreenData.disallow = binding.disallow.isChecked();
                myCallScreenData.reject = binding.reject.isChecked();
                myCallScreenData.nonot = binding.nonot.isChecked();
                myCallScreenData.nolog = binding.nolog.isChecked();
                saveTitlePref(getApplicationContext(), myCallScreenData);
            }
        });
        binding.reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.reject.isChecked()) {
                    binding.disallow.setChecked(true);
                }
                myCallScreenData.disallow = binding.disallow.isChecked();
                myCallScreenData.reject = binding.reject.isChecked();
                saveTitlePref(getApplicationContext(), myCallScreenData);
            }
        });
        binding.nonot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.nonot.isChecked()) {
                    binding.disallow.setChecked(true);
                }
                myCallScreenData.disallow = binding.disallow.isChecked();
                myCallScreenData.nonot = binding.nonot.isChecked();
                saveTitlePref(getApplicationContext(), myCallScreenData);
            }
        });
        binding.nolog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.nolog.isChecked()) {
                    binding.disallow.setChecked(true);
                }
                myCallScreenData.disallow = binding.disallow.isChecked();
                myCallScreenData.nolog = binding.nolog.isChecked();
                saveTitlePref(getApplicationContext(), myCallScreenData);
            }
        });

        requestRole();
    }

    public void requestRole() {
        RoleManager roleManager = (RoleManager) getSystemService(ROLE_SERVICE);
        Intent intent = roleManager.createRequestRoleIntent(RoleManager.ROLE_CALL_SCREENING);
        myActivityResultLauncher.launch(intent);
    }

    // Write the prefix to the SharedPreferences object for this widget
    static void saveTitlePref(Context context, CallScreenData data) {
        SharedPreferences.Editor prefs = context.getSharedPreferences(PREFS_NAME, 0).edit();
        prefs.putBoolean("noring", data.noring);
        prefs.putBoolean("disallow", data.disallow);
        prefs.putBoolean("reject", data.reject);
        prefs.putBoolean("nolog", data.nolog);
        prefs.putBoolean("nonot", data.nonot);
        prefs.commit();
    }

    // Read the prefix from the SharedPreferences object for this widget.
    // If there is no preference saved, get the default from a resource
    static void loadTitlePref(Context context, CallScreenData data) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, 0);
        data.noring = prefs.getBoolean("noring", false);
        data.disallow = prefs.getBoolean("disallow", false);
        data.reject = prefs.getBoolean("reject", false);
        data.nolog = prefs.getBoolean("nolog", false);
        data.nonot = prefs.getBoolean("nonot", false);

    }

}