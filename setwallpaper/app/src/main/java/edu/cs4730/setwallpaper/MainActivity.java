package edu.cs4730.setwallpaper;

import android.annotation.SuppressLint;
import android.app.WallpaperManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.IOException;

import androidx.appcompat.app.AppCompatActivity;

/**
 * This is an example of how to setup the wall paper on the device.
 */

public class MainActivity extends AppCompatActivity {

	@SuppressLint("ResourceType")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Button buttonSetWallpaper =findViewById(R.id.set);
		ImageView imagePreview = findViewById(R.id.preview);

		imagePreview.setImageResource(R.raw.ifixedit);

		buttonSetWallpaper.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View view) {
				WallpaperManager myWallpaperManager = WallpaperManager.getInstance(getApplicationContext());
				try {
					//studio claims there is an error, but compiles and the project runs just fine.  stupid studio.
					myWallpaperManager.setResource(R.raw.ifixedit);
				} catch (IOException e) {
					e.printStackTrace();
				}

			}
		});
	}
}
