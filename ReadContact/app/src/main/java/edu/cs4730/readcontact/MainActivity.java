package edu.cs4730.readcontact;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

/*
 * See the MainFragment on how to read all the contacts on the phone.
 */
public class MainActivity extends ActionBarActivity {


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
			.add(R.id.container, new MainFragment()).commit();
		}
	}


}
