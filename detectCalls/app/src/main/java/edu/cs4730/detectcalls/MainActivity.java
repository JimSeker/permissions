package edu.cs4730.detectcalls;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

/*
 * In here we request permissions.  Normally your app would have something more interesting
 * then an empty mainactivity.
 *
 * It's the receivers that you want to look at.
 */

public class MainActivity extends AppCompatActivity {

    String TAG = "MainActivity";
    public static final int REQUEST_PERM_ACCESS = 1;
    TextView logger;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        logger = (TextView) findViewById(R.id.logger);
    }

    @Override
    public void onResume() {
        super.onResume();
        CheckPerm();
    }


    //ask for permissions when we start.

    public void CheckPerm() {
        if ((ActivityCompat.checkSelfPermission(this, Manifest.permission.PROCESS_OUTGOING_CALLS) != PackageManager.PERMISSION_GRANTED) ||
                (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED)) {
            //I'm on not explaining why, just asking for permission.
            Log.v(TAG, "asking for permissions");
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.PROCESS_OUTGOING_CALLS, Manifest.permission.READ_PHONE_STATE, Manifest.permission.RECORD_AUDIO},
                    MainActivity.REQUEST_PERM_ACCESS);

        } else {
            logger.setText("Phone Access: Granted");
        }

    }

    //handle the response.
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_PERM_ACCESS: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    logger.setText("Phone Access: Granted");
                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    logger.setText("Phone Access: Not Granted");
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }
}