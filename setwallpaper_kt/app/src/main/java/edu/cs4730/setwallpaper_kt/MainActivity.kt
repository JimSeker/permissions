package edu.cs4730.setwallpaper_kt

import android.annotation.SuppressLint
import android.app.WallpaperManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import edu.cs4730.setwallpaper_kt.databinding.ActivityMainBinding
import java.io.IOException

/**
 * This is an example of how to setup the wall paper on the device.
 */
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.getRoot())
        binding.preview.setImageResource(R.raw.ifixedit)
        binding.set.setOnClickListener {
            //get the wall paper manager and set a new wall paper.
            val myWallpaperManager = WallpaperManager.getInstance(applicationContext)
            try {
                myWallpaperManager.setResource(R.raw.ifixedit)
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }
}
