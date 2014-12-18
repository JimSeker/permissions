package edu.cs4730.readprofile;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.app.Fragment;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

/**
 * This reads a profile and displays all the information to the screen.
 * 
 */
public class MainFragment extends Fragment {


	Button call;
	TextView output;
	
	public MainFragment() {
		// Required empty public constructor
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View myView = inflater.inflate(R.layout.fragment_main, container, false);
        myView.findViewById(R.id.btn).setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				getprofile();
			}
        });
		output = (TextView) myView.findViewById(R.id.ouput);
		
		return myView;
	}

	 public void getprofile() {
		 
		 Cursor c = getActivity().getContentResolver().query(ContactsContract.Profile.CONTENT_URI, null,  null, null, null);
		 int count = c.getCount();

		 String[] columnNames = c.getColumnNames();

		 boolean b = c.moveToFirst();
		 int position = c.getPosition();
		// if (count == 1 && position == 0) {
			 for (int i = 0; i < count; i++) {
				 for (int j = 0; j < columnNames.length; j++) {
					 output.append("\n"+
					    columnNames[j] +" " +
					    c.getString(c.getColumnIndex(columnNames[j]))
					  );
				 }	
				 boolean b2 = c.moveToNext();
			 }
		// }
		 c.close();
	}
	
}
