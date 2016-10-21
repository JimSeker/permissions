package edu.cs4730.readprofile;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.TextView;

/**
 * This reads a profile and displays all the information to the screen.
 *
 * We need contact read permission, as it is dangerous.  not profile though.  odd.
 */
public class MainFragment extends Fragment {

    TextView logger;
    String TAG = "MainFragment";

    public MainFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View myView = inflater.inflate(R.layout.fragment_main, container, false);
        myView.findViewById(R.id.btn).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckPerm();
            }
        });
        logger = (TextView) myView.findViewById(R.id.logger);

        return myView;
    }


    //ask for permissions when we start.

    public void CheckPerm() {
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            //I'm on not explaining why, just asking for permission.
            Log.v(TAG, "asking for permissions");
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_CONTACTS},
                    MainActivity.REQUEST_PERM_ACCESS);

        } else {
            logger.setText("Contact Read Access: Granted\n");
            getprofile();
        }

    }


    public void getprofile() {

        Cursor c = getActivity().getContentResolver().query(ContactsContract.Profile.CONTENT_URI, null, null, null, null);
        int count = c.getCount();

        String[] columnNames = c.getColumnNames();

        boolean b = c.moveToFirst();
        int position = c.getPosition();
        // if (count == 1 && position == 0) {
        for (int i = 0; i < count; i++) {
            for (int j = 0; j < columnNames.length; j++) {
                logger.append("\n" +
                        columnNames[j] + " " +
                        c.getString(c.getColumnIndex(columnNames[j]))
                );
            }
            boolean b2 = c.moveToNext();
        }
        // }
        c.close();
    }

}
