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

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    public static final String PREFS_NAME = "CallScreen";
    SwitchMaterial noring, disallow, nonot, nolog, reject;
    CallScreenData myCallScreenData = new CallScreenData();
    ActivityResultLauncher<Intent> myActivityResultLauncher;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        noring = findViewById(R.id.noring);
        disallow = findViewById(R.id.disallow);
        nonot = findViewById(R.id.nonot);
        nolog = findViewById(R.id.nolog);
        reject = findViewById(R.id.reject);

        myActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
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
        noring.setChecked(myCallScreenData.noring);
        disallow.setChecked(myCallScreenData.disallow);
        nonot.setChecked(myCallScreenData.nonot);
        nolog.setChecked(myCallScreenData.nolog);
        reject.setChecked(myCallScreenData.reject);

        noring.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myCallScreenData.noring = noring.isChecked();
                saveTitlePref(getApplicationContext(), myCallScreenData);
            }
        });
        disallow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!disallow.isChecked()) { //turned off
                    reject.setChecked(false);
                    nonot.setChecked(false);
                    nolog.setChecked(false);
                }
                myCallScreenData.disallow = disallow.isChecked();
                myCallScreenData.reject = reject.isChecked();
                myCallScreenData.nonot = nonot.isChecked();
                myCallScreenData.nolog = nolog.isChecked();
                saveTitlePref(getApplicationContext(), myCallScreenData);
            }
        });
        reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (reject.isChecked()) {
                    disallow.setChecked(true);
                }
                myCallScreenData.disallow = disallow.isChecked();
                myCallScreenData.reject = reject.isChecked();
                saveTitlePref(getApplicationContext(), myCallScreenData);
            }
        });
        nonot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (nonot.isChecked()) {
                    disallow.setChecked(true);
                }
                myCallScreenData.disallow = disallow.isChecked();
                myCallScreenData.nonot = nonot.isChecked();
                saveTitlePref(getApplicationContext(), myCallScreenData);
            }
        });
        nolog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (nolog.isChecked()) {
                    disallow.setChecked(true);
                }
                myCallScreenData.disallow = disallow.isChecked();
                myCallScreenData.nolog = nolog.isChecked();
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
        SharedPreferences.Editor prefs = context.getSharedPreferences(
            PREFS_NAME, 0).edit();
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