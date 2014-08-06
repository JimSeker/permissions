package edu.cs4730.readprofile;

import android.app.Activity;
import android.os.Bundle;

/*
 * See the MainFragment for how to read the profile.
 * 
 * Note, this app only works on api 14+  No support libs were used either.
 */

public class MainActivity extends Activity {

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
