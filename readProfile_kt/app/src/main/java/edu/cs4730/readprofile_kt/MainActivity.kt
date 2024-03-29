package edu.cs4730.readprofile_kt


import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import edu.cs4730.readprofile_kt.databinding.ActivityMainBinding


/**
 * This reads a profile and displays all the information to the screen.
 */
class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    var TAG = "MainActivity"
    lateinit var rpl: ActivityResultLauncher<Array<String>>

    //private final String[] REQUIRED_PERMISSIONS = new String[]{Manifest.permission.READ_PROFILE, Manifest.permission.WRITE_PROFILE, Manifest.permission.READ_CONTACTS};
    //we only need to ask for read contacts permission.  The other are needed in the manifest.  this doesn't write the profile, so that is not needed.
    private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.READ_CONTACTS)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.getRoot())
        rpl = registerForActivityResult<Array<String>, Map<String, Boolean>>(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { isGranted ->
            var granted = true
            for ((key, value) in isGranted) {
                logthis("$key is $value")
                if (!value) granted = false
            }
            if (granted) getProfile()
        }
        binding.btn.setOnClickListener {
            if (!allPermissionsGranted()) rpl.launch(REQUIRED_PERMISSIONS) else {
                logthis("all permissions granted.")
                getProfile()
            }
        }
    }

    fun logthis(msg: String) {
        binding.logger.append(msg + "\n")
        Log.d(TAG, msg)
    }

    //ask for permissions when we start.
    private fun allPermissionsGranted(): Boolean {
        for (permission in REQUIRED_PERMISSIONS) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    permission
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                return false
            }
        }
        return true
    }

    /**
     * This will go through the profile and print everything we can see.
     */

    @SuppressLint("Range")
    private fun getProfile() {
        val c =
            contentResolver.query(ContactsContract.Profile.CONTENT_URI, null, null, null, null)
                ?: return
        val count = c.count
        val columnNames = c.columnNames
        c.moveToFirst() //make sure the cursor is at the beginning, because it could be at the end of the list already.
        for (i in 0 until count) {
            for (columnName in columnNames) {
                logthis(columnName + " " + c.getString(c.getColumnIndex(columnName)))
            }
        }
        c.close()
    }
}

