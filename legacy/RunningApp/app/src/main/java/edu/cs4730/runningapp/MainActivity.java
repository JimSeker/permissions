package edu.cs4730.runningapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/*
 * See the MainFragment for the code on how to see the running apps.
 */

public class MainActivity extends AppCompatActivity {
	
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
