package edu.cs4730.readprofile;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/*
 * See the MainFragment for how to read the profile.
 * 
 * Note, this app only works on api 14+, since there were no profiles before api 14.
 */

public class MainActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		if (savedInstanceState == null) {
			getFragmentManager().beginTransaction()
			.add(R.id.container, new MainFragment()).commit();
		}
	}


}
