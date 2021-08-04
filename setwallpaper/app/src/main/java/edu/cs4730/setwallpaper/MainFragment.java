package edu.cs4730.setwallpaper;

import java.io.IOException;

import android.app.WallpaperManager;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

/**
 * This is an example of how to setup the wall paper on the device.
 */
public class MainFragment extends Fragment {

    public MainFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View myView = inflater.inflate(R.layout.fragment_main, container, false);

        Button buttonSetWallpaper = myView.findViewById(R.id.set);
        ImageView imagePreview = myView.findViewById(R.id.preview);

        imagePreview.setImageResource(R.raw.ifixedit);

        buttonSetWallpaper.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                WallpaperManager myWallpaperManager = WallpaperManager.getInstance(getActivity().getApplicationContext());
                try {
                    //studio claims there is an error, but compiles and the project runs just fine.  stupid studio.
                    myWallpaperManager.setResource(R.raw.ifixedit);
                } catch (IOException e) {

                    e.printStackTrace();
                }

            }
        });
        return myView;
    }

}
