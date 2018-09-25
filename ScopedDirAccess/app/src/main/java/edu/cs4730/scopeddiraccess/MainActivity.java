package edu.cs4730.scopeddiraccess;

import android.content.Context;
import android.content.Intent;
import android.content.UriPermission;
import android.net.Uri;
import android.os.Environment;
import android.os.storage.StorageManager;
import android.os.storage.StorageVolume;

import androidx.documentfile.provider.DocumentFile;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;


/**
 * An example of the using the  Scoped Direcatory access permissions in N.
 * Not, there is no permission request set in the manifest file.
 * <p>
 * To test, you can revoke the permission by going to app -> scopedDirAccess -> storage ->  Clear Access button.
 * This is based off of https://plus.google.com/u/0/+AndroidDevelopers/posts/byKvsJ5W4Lj?cfem=1
 * As note, see comments, there is a bug in 7.0, where if the user denies and never ask again is check.
 * It's permanent, even surviving a deinstall of the app and reinstall.  Should be fixed 7.1
 */


public class MainActivity extends AppCompatActivity {

    int REQUEST_ACCESS = 1;

    Button button;
    TextView logger;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DemoCode();
            }
        });
        logger = findViewById(R.id.textView);

    }

    void DemoCode() {

        StorageManager storageManager = (StorageManager) getSystemService(Context.STORAGE_SERVICE);

        StorageVolume primaryVolume = storageManager.getPrimaryStorageVolume();

        List<StorageVolume> volumes = storageManager.getStorageVolumes();

        //get access to say the picture directories
        Intent intent = primaryVolume.createAccessIntent(Environment.DIRECTORY_DOWNLOADS);
        startActivityForResult(intent, REQUEST_ACCESS);
        haveaccess();
    }


    public void haveaccess() {
        List<UriPermission> uriPermissionList = getContentResolver().getPersistedUriPermissions();
        if (uriPermissionList != null && uriPermissionList.size() > 0) {
            logger.append("list is of size" + uriPermissionList.size());
            for (int i = 0; i < uriPermissionList.size(); i++)
                logger.append("\n" +
                    uriPermissionList.get(i).getUri().toString()
                );
        } else {
            logger.append("\nNo uri permissions have been granted.");
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_ACCESS) {
            //check to see if user accepted the request
            if (resultCode == RESULT_OK) {
                logger.append("\nAccess granted.  The following");
                Uri documentTreeUri = data.getData();
                DocumentFile directory = DocumentFile.fromTreeUri(this, documentTreeUri);
                DocumentFile[] filelist = directory.listFiles();
                if (filelist != null) {
                    for (int i = 0; i < filelist.length; i++) {
                        logger.append("\n" + i + " " + filelist[i].getName());
                    }
                }
                //get perment access, so I don't have ask user everytime.  get both read and write access to the picture directory.
                getContentResolver().takePersistableUriPermission(documentTreeUri, Intent.FLAG_GRANT_READ_URI_PERMISSION);
                getContentResolver().takePersistableUriPermission(documentTreeUri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION);

            } else {
                logger.append("\nUser rejected Access.");
                Toast.makeText(this, "User rejected request to access pictures", Toast.LENGTH_SHORT).show();
            }
        }

    }
}
