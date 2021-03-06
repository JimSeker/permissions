package edu.cs4730.readcontact;

import android.content.pm.PackageManager;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

/*
 * See the MainFragment on how to read all the contacts on the phone.
 */
public class MainActivity extends AppCompatActivity {
    public static final int REQUEST_PERM_ACCESS = 1;
    MainFragment myMainFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myMainFragment = new MainFragment();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, myMainFragment).commit();
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
                    myMainFragment.logger.setText("Contact Read Access: Granted");
                    myMainFragment.fetchContacts();
                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    myMainFragment.logger.setText("Contact Read Access: Not Granted");
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

}
