package edu.cs4730.callscreeningdemo_kt

import android.app.role.RoleManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import edu.cs4730.callscreeningdemo_kt.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private var myCallScreenData = CallScreenData()
    private lateinit var myActivityResultLauncher: ActivityResultLauncher<Intent>
    private lateinit var binding: ActivityMainBinding
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.getRoot())
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v: View, insets: WindowInsetsCompat ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            WindowInsetsCompat.CONSUMED
        }
        myActivityResultLauncher = registerForActivityResult<Intent, ActivityResult>(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == RESULT_OK) {
                //Intent data = result.getData();
                Log.d(TAG, "We are the call screening app")
            } else {
                // Your app is not the call screening app
                Log.wtf(TAG, "We are NOT the call screening app")
            }
        }

        //set everything.
        loadTitlePref(applicationContext, myCallScreenData)
        binding.noring.setChecked(myCallScreenData.noring)
        binding.disallow.setChecked(myCallScreenData.disallow)
        binding.nonot.setChecked(myCallScreenData.nonot)
        binding.nolog.setChecked(myCallScreenData.nolog)
        binding.reject.setChecked(myCallScreenData.reject)
        binding.noring.setOnClickListener {
            myCallScreenData.noring = binding.noring.isChecked
            saveTitlePref(applicationContext, myCallScreenData)
        }
        binding.disallow.setOnClickListener {
            if (!binding.disallow.isChecked) { //turned off
                binding.reject.setChecked(false)
                binding.nonot.setChecked(false)
                binding.nolog.setChecked(false)
            }
            myCallScreenData.disallow = binding.disallow.isChecked
            myCallScreenData.reject = binding.reject.isChecked
            myCallScreenData.nonot = binding.nonot.isChecked
            myCallScreenData.nolog = binding.nolog.isChecked
            saveTitlePref(applicationContext, myCallScreenData)
        }
        binding.reject.setOnClickListener {
            if (binding.reject.isChecked) {
                binding.disallow.setChecked(true)
            }
            myCallScreenData.disallow = binding.disallow.isChecked
            myCallScreenData.reject = binding.reject.isChecked
            saveTitlePref(applicationContext, myCallScreenData)
        }
        binding.nonot.setOnClickListener {
            if (binding.nonot.isChecked) {
                binding.disallow.setChecked(true)
            }
            myCallScreenData.disallow = binding.disallow.isChecked
            myCallScreenData.nonot = binding.nonot.isChecked
            saveTitlePref(applicationContext, myCallScreenData)
        }
        binding.nolog.setOnClickListener {
            if (binding.nolog.isChecked) {
                binding.disallow.setChecked(true)
            }
            myCallScreenData.disallow = binding.disallow.isChecked
            myCallScreenData.nolog = binding.nolog.isChecked
            saveTitlePref(applicationContext, myCallScreenData)
        }
        requestRole()
    }

    private fun requestRole() {
        val roleManager = getSystemService(ROLE_SERVICE) as RoleManager
        val intent = roleManager.createRequestRoleIntent(RoleManager.ROLE_CALL_SCREENING)
        myActivityResultLauncher.launch(intent)
    }

    companion object {
        private const val TAG = "MainActivity"
        const val PREFS_NAME = "CallScreen"

        // Write the prefix to the SharedPreferences object for this widget
        @JvmStatic
        fun saveTitlePref(context: Context, data: CallScreenData) {
            val prefs = context.getSharedPreferences(PREFS_NAME, 0).edit()
            prefs.putBoolean("noring", data.noring)
            prefs.putBoolean("disallow", data.disallow)
            prefs.putBoolean("reject", data.reject)
            prefs.putBoolean("nolog", data.nolog)
            prefs.putBoolean("nonot", data.nonot)
            prefs.commit()
        }

        // Read the prefix from the SharedPreferences object for this widget.
        // If there is no preference saved, get the default from a resource
        @JvmStatic
        fun loadTitlePref(context: Context, data: CallScreenData) {
            val prefs = context.getSharedPreferences(PREFS_NAME, 0)
            data.noring = prefs.getBoolean("noring", false)
            data.disallow = prefs.getBoolean("disallow", false)
            data.reject = prefs.getBoolean("reject", false)
            data.nolog = prefs.getBoolean("nolog", false)
            data.nonot = prefs.getBoolean("nonot", false)
        }

    }
}