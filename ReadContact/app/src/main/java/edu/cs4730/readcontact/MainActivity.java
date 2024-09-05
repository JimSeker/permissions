package edu.cs4730.readcontact;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;

import java.util.Map;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import edu.cs4730.readcontact.databinding.ActivityMainBinding;

/**
 * This is an example to read the contacts lists on the phone.
 * <p>
 * We need the check permissions as well.  READ_CONTACTS
 */

public class MainActivity extends AppCompatActivity {
    public ActivityMainBinding binding;
    public String TAG = "MainActivity";
    ActivityResultLauncher<String[]> rpl;
    private final String[] REQUIRED_PERMISSIONS = new String[]{Manifest.permission.READ_CONTACTS};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //this allows us to check with multiple permissions, but in this case (currently) only need 1.
        rpl = registerForActivityResult(new ActivityResultContracts.RequestMultiplePermissions(), new ActivityResultCallback<Map<String, Boolean>>() {
            @Override
            public void onActivityResult(Map<String, Boolean> isGranted) {
                boolean granted = true;
                for (Map.Entry<String, Boolean> x : isGranted.entrySet()) {
                    logthis(x.getKey() + " is " + x.getValue());
                    if (!x.getValue()) granted = false;
                }
                if (granted) fetchContacts();
            }
        });

        if (!allPermissionsGranted())
            rpl.launch(REQUIRED_PERMISSIONS);
        else {
            logthis("All permissions have been granted already.");
            fetchContacts();
        }
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

    @SuppressLint("Range")
    public void fetchContacts() {

        String phoneNumber;
        String email;

        Uri CONTENT_URI = ContactsContract.Contacts.CONTENT_URI;
        String _ID = ContactsContract.Contacts._ID;
        String DISPLAY_NAME = ContactsContract.Contacts.DISPLAY_NAME;
        String HAS_PHONE_NUMBER = ContactsContract.Contacts.HAS_PHONE_NUMBER;

        Uri PhoneCONTENT_URI = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
        String Phone_CONTACT_ID = ContactsContract.CommonDataKinds.Phone.CONTACT_ID;
        String NUMBER = ContactsContract.CommonDataKinds.Phone.NUMBER;

        Uri EmailCONTENT_URI = ContactsContract.CommonDataKinds.Email.CONTENT_URI;
        String EmailCONTACT_ID = ContactsContract.CommonDataKinds.Email.CONTACT_ID;
        String DATA = ContactsContract.CommonDataKinds.Email.DATA;

        ContentResolver contentResolver = getContentResolver();
        Cursor cursor = contentResolver.query(CONTENT_URI, null, null, null, null);

        // Loop for every contact in the phone
        if (cursor == null) return;
        if (cursor.getCount() > 0) {

            while (cursor.moveToNext()) {

                String contact_id = cursor.getString(cursor.getColumnIndex(_ID));
                logthis("\nID:" + contact_id);
                String name = cursor.getString(cursor.getColumnIndex(DISPLAY_NAME));
                int hasPhoneNumber = Integer.parseInt(cursor.getString(cursor.getColumnIndex(HAS_PHONE_NUMBER)));

                if (hasPhoneNumber > 0) {
                    logthis("Name:" + name);
                    // Query and loop for every phone number of the contact
                    Cursor phoneCursor = contentResolver.query(PhoneCONTENT_URI, null, Phone_CONTACT_ID + " = ?", new String[]{contact_id}, null);
                    while (phoneCursor.moveToNext()) {
                        phoneNumber = phoneCursor.getString(phoneCursor.getColumnIndex(NUMBER));
                        logthis("Phone number:" + phoneNumber);
                    }
                    phoneCursor.close();

                    // Query and loop for every email of the contact
                    Cursor emailCursor = contentResolver.query(EmailCONTENT_URI, null, EmailCONTACT_ID + " = ?", new String[]{contact_id}, null);
                    while (emailCursor.moveToNext()) {
                        email = emailCursor.getString(emailCursor.getColumnIndex(DATA));
                        logthis("Email:" + email);
                    }
                    emailCursor.close();
                }
            }

        }
        cursor.close();
    }
}
