package edu.cs4730.setwallpaper_kt

import android.annotation.SuppressLint
import android.app.WallpaperManager
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
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
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v: View, insets: WindowInsetsCompat ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            WindowInsetsCompat.CONSUMED
        }

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
