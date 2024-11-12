package edu.cs4730.setwallpaper;

import android.annotation.SuppressLint;
import android.app.WallpaperManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.io.IOException;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import edu.cs4730.setwallpaper.databinding.ActivityMainBinding;

/**
 * This is an example of how to setup the wall paper on the device.
 */

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(binding.main, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return WindowInsetsCompat.CONSUMED;
        });
        binding.preview.setImageResource(R.raw.ifixedit);

        binding.set.setOnClickListener(new Button.OnClickListener() {
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
