package edu.cs4730.readprofile;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;

import java.util.Map;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import edu.cs4730.readprofile.databinding.ActivityMainBinding;

/**
 * This reads a profile and displays all the information to the screen.
 */

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    String TAG = "MainActivity";
    ActivityResultLauncher<String[]> rpl;
    //private final String[] REQUIRED_PERMISSIONS = new String[]{Manifest.permission.READ_PROFILE, Manifest.permission.WRITE_PROFILE, Manifest.permission.READ_CONTACTS};
    //we only need to ask for read contacts permission.  The other are needed in the manifest.  this doesn't write the profile, so that is not needed.
    private final String[] REQUIRED_PERMISSIONS = new String[]{Manifest.permission.READ_CONTACTS};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(binding.main, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return WindowInsetsCompat.CONSUMED;
        });
        rpl = registerForActivityResult(new ActivityResultContracts.RequestMultiplePermissions(),
            new ActivityResultCallback<Map<String, Boolean>>() {
                @Override
                public void onActivityResult(Map<String, Boolean> isGranted) {
                    boolean granted = true;
                    for (Map.Entry<String, Boolean> x : isGranted.entrySet()) {
                        logthis(x.getKey() + " is " + x.getValue());
                        if (!x.getValue()) granted = false;
                    }
                    if (granted) getProfile();
                }
            }
        );

        binding.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!allPermissionsGranted())
                    rpl.launch(REQUIRED_PERMISSIONS);
                else {
                    logthis("all permissions granted.");
                    getProfile();
                }
            }
        });
    }

    public void logthis(String msg) {
        binding.logger.append(msg + "\n");
        Log.d(TAG, msg);
    }

    //ask for permissions when we start.
    private boolean allPermissionsGranted() {
        for (String permission : REQUIRED_PERMISSIONS) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    /**
     * This will go through the profile and print everything we can see.
     */
    @SuppressLint("Range")
    public void getProfile() {

        Cursor c = getContentResolver().query(ContactsContract.Profile.CONTENT_URI, null, null, null, null);
        if (c == null) return;
        int count = c.getCount();

        String[] columnNames = c.getColumnNames();

        c.moveToFirst();  //make sure the cursor is at the beginning, because it could be at the end of the list already.
        for (int i = 0; i < count; i++) {
            for (String columnName : columnNames) {
                logthis(columnName + " " + c.getString(c.getColumnIndex(columnName)));
            }
        }
        c.close();
    }

}
